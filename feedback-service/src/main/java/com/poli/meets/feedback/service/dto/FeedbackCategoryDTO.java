package com.poli.meets.feedback.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackCategoryDTO {

    private String categoryName;

    private Double gradeCategory;

    private Integer countFeedbacks;

    private List<FeedbackCommentDTO> feedbackComments;

    private List<RatingBreakdownDTO> ratingBreakdown;
}
