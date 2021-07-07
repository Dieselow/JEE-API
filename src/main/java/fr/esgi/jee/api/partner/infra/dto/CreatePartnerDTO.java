package fr.esgi.jee.api.partner.infra.dto;

import fr.esgi.jee.api.partner.domain.Partner;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePartnerDTO {
    public CreatingPartnerDTO partner;
    public String address;
    public String userId;
}

