package fr.esgi.jee.api.users.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Field(value = "firstname")
    private String firstName;
    @Field(value = "lastname")
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private int phoneNumber;
    @Field(value = "birth_date")
    private Date birthDate;
    @Field(value = "create_date")
    private Date createDate;
    @Field(value = "close_date")
    private Date closeDate;
    @Field(value = "last_login_date")
    private Date lastLoginDate;
    private String token;
    private List<String> roles;
}
