package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.domain.enumeration.Year;
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
