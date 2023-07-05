package poli.meets.coreservice.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {
    String name;

    String email;

    String message;
}
