package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.enumeration.Semester;
import com.poli.meets.feedback.domain.enumeration.Year;
import lombok.Data;

@Data
public class SubjectDTO {

    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    private Semester semester;

    private TeacherDTO teacher;

    private UniversityYearDTO universityYear;

    public Year getYear() {
        return universityYear.getYear();
    }
}
