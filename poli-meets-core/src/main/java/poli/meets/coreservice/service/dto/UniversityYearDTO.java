package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import poli.meets.coreservice.domain.enumeration.Year;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.UniversityYear} entity.
 */
@Data
@NoArgsConstructor
public class UniversityYearDTO implements Serializable {
    
    private Long id;

    private Year year;

    private String series;

    private FacultyDTO facultyId;
}
