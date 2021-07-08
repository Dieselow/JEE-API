package fr.esgi.jee.api.partner.domain.timeslot.reservation;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Reservation {
    private String owner;
    private List<String> invitedUsers;
}
