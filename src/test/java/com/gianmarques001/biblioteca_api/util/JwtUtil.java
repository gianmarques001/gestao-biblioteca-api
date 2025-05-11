package com.gianmarques001.biblioteca_api.util;

import com.gianmarques001.biblioteca_api.common.security.JwtToken;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

public class JwtUtil {

    public static String obterTokenAutenticado() {
        String requestBody = "{\"email\":\"admin02@gmail.com\", \"senha\":\"1234567\"}";

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

        return jwtToken.getToken();
    }

    public static String obterTokenAutenticado(String email) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("senha", "1234567");

        JwtToken jwtToken = given()
                .contentType(ContentType.JSON)
                .body(requestBody.toJSONString())
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(JwtToken.class);

        return jwtToken.getToken();
    }

}
