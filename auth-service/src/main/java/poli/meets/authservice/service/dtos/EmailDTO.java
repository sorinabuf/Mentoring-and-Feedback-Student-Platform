package poli.meets.authservice.service.dtos;

import lombok.Data;

@Data
public class EmailDTO {

    private String to;

    private String subject;

    private String body;
}
