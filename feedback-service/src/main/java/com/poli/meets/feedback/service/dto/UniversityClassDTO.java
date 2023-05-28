package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.domain.enumeration.Semester;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link UniversityClass} entity.
 */
@Data
@NoArgsConstructor
public class UniversityClassDTO implements Serializable {
    
    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    private Semester semester;

    private Long teacherId;

    private Long universityYearId;
}
