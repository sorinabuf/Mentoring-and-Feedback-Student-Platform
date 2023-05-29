package com.poli.meets.mentorship.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UniversityClassMentorshipDTO implements Serializable {
    private Long id;

    private String name;

    private String abbreviation;

    private UniversityYearDTO universityYear;
}
