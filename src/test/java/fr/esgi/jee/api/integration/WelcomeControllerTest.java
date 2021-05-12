package fr.esgi.jee.api.integration;

import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WelcomeControllerTest {

    private String apiPath = "/api/v1";
    private String apiPort = "3001";

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