package com.poli.meets.mentorship.service.dto;

import lombok.Data;

@Data
public class StudentPostDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String studentEmail;

    private String personalEmail;

    private Long universityYearId;
}