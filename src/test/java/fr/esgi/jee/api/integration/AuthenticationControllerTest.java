package fr.esgi.jee.api.integration;

import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthenticationControllerTest {

    @Value("${server.servlet.context-path}")
    private String apiPath;

    @Value("${server.port}")
    private String apiPort;

    @Test
    public void login() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "test@test.com");
        requestBody.put("password", "test");

        given()
            .contentType("application/json")
            .body(requestBody)
            .post("http://localhost:" + apiPort + apiPath + "/auth/login")
            .then()
            .assertThat()
            .statusCode(200)
            .body("token", Matchers.notNullValue());
    }
}