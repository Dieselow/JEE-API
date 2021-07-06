package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.partner.infra.dto.CreatingPartnerDTO;
import fr.esgi.jee.api.users.domain.User;

import java.util.List;

public interface PartnerService {
    Partner addPartner(CreatingPartnerDTO partner, String strAddress, User user);
}
