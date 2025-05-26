package org.example.learningsystem.course.service;

import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.email.config.EmailServerProperties;

public interface CourseNotificationService {

    void sendCourseNotifications(Course course, EmailServerProperties emailServerProperties);
}
