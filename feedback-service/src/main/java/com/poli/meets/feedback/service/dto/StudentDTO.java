package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Student} entity.
 */
@Data
@NoArgsConstructor
public class StudentDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

    private String studentEmail;

    private String personalEmail;


    private Long universityYearId;
}
