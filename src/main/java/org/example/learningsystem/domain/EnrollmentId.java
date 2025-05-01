package org.example.learningsystem.domain;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentId implements Serializable {
    private UUID courseId;
    private UUID studentId;
}
