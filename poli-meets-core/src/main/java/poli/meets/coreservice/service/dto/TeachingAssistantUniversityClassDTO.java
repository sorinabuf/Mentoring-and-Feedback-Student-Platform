package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.TeachingAssistantUniversityClass} entity.
 */
@Data
@NoArgsConstructor
public class TeachingAssistantUniversityClassDTO implements Serializable {
    
    private Long id;


    private Long teachingAssistantId;

    private Long universityClassId;
}
