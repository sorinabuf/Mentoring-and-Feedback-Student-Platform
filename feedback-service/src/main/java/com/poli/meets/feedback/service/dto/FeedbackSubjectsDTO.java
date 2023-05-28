package com.poli.meets.feedback.service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class FeedbackSubjectsDTO {

    private Set<SubjectDTO> activeSubjects;

    private Set<SubjectDTO> previousSubjects;

    private Set<SubjectDTO> submittedSubjects;

    private Set<SubjectDTO> unavailableSubjects;

}