package com.poli.meets.feedback.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackFacultyDTO {

    private Long id;

    private String name;

    private String domain;

    private String description;

    private List<FeedbackUniversityYearDTO> years;
}
