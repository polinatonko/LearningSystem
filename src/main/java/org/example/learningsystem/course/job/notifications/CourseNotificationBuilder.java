package org.example.learningsystem.course.job.notifications;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.model.Student;

public interface CourseNotificationBuilder {

    CourseNotificationDto build(Course course, Student student);
}
