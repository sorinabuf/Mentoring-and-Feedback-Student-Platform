package poli.meets.authservice.service.dtos;

import lombok.Data;
import poli.meets.authservice.model.enums.RoleType;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Data
public class UserDetailsDTO {

    private String username;

    private Boolean isActivated;

    private List<RoleType> roles;
}
