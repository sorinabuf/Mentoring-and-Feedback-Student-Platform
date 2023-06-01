package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.domain.enumeration.Year;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link UniversityYear} entity.
 */
@Data
@NoArgsConstructor
public class UniversityYearDTO implements Serializable {
    
    private Long id;

    private Year year;

    private String series;

    private Long facultyId;
}
