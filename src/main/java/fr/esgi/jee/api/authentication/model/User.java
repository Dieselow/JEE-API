package fr.esgi.jee.api.authentication.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;


@Data
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Field("firstname")
    private String firstName;
    @Field("lastname")
    private String lastName;
    private String email;
    private int phoneNumber;
    @Field("birth_date")
    private Date birthDate;
    @Field("create_date")
    private Date createDate;
    @Field("close_date")
    private Date closeDate;
    @Field("last_login_date")
    private Date lastLoginDate;
    private String token;
    private List<String> roles;
}
