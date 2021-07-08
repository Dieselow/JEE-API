package fr.esgi.jee.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WelcomeControllerTest {

    @Value("${server.servlet.context-path}")
    private String apiPath;

    @Value("${server.port}")
    private String apiPort;

    @Test
    public void welcome() {
        get("http://localhost:" + apiPort + apiPath + "/welcome")
                .then()
                .assertThat()
                .statusCode(200)
                .body("team", is("Les Bros Ⓒ"))
                .body("project", is("JEE-API"))
                .body("members", hasItems("Julien DA CORTE", "Louis DUMONT", "Maxime D'HARBOULLE"));
    }
}