package com.desastres.tests;

import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class ApiStepDefinitions {

    private static Long userId;
    private static Long locationId;
    private Response response;

    @Dado("que eu tenho um novo usuário com os seguintes dados:")
    public void criarUsuario(Map<String, String> dados) {
        String userJson = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\",\"telefone\":%s}",
                dados.get("nome"), dados.get("email"), dados.get("senha"), dados.get("telefone"));
        
        response = RestAssured.given()
            .contentType("application/json")
            .body(userJson)
            .post("/api/users");
        
        userId = response.then().extract().path("id");
    }

    @Dado("que eu tenho uma nova localização com os seguintes dados:")
    public void criarLocalizacao(Map<String, String> dados) {
        String locationJson = String.format("{\"nome\":\"%s\",\"latitude\":%s,\"longitude\":%s,\"tipo\":\"%s\"}",
                dados.get("nome"), dados.get("latitude"), dados.get("longitude"), dados.get("tipo"));
        
        response = RestAssured.given()
            .contentType("application/json")
            .body(locationJson)
            .post("/localizacoes");
        
        locationId = response.then().extract().path("id");
    }

    @Quando("eu envio uma requisição POST para {string} com esses dados")
    public void enviarPost(String endpoint) {
        // Implementação já feita nos métodos acima
    }

    @Entao("o status da resposta deve ser {int}")
    public void verificarStatus(int status) {
        response.then().statusCode(status);
    }

    @Entao("a resposta deve conter um ID de usuário")
    public void verificarIdUsuario() {
        response.then().body("id", notNullValue());
    }

}
