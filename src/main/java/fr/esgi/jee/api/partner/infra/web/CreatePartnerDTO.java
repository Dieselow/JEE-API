package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.Partner;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePartnerDTO {
    private Partner partner;
    private String userId;
}
