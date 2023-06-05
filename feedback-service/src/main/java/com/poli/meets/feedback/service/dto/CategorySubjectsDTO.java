package com.poli.meets.feedback.service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategorySubjectsDTO {

    private Long id;

    private String name;

    private Set<SubjectDTO> activeSubjects;

    private Set<SubjectDTO> submittedSubjects;
}
