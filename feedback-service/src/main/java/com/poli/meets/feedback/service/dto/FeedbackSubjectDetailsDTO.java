package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.enumeration.Semester;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackSubjectDetailsDTO {

    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    private Semester semester;

    private TeacherDTO teacher;

    private Double gradeOverall;

    private Integer countFeedbacks;

    private List<FeedbackCategoryDTO> feedbackCategories;
}
