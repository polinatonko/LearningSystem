package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learningsystem.LearningSystemApplication;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.domain.CourseSettings;
import org.example.learningsystem.domain.Student;
import org.example.learningsystem.dto.course.CourseRequestDto;
import org.example.learningsystem.dto.student.StudentRequestDto;
import org.example.learningsystem.service.course.CourseService;
import org.example.learningsystem.service.student.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {LearningSystemApplication.class})
@AutoConfigureMockMvc
public class EnrollmentTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;
    private static Course course;
    private static Student student;

    @BeforeAll
    static void setUp() {
        course = new Course("Test course", "Course description", BigDecimal.valueOf(100));
        course.setSettings(CourseSettings.builder().isPublic(true).build());

        student = new Student("Name", "Surname", "name@gmail.com",
                LocalDate.of(1900, 1, 1), BigDecimal.valueOf(150));
    }

    @Test
    void enrollStudentSuccess() throws Exception {
        /* Create course and student, make sure that the course is public,
           and the number of student coins is not lower than the price
         */
        course.getSettings().setIsPublic(true);
        course = createCourse(course);
        student.setCoins(course.getPrice());
        student = createStudent(student);

        // Perform enrollment
        mockMvc.perform(post("/courses/{id}/students/{studentId}", course.getId(), student.getId()))
                .andExpect(status().isOk());

        // Verify that course's students contain that student
        var courseStudents = studentService.getAllByCourseId(course.getId());
        assertEquals(1, courseStudents.size());
        var updatedStudent = courseStudents.getFirst();
        assertEquals(student.getId(),updatedStudent.getId());

        // Verify that student's and course's value of coins changed
        assertNotEquals(student.getCoins(), updatedStudent.getCoins());
        var updatedCourse = courseService.getById(course.getId());
        assertNotEquals(course.getCoinsPaid(), updatedCourse.getCoinsPaid());
    }

    @Test
    void enrollStudentEnrollmentDenied() throws Exception {
        // Create groups of courses and students, make sure that the course is private
        course.getSettings().setIsPublic(false);
        course = createCourse(course);
        student = createStudent(student);

        // Perform enrollment and check response status
        mockMvc.perform(post("/courses/{id}/students/{studentId}", course.getId(), student.getId()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void enrollStudentInsufficientFunds() throws Exception {
        /* Create course and student, make sure that the course is public,
           and the price is higher than the number of student coins
         */
        course.getSettings().setIsPublic(true);
        course = createCourse(course);

        student.setCoins(course.getPrice().subtract(BigDecimal.ONE));
        student = createStudent(student);

        // Perform enrollment and check response status
        mockMvc.perform(post("/courses/{id}/students/{studentId}", course.getId(), student.getId()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void unenrollStudentSuccess() throws Exception {
        /* Create course and student, make sure that the course is public,
           and the number of student coins is not lower than the price
         */
        course.getSettings().setIsPublic(true);
        course = createCourse(course);
        student.setCoins(course.getPrice());
        student = createStudent(student);

        // Perform enrollment
        mockMvc.perform(post("/courses/{id}/students/{studentId}", course.getId(), student.getId()))
                .andExpect(status().isOk());
        // Perform unenrolment
        mockMvc.perform(delete("/courses/{id}/students/{studentId}", course.getId(), student.getId()))
                .andExpect(status().isNoContent());

        // Verify that course doesn't contain students
        var courseStudents = studentService.getAllByCourseId(course.getId());
        assertEquals(0, courseStudents.size());
    }

    private Course createCourse(Course course) {
        course.setId(null);
        course.getSettings().setId(null);
        return courseService.create(course);
    }

    private Student createStudent(Student student) {
        student.setId(null);
        student.setEnrollments(null);
        student.setEmail(String.format("%s@gmail.com", UUID.randomUUID()));
        return studentService.create(student);
    }

    private CourseRequestDto getCourseRequestDto(Course course, boolean isPublic) {
        return new CourseRequestDto(course.getId(), course.getTitle(), course.getDescription(),
                course.getPrice(), course.getCoinsPaid(), course.getSettings().getStartDate(),
                course.getSettings().getEndDate(), isPublic);
    }

    private StudentRequestDto getStudentRequestDto(Student student, BigDecimal coins) {
        return new StudentRequestDto(student.getId(), student.getFirstName(), student.getLastName(),
                student.getEmail(), student.getDateOfBirth(), coins);
    }
}
