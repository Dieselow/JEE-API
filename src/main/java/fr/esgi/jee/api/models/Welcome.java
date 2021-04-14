package fr.esgi.jee.api.models;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * @Data generate :
 * @Getter
 * @Setter
 * @EqualsAndHashCode
 * @ToString
 */
@Data
@Builder
public class Welcome {
    private String team;
    private String project;
    private String version;
    private List<String> members;
}
