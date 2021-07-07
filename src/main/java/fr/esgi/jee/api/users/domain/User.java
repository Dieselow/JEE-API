package fr.esgi.jee.api.users.domain;

import fr.esgi.jee.api.authentication.domain.login.Role;
import fr.esgi.jee.api.partner.domain.Partner;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;


@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Field(value = "firstname")
    private String firstName;
    @Field(value = "lastname")
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    @Field(value = "create_date")
    private Date createDate;
    @Field(value = "close_date")
    private Date closeDate;
    @DBRef
    private Set<Role> roles;
    @DBRef
    private Set<Partner> partners;
}
