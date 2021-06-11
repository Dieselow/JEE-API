package fr.esgi.jee.api.users.infra.web;

import fr.esgi.jee.api.authentication.login.Role;
import fr.esgi.jee.api.users.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditRoleDTO {
    User user;
    String role;
    UpdateAction action;
}
