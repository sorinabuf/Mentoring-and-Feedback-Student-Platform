package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Teacher} entity.
 */
@Data
@NoArgsConstructor
public class TeacherDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

}
