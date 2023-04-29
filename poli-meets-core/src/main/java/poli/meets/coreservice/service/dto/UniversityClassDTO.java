package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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


    private Long teacherId;

    private Long universityYearId;
}
