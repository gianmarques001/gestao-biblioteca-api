package com.gianmarques001.biblioteca_api;

import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioResponseDTO;
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
public class UsuarioTest {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = port;
    }


    @Test
    void criarUsuarioValido_Retorno201() {

        String token = JwtUtil.obterTokenAutenticado();
        JSONObject requestParams = new JSONObject();
        requestParams.put("nome", "João Carlos.");
        requestParams.put("email", "joao@gmail.com");
        requestParams.put("senha", "1234567");
        requestParams.put("permissao", "ROLE_ALUNO");

        UsuarioResponseDTO response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/usuarios")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(UsuarioResponseDTO.class);

        Assertions.assertThat(response.email()).isEqualTo("joao@gmail.com");
    }

    @Test
    void criarUsuarioComEmailDuplicado_Retorno409() {

        String token = JwtUtil.obterTokenAutenticado();
        JSONObject requestParams = new JSONObject();
        requestParams.put("nome", "Admin Master");
        requestParams.put("email", "admin02@gmail.com");
        requestParams.put("senha", "1234567");
        requestParams.put("permissao", "ROLE_GERENTE");

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/usuarios")
                .then()
                .statusCode(409)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Email já cadastrado");
    }

    @Test
    void criarUsuarioComCamposInvalidos_Retorno422() {

        String token = JwtUtil.obterTokenAutenticado();
        JSONObject requestParams = new JSONObject();
        requestParams.put("nome", "João Alberto");
        requestParams.put("email", "alberto@gmail.com");
        requestParams.put("senha", null);
        requestParams.put("permissao", "ROLE_ALUNO");

        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .post("/api/usuarios")
                .then()
                .statusCode(422)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Alguns campos estão inválidos");
    }

    @Test
    void buscarUsuarioComIdValido_Retorno200() {

        String token = JwtUtil.obterTokenAutenticado();
        UsuarioDetailsResponseDTO response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/usuarios/101")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(UsuarioDetailsResponseDTO.class);

        Assertions.assertThat(response.nome()).isEqualTo("admin");
    }

    @Test
    void buscarUsuarioComIdInvalido_Retorno404() {

        String token = JwtUtil.obterTokenAutenticado();
        MensagemDeErro response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/usuarios/1000")
                .then()
                .statusCode(404)
                .extract()
                .body()
                .as(MensagemDeErro.class);

        Assertions.assertThat(response.getMessage()).isEqualTo("Usuário não encontrado");
    }


    @Test
    void atualizarUsuario_Retorno204() {
        String token = JwtUtil.obterTokenAutenticado();

        JSONObject requestParams = new JSONObject();
        requestParams.put("senha", "1234567");
        requestParams.put("novaSenha", "12345678");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .when()
                .patch("/api/usuarios")
                .then()
                .statusCode(204);
    }

}
