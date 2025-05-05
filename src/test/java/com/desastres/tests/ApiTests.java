package com.desastres.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTests {

    private static Integer userId;
    private static Integer locationId;

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
            .post("/api/users")
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .extract().response();

        System.out.println("Resposta formatada:");
        response.prettyPrint();

        userId = Long.valueOf(response.path("id").toString());
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
            .post("/localizacoes")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .extract().response();

        System.out.println("Resposta formatada:");
        response.prettyPrint();

        locationId = Long.valueOf(response.path("id").toString());
    }

    @Test
    @Order(3)
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
    @Order(4)
    public void testCreateDisasterInvalidData() {
        String invalidJson = String.format("{"
            + "\"tipo\": \"Enchente\","
            + "\"data\": \"2025-05-04\","
            + "\"intensidade\": 4,"
            + "\"duracao\": 2,"
            + "\"usuarioId\": %d"
            + "}", userId);
            // criando sem um campo obrigatório 'LOCALIZACAOID'

        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .post("/desastres")
        .then()
            .statusCode(500);
    }

    @Test
    @Order(5)
    public void testGetDisastersByLocation() {
        System.out.println("location id = " + locationId);
        given()
            .queryParam("location", "São Paulo")
        .when()
            .get("/desastres/" + locationId)
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }
}
