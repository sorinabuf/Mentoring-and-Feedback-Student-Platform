package poli.meets.authservice.service.dtos;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

    private Boolean extendedValidity;
}
