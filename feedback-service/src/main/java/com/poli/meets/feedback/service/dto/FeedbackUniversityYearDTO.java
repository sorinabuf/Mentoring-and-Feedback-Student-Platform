package com.poli.meets.feedback.service.dto;


import com.poli.meets.feedback.domain.enumeration.Year;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackUniversityYearDTO {

    private Long id;

    private Year year;

    private String series;

    private List<FeedbackUniversityClassDTO> universityClasses;
}
