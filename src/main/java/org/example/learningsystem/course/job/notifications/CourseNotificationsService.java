package org.example.learningsystem.course.job.notifications;

import org.example.learningsystem.course.model.Course;

import java.util.List;

public interface CourseNotificationsService {

    void send(List<Course> courses);
}
