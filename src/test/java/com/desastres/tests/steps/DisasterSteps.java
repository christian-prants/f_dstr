package com.desastres.tests.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class DisasterSteps {

    private Response response;
    private String requestBody;

    @Dado("que estou autenticado como administrador")
    public void autenticarComoAdmin() {
        // Implementar lógica de autenticação
    }

    @Quando("envio uma requisição POST para {string} com:")
    public void enviarRequisicaoPost(String endpoint, String body) {
        requestBody = body;
        response = given()
            .contentType("application/json")
            .body(body)
            .when()
            .post(endpoint);
    }

    @Então("a resposta deve ter status {int}")
    public void verificarStatus(int status) {
        response.then().statusCode(status);
    }

    @Então("o corpo da resposta deve conter um ID")
    public void verificarIdNaResposta() {
        response.then().body("id", notNullValue());
    }
}