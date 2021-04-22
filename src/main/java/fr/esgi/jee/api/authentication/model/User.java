package fr.esgi.jee.api.authentication.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.List;


@Data
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private Date birthDate;
    private Date createDate;
    private Date closeDate;
    private Date lastLoginDate;
    private String token;
    private List<String> roles;
}
