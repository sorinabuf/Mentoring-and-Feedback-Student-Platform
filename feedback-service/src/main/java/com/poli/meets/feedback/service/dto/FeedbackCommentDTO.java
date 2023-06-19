package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.enumeration.Grade;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeedbackCommentDTO {

    private LocalDate feedbackDate;

    private String comment;

    private Integer grade;
}
