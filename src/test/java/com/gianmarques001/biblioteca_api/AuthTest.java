package com.gianmarques001.biblioteca_api;

import com.gianmarques001.biblioteca_api.common.security.JwtToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthTest {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = port;
    }

    @Test
    void autenticarComCredenciaisValidas_Retorno200() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        String requestBody = "{\"email\":\"api.usuario.dev@gmail.com\", \"senha\":\"1234567\"}";

        JwtToken jwtToken = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(JwtToken.class);

        assertNotNull(jwtToken.getToken());

    }

    @Test
    void autenticarComCredenciaisInvalidas_Retorno400() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        String requestBody = "{\"email\":\"admin02@gmail.com\", \"senha\":\"12345678\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(400);
    }
}
