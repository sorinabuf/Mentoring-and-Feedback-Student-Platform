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
public class FeedbackPostDTO implements Serializable {
    
    private Long id;

    private Grade grade;

    private String feedbackText;

    private Long categoryId;

    private Long universityClassId;

}
