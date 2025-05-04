package com.desastres.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApiTests {

    private static Long userId;
    private static Long locationId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        String userJson = "{"
            + "\"nome\": \"João da Silva\","
            + "\"email\": \"joao@email.com\","
            + "\"senha\": \"senha123\","
            + "\"telefone\": 11999999999"
            + "}";

        Response response = given()
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/api/usuarios")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .extract().response();

        userId = response.path("id");
    }

    @Test
    @Order(2)
    public void testCreateLocation() {
        String locationJson = "{"
            + "\"nome\": \"São Paulo\","
            + "\"latitude\": -23.5505,"
            + "\"longitude\": -46.6333,"
            + "\"tipo\": \"Urbano\""
            + "}";

        Response response = given()
            .contentType("application/json")
            .body(locationJson)
        .when()
            .post("/api/localizacoes")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .extract().response();

        locationId = response.path("id");
    }

    @Test
    @Order(3)
    public void testCreateDisasterSuccess() {
        String disasterJson = String.format("{"
            + "\"tipo\": \"Enchente\","
            + "\"data\": \"2025-05-04\","
            + "\"intensidade\": 4,"
            + "\"duracao\": 2,"
            + "\"usuarioId\": {\"id\": %d},"
            + "\"localizacaoId\": {\"id\": %d}"
            + "}", userId, locationId);

        given()
            .contentType("application/json")
            .body(disasterJson)
        .when()
            .post("/api/desastres")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
    }

    @Test
    @Order(4)
    public void testCreateDisasterInvalidData() {
        String invalidJson = String.format("{"
            + "\"tipo\": \"Enchente\","
            + "\"data\": \"2025-05-04\","
            + "\"intensidade\": 4,"
            + "\"duracao\": 2,"
            + "\"usuarioId\": {\"id\": %d}"
            + "}", userId);

        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .post("/api/desastres")
        .then()
            .statusCode(400);
    }

    @Test
    @Order(5)
    public void testGetDisastersByLocation() {
        given()
            .queryParam("location", "São Paulo")
        .when()
            .get("/api/desastres")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }
}
