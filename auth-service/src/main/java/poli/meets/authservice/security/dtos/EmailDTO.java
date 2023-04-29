package poli.meets.authservice.security.dtos;

import lombok.Data;

@Data
public class EmailDTO {

    private String to;

    private String subject;

    private String body;
}
