package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.enumeration.Semester;
import lombok.Data;

@Data
public class FeedbackUniversityClassDTO {
    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    private Semester semester;

    private TeacherDTO teacher;

    private Double gradeOverall;
}
