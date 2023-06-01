package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.enumeration.Year;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UniversityYearPostDTO {

    @Enumerated(EnumType.STRING)
    private Year year;

    private String series;

    private Long facultyId;
}

