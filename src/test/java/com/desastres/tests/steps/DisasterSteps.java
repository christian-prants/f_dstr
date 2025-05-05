package com.desastres.tests.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.hamcrest.Matchers.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class DisasterSteps {

    private Response response;
    private RequestSpecification request;
    private static Long userId;
    private static Long locationId;
    private String requestBody;

    @Dado("que o sistema está configurado")
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        request = RestAssured.given().contentType("application/json");
    }

    @Dado("um novo usuário com:")
    public void criarUsuario(DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);

        String email = dados.get("email").replace("@", "+" + System.currentTimeMillis() + "@");

        String userJson = String.format(
            "{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\",\"telefone\":\"%s\"}",
            dados.get("nome"), email, dados.get("senha"), dados.get("telefone"));

        response = request.body(userJson).post("/api/users");

        if (response.getStatusCode() >= 400) {
            throw new RuntimeException("Erro ao criar usuário: " + response.getStatusCode() + " - " + response.getBody().asString());
        }

        try {
            userId = response.jsonPath().getLong("id");
            if (userId == null) {
                throw new RuntimeException("Resposta não contém 'id': " + response.getBody().asString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair 'id' da resposta do usuário: " + response.getBody().asString(), e);
        }
    }

    @Dado("uma nova localização com:")
    public void criarLocalizacao(DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);

        String locationJson = String.format(
            "{\"nome\":\"%s\",\"latitude\":%s,\"longitude\":%s,\"tipo\":\"%s\"}",
            dados.get("nome"), dados.get("latitude"), dados.get("longitude"), dados.get("tipo"));

        response = request.body(locationJson).post("/localizacoes");

        if (response.getStatusCode() != 200 && response.getStatusCode() != 201) {
            throw new RuntimeException("Erro ao criar localização: " + response.getStatusCode() + " - " + response.getBody().asString());
        }

        try {
            locationId = response.jsonPath().getLong("id");
            if (locationId == null) {
                throw new RuntimeException("Resposta não contém 'id': " + response.getBody().asString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair 'id' da resposta da localização: " + response.getBody().asString(), e);
        }
    }

    @Quando("submeto um novo desastre com:")
    public void criarDesastre(String body) {
        if (body.contains("<userId>") && userId == null) {
            throw new RuntimeException("userId está null antes de enviar o desastre");
        }
        if (body.contains("<locationId>") && locationId == null) {
            throw new RuntimeException("locationId está null antes de enviar o desastre");
        }

        String formattedBody = body
            .replace("<userId>", userId.toString())
            .replace("<locationId>", locationId.toString());

        response = request.body(formattedBody).post("/desastres");

        if (response.getStatusCode() >= 400) {
            throw new RuntimeException("Erro ao criar desastre: " + response.getStatusCode() + " - " + response.getBody().asString());
        }
    }

    @Quando("busco desastres por localização")
    public void buscarDesastresPorLocalizacao() {
        if (locationId == null) {
            throw new RuntimeException("locationId está null antes de buscar desastres");
        }

        response = request.get("/desastres/" + locationId);

        if (response.getStatusCode() >= 400) {
            throw new RuntimeException("Erro ao buscar desastres: " + response.getStatusCode() + " - " + response.getBody().asString());
        }
    }

    @Entao("o sistema retorna status {int}")
    public void verificarStatus(int status) {
        response.then().statusCode(status);
    }

    @Entao("contém um ID válido")
    public void verificarIdValido() {
        response.then().body("id", notNullValue());
    }

    @Entao("lista de desastres não está vazia")
    public void verificarListaNaoVazia() {
        response.then().body("size()", greaterThan(0));
    }
}
