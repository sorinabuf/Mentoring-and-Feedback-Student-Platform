package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.UniversityClass;
import com.poli.meets.mentorship.domain.enumeration.Semester;
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

    private Long universityYearId;
}
