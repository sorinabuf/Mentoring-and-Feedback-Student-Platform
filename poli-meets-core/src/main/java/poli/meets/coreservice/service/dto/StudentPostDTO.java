package poli.meets.coreservice.service.dto;

import lombok.Data;

@Data
public class StudentPostDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String studentEmail;

    private String personalEmail;

    private String groupNum;

    private UniversityYearPostDTO universityYear;
}
