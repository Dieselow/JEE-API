package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.authentication.login.Role;
import fr.esgi.jee.api.users.domain.User;
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
@Document(collection = "partner")
public class Partner {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String phoneNumber;
    @Indexed(unique = true)
    private String address;
    @Field(value = "create_date")
    private Date createDate;
    @Field(value = "close_date")
    private Date closeDate;
}