package fr.esgi.jee.api.partner.domain.reservation;

import fr.esgi.jee.api.users.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document(collection = "reservation")
public class Reservation {
    @Id
    private String id;
    private List<User> users;
}
