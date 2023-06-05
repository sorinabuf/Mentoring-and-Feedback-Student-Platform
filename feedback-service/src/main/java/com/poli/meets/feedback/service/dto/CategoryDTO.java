package com.poli.meets.feedback.service.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String gradeQuestion;

    private String feedbackTextQuestion;
}
