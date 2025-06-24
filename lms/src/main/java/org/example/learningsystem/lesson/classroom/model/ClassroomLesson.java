package org.example.learningsystem.lesson.classroom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.lesson.common.model.Lesson;

@Entity
@Table(name = "classroom_lesson")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class ClassroomLesson extends Lesson {

    private String location;

    private Integer capacity;
}
