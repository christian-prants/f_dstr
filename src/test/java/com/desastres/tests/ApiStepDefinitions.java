package com.desastres.tests;

import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class ApiStepDefinitions {

    private Response response;
    private RequestSpecification request;
    private static Long userId;
    private static Long locationId;
    private String lastEndpoint;
    private String requestBody;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        request = RestAssured.given().contentType("application/json");
    }

    @Dado("que eu tenho um novo usuário com os seguintes dados:")
    public void criarUsuario(Map<String, String> dados) {
        String userJson = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\",\"telefone\":%s}",
                dados.get("nome"), dados.get("email"), dados.get("senha"), dados.get("telefone"));
        
        response = request.body(userJson).post("/api/users");
        userId = response.then().extract().path("id");
    }

    @Dado("que eu tenho uma nova localização com os seguintes dados:")
    public void criarLocalizacao(Map<String, String> dados) {
        String locationJson = String.format("{\"nome\":\"%s\",\"latitude\":%s,\"longitude\":%s,\"tipo\":\"%s\"}",
                dados.get("nome"), dados.get("latitude"), dados.get("longitude"), dados.get("tipo"));
        
        response = request.body(locationJson).post("/localizacoes");
        locationId = response.then().extract().path("id");
    }

    @Dado("que existe um usuário cadastrado")
    public void usuarioJaCadastrado() {
        // Assume que o usuário já foi criado em um passo anterior
        if (userId == null) {
            criarUsuario(Map.of(
                "nome", "Usuário Padrão",
                "email", "default@email.com",
                "senha", "senha123",
                "telefone", "11999999999"
            ));
        }
    }

    @Dado("que existe uma localização cadastrada")
    public void localizacaoJaCadastrada() {
        // Assume que a localização já foi criada em um passo anterior
        if (locationId == null) {
            criarLocalizacao(Map.of(
                "nome", "Localização Padrão",
                "latitude", "-23.5505",
                "longitude", "-46.6333",
                "tipo", "Urbano"
            ));
        }
    }

    @Dado("que existem desastres registrados para {string}")
    public void desastresRegistrados(String localizacao) {
        // Cria um desastre padrão se não existir
        testCreateDisasterSuccess();
    }

    @Quando("eu envio uma requisição POST para {string} com esses dados")
    public void enviarPostComDados(String endpoint) {
        lastEndpoint = endpoint;
        response = request.body(requestBody).post(endpoint);
    }

    @Quando("eu envio uma requisição POST para {string} com:")
    public void enviarPostComBody(String endpoint, String body) {
        lastEndpoint = endpoint;
        requestBody = body
            .replace("<userId>", userId.toString())
            .replace("<locationId>", locationId.toString());
        response = request.body(requestBody).post(endpoint);
    }

    @Quando("eu envio uma requisição POST para {string} com dados incompletos:")
    public void enviarPostIncompleto(String endpoint, String body) {
        lastEndpoint = endpoint;
        requestBody = body.replace("<userId>", userId.toString());
        response = request.body(requestBody).post(endpoint);
    }

    @Quando("eu envio uma requisição GET para {string}")
    public void enviarGet(String endpoint) {
        lastEndpoint = endpoint
            .replace("<locationId>", locationId.toString());
        response = request.get(lastEndpoint);
    }

    @Entao("o status da resposta deve ser {int}")
    public void verificarStatus(int status) {
        response.then().statusCode(status);
    }

    @Entao("a resposta deve conter um ID de usuário")
    public void verificarIdUsuario() {
        response.then().body("id", notNullValue());
    }

    @Entao("a resposta deve conter um ID de localização")
    public void verificarIdLocalizacao() {
        response.then().body("id", notNullValue());
    }

    @Entao("a resposta deve conter um ID de desastre")
    public void verificarIdDesastre() {
        response.then().body("id", notNullValue());
    }

    @Entao("a resposta deve conter uma lista não vazia de desastres")
    public void verificarListaDesastres() {
        response.then().body("size()", greaterThan(0));
    }

    // Método auxiliar para criar um desastre de teste
    private void testCreateDisasterSuccess() {
        String disasterJson = String.format(
            "{\"tipo\":\"Enchente\",\"data\":\"2025-05-04\",\"intensidade\":4,\"duracao\":2,\"usuarioId\":%d,\"localizacaoId\":%d}",
            userId, locationId);
        
        request.body(disasterJson).post("/desastres");
    }
}
