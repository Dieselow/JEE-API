package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.users.domain.User;

import java.util.List;

public interface PartnerService {
    Partner addPartner(Partner partner, User user);
    List<Partner> getPartners();
}
