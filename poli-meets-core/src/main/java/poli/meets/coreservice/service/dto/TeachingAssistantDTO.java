package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.TeachingAssistant} entity.
 */
@Data
@NoArgsConstructor
public class TeachingAssistantDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

}
