package com.gianmarques001.biblioteca_api;

import com.gianmarques001.biblioteca_api.livro.dto.LivroDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.util.JwtUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LivroTest {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = port;
    }


    @Test
    void criarLivroValido_Retorno201() {
        String token = JwtUtil.obterTokenAutenticado();

        JSONObject requestParams = new JSONObject();
        requestParams.put("titulo", "O Código da Vinci");
        requestParams.put("autor", "Dan Brown");
        requestParams.put("genero", "SUSPENSE");
        requestParams.put("isbn", "9780739339787");

        LivroDetailsResponseDTO response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/livros")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(LivroDetailsResponseDTO.class);

        Assertions.assertThat(response.titulo()).isEqualTo("O Código da Vinci");
    }

    @Test
    void criarLivroComISBNDuplicado_Retorno409() {
        String token = JwtUtil.obterTokenAutenticado();

        JSONObject requestParams = new JSONObject();
        requestParams.put("titulo", "O Código da Vinci");
        requestParams.put("autor", "Dan Brown");
        requestParams.put("genero", "SUSPENSE");
        requestParams.put("isbn", "9783140464079");

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/livros")
                .then()
                .statusCode(409)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("ISBN já existente.");
    }

    @Test
    void criarLivroComTituloDuplicado_Retorno409() {
        String token = JwtUtil.obterTokenAutenticado();

        JSONObject requestParams = new JSONObject();
        requestParams.put("titulo", "O Pequeno Principe");
        requestParams.put("autor", "Antonie de Saint-Exupery");
        requestParams.put("genero", "AVENTURA");
        requestParams.put("isbn", "9780156012072");

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/livros")
                .then()
                .statusCode(409)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Livro já existente.");
    }

    @Test
    void criarLivroComCamposInvalidos_Retorno422() {
        String token = JwtUtil.obterTokenAutenticado();

        JSONObject requestParams = new JSONObject();
        requestParams.put("titulo", "O Código da Vinci");
        requestParams.put("autor", null);
        requestParams.put("genero", "SUSPENSE");
        requestParams.put("isbn", "9780739339787");

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/livros")
                .then()
                .statusCode(422)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Alguns campos estão inválidos");
    }

}
