package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import poli.meets.coreservice.domain.enumeration.Semester;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.UniversityClass} entity.
 */
@Data
@NoArgsConstructor
public class UniversityClassDTO implements Serializable {
    
    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    private TeacherDTO teacher;

    private UniversityYearDTO universityYear;
}
