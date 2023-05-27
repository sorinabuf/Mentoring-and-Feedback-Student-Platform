package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.Feedback;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import com.poli.meets.feedback.domain.enumeration.Grade;

/**
 * A DTO for the {@link Feedback} entity.
 */
@Data
@NoArgsConstructor
public class FeedbackDTO implements Serializable {
    
    private Long id;

    private Grade gradeCourse;

    private String feedbackCourse;

    private Grade gradeLaboratory;

    private String feedbackLaboratory;

    private Grade gradeHomework;

    private String feedbackDuringSemester;

    private Grade gradeExam;

    private String feedbackExam;

    private Grade gradeDifficulty;

    private String feedbackDifficutly;

    private Grade gradeRelevance;

    private String feedbackRelevance;


    private Long studentId;

    private Long universityClassId;

    private Long teachingAssistantId;
}
