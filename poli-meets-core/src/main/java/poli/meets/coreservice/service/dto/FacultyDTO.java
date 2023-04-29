package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.Faculty} entity.
 */
@Data
@NoArgsConstructor
public class FacultyDTO implements Serializable {
    
    private Long id;

    private String name;

    private String domain;

    private String description;

}
