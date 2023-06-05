package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link poli.meets.coreservice.domain.Student} entity.
 */
@Data
@NoArgsConstructor

public class StudentDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

    private String studentEmail;

    private String personalEmail;

    private String groupNum;

    private UniversityYearDTO universityYear;

    private byte[] image;
}
