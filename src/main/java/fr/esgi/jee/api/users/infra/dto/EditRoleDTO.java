package fr.esgi.jee.api.users.infra.dto;

import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.infra.web.UpdateAction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditRoleDTO {
    public User user;
    public String role;
    public UpdateAction action;
}
