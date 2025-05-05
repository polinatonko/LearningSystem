package org.example.learningsystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "course")
public class Lesson {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private String title;
    private Integer duration;
    @ManyToOne(optional = false)
    private Course course;
}