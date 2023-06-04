package com.poli.meets.coreservice.service.dto;

import com.poli.meets.feedback.domain.enumeration.Year;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class UniversityYearDTO implements Serializable {
    
    private Long id;

    private Year year;

    private String series;

    private FacultyDTO faculty;
}
