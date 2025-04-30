package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "course")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private Integer duration;
    @ManyToOne(optional = false)
    private Course course;
}