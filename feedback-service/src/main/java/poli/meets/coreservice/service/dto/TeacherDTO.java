package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class TeacherDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

}
