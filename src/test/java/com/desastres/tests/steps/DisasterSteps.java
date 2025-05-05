package com.desastres.tests.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

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
        Map<String, String> dados = dataTable.asMaps().get(0); // usa a primeira linha da tabela
        String userJson = String.format(
            "{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\",\"telefone\":%s}",
            dados.get("nome"), dados.get("email"), dados.get("senha"), dados.get("telefone"));
        
        response = request.body(userJson).post("/api/users");
        userId = response.jsonPath().getLong("id");
    }

    @Dado("uma nova localização com:")
    public void criarLocalizacao(DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0); // usa a primeira linha da tabela
        String locationJson = String.format(
            "{\"nome\":\"%s\",\"latitude\":%s,\"longitude\":%s,\"tipo\":\"%s\"}",
            dados.get("nome"), dados.get("latitude"), dados.get("longitude"), dados.get("tipo"));
        
        response = request.body(locationJson).post("/localizacoes");
        locationId = response.jsonPath().getLong("id");
    }

    @Quando("submeto um novo desastre com:")
    public void criarDesastre(String body) {
        String formattedBody = body
            .replace("<userId>", userId.toString())
            .replace("<locationId>", locationId.toString());
        response = request.body(formattedBody).post("/desastres");
    }

    @Quando("busco desastres por localização")
    public void buscarDesastresPorLocalizacao() {
        response = request.get("/desastres/" + locationId);
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
