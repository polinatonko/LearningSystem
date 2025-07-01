package org.example.learningsystem.course.job.builder;

import org.example.learningsystem.course.job.dto.CourseNotificationDto;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.model.Student;

public interface CourseNotificationBuilder {

    CourseNotificationDto build(Course course, Student student);
}
