package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.Faculty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Faculty} entity.
 */
@Data
@NoArgsConstructor
public class FacultyDTO implements Serializable {
    
    private Long id;

    private String name;

    private String domain;

    private String description;

}
