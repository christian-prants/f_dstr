package com.desastres.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class ApiTests {

    private static Long userId;
    private static Long locationId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        // Criar usuário
        String userJson = "{"
            + "\"nome\": \"João da Silva\","
            + "\"email\": \"joao@email.com\","
            + "\"senha\": \"senha123\","
            + "\"telefone\": 11999999999"
            + "}";

        Response userResponse = given()
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/api/users")
        .then()
            .statusCode(200)
            .body("id_usuario", notNullValue())
            .extract().response();

        userId = userResponse.path("id_usuario");

        // Criar localização
        String locationJson = "{"
            + "\"nome\": \"São Paulo\","
            + "\"latitude\": -23.5505,"
            + "\"longitude\": -46.6333,"
            + "\"tipo\": \"Urbano\""
            + "}";

        Response locationResponse = given()
            .contentType("application/json")
            .body(locationJson)
        .when()
            .post("/localizacoes")
        .then()
            .statusCode(201)
            .body("id_localizacao", notNullValue())
            .extract().response();

        locationId = locationResponse.path("id_localizacao");
    }

    @Test
    @Order(1)
    public void testCreateDisasterSuccess() {
        System.out.println("userId = " + userId);        
        System.out.println("locationId = " + locationId); 
        
        String disasterJson = String.format("{"
            + "\"tipo\": \"Enchente\","
            + "\"data\": \"2025-05-04\","
            + "\"intensidade\": 4,"
            + "\"duracao\": 2,"
            + "\"usuarioId\": %d,"
            + "\"localizacaoId\": %d"
            + "}", userId, locationId);

        given()
            .contentType("application/json")
            .body(disasterJson)
        .when()
            .post("/desastres")
        .then()
            .statusCode(200)
            .body("id", notNullValue());
    }

    @Test
    @Order(2)
    public void testCreateDisasterInvalidData() {
        String invalidJson = String.format("{"
            + "\"tipo\": \"Enchente\","
            + "\"data\": \"2025-05-04\","
            + "\"intensidade\": 4,"
            + "\"duracao\": 2,"
            + "\"usuarioId\":  %d"
            + "}", userId);

        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .post("/desastres")
        .then()
            .statusCode(400);
    }

    @Test
    @Order(3)
    public void testGetDisastersByLocation() {
        given()
            .queryParam("location", "São Paulo")
        .when()
            .get("/desastres/" + locationId)
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }
}
