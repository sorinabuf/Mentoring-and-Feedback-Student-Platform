package com.poli.meets.mentorship.service.dto;

import lombok.Data;

@Data
public class EmailDTO {

    private String to;

    private String subject;

    private String body;
}
