package poli.meets.coreservice.service.dto;

import lombok.Data;
import poli.meets.coreservice.domain.enumeration.Year;

@Data
public class UniversityYearPostDTO {

    private Year year;

    private String series;

    private Long facultyId;
}
