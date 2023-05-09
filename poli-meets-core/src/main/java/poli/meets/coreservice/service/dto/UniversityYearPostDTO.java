package poli.meets.coreservice.service.dto;

import lombok.Data;
import poli.meets.coreservice.domain.enumeration.Year;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UniversityYearPostDTO {

    @Enumerated(EnumType.STRING)
    private Year year;

    private String series;

    private Long facultyId;
}
