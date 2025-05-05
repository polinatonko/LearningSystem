package org.example.learningsystem.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "course")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString(exclude = {"settings", "lessons", "enrollments"})
@DynamicInsert
public class Course {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal coinsPaid;
    @OneToOne(mappedBy = "course", cascade = ALL)
    private CourseSettings settings;
    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "course", cascade = ALL)
    private Set<Enrollment> enrollments;

    public void setSettings(CourseSettings settings) {
        this.settings = settings;
        settings.setCourse(this);
    }

    public Course(String title, String description, BigDecimal price, BigDecimal coinsPaid) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.coinsPaid = coinsPaid;
    }
}