package org.example.learningsystem.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.core.audit.model.AuditableEntity;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@ToString(exclude = "enrollments")
@DynamicInsert
public class Student extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private BigDecimal coins;
    private String language;
    @OneToMany(mappedBy = "student", cascade = ALL)
    private Set<CourseEnrollment> enrollments;

    public Student(String firstName, String lastName, String email, LocalDate dateOfBirth, BigDecimal coins) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.coins = coins;
    }
}