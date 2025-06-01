package com.gianmarques001.biblioteca_api;


import com.gianmarques001.biblioteca_api.emprestimo.dto.EmprestimoEncerradoResponseDTO;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class EmprestimoTest {

    @MockitoBean
    private JavaMailSender mailSender;

    @LocalServerPort
    private int port;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = port;
    }

    // Salvar Emprestimo Valido
    @Test
    void salvarEmprestimoValido_Retorno201() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("idLivro", 201);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/emprestimos")
                .then()
                .statusCode(201);

    }

    // Salvar Emprestimo Invalido
    @Test
    void salvarEmprestimoComLivroInvalido_Retorno404() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario.dev@gmail.com");


        JSONObject requestParams = new JSONObject();
        requestParams.put("idLivro", 300);

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/emprestimos")
                .then()
                .statusCode(404)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Livro não encontrado");
    }

    // Salvar Emprestimo usuario ja possui o livro
    @Test
    void salvarEmprestimoDuplicado_Retorno409() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario.dev@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("idLivro", 202);

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/emprestimos")
                .then()
                .statusCode(409)
                .extract()
                .body()
                .as(MensagemDeErro.class);


        Assertions.assertThat(response.getMessage()).isEqualTo("Usuário já possui este livro emprestado.");
    }

    // Salvar Emprestimo Invalido livro indisponivel
    @Test
    void salvarEmprestimoComLivroIndisponivel_Retorno409() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("idLivro", 202);

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/emprestimos")
                .then()
                .statusCode(409)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Livro indisponível no momento para empréstimo. Você foi adicionado a lista de para reservar.");
    }

    // Salvar Emprestimo usuario ultrapassou o limite
    @Test
    void salvarEmprestimoLimiteExcedeu_Retorno403() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario.dev@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("idLivro", 201);

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/emprestimos")
                .then()
                .statusCode(403)
                .extract()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Usuário atingiu o limite de empréstimos permitidos.");
    }

    // Encerrar Emprestimo Valido
    @Test
    void encerrarEmprestimoValido_Retorno200() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario.dev@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("livro", 202);

        EmprestimoEncerradoResponseDTO response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch("/api/emprestimos/devolver/" + 301L)
                .then()
                .statusCode(200)
                .extract()
                .as(EmprestimoEncerradoResponseDTO.class);

        Assertions.assertThat(response.message()).isEqualTo("Emprestimo Encerrado");

    }


    // Encerrar Emprestimo ja encerrado
    @Test
    void encerrarEmprestimoJaEncerrado_Retorno400() {
        String token = JwtUtil.obterTokenAutenticado("api.usuario.dev@gmail.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("livro", 202);


        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch("/api/emprestimos/devolver/" + 301L)
                .then()
                .statusCode(200);


        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch("/api/emprestimos/devolver/" + 301L)
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .extract()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Esse emprestimo já está encerrado. Verifique novamente.");


    }
}
