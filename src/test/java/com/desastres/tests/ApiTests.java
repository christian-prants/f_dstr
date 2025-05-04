package com.desastres.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")

public class ApiTests {
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        String userJson = "{"
            + "\"nmNome\": \"João da Silva\","
            + "\"nmEmail\": \"joao@email.com\","
            + "\"senha\": \"senha123\","
            + "\"nrTelefone\": 11999999999"
            + "}";

        Response response = given()
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/api/users")
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
            + "\"nmNome\": \"São Paulo\","
            + "\"nrLatitude\": -23.5505,"
            + "\"nrLongitude\": -46.6333,"
            + "\"nmTipo\": \"Urbano\""
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

        locationId = response.path("id");
    }

    @Test
    @Order(3)
    public void testCreateDisasterSuccess() {
        String disasterJson = String.format("{"
            + "\"nmTipo\": \"Enchente\","
            + "\"dtData\": \"2025-05-04\","
            + "\"nrIntensidade\": 4,"
            + "\"hrDuracao\": 2,"
            + "\"tUsuariosIdUsuario\": %d,"
            + "\"tLocalizacaoIdLocalizacao\": %d"
            + "}", userId, locationId);

        given()
            .contentType("application/json")
            .body(disasterJson)
        .when()
            .post("/desastres")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
    }


    @Test
    public void testCreateDisasterInvalidData() {
        String disasterJson = String.format("{"
            + "\"nmTipo\": \"Enchente\","
            + "\"dtData\": \"2025-05-04\","
            + "\"nrIntensidade\": 4,"
            + "\"hrDuracao\": 2,"
            + "\"tUsuariosIdUsuario\": %d,"
            + "\"tLocalizacaoIdLocalizacao\": 4"
            + "}", userId, locationId);

        given()
            .contentType("application/json")
            .body(disasterJson)
        .when()
            .post("/desastres")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
    }

    @Test
    public void testGetDisastersByLocation() {
        // Cria dados de teste usando a sintaxe tradicional
        given()
            .contentType("application/json")
            .body("{ \"name\": \"Teste SP\", \"location\": \"São Paulo\", \"severity\": \"MEDIUM\" }")
        .when()
            .post("/api/disasters");
        
        given()
            .contentType("application/json")
            .body("{ \"name\": \"Teste RJ\", \"location\": \"Rio de Janeiro\", \"severity\": \"LOW\" }")
        .when()
            .post("/api/disasters");
        
        // Teste de consulta
        given()
            .param("location", "São Paulo")
        .when()
            .get("/desastres")
        .then()
            .statusCode(200)
            .body("$", everyItem(hasEntry("location", "São Paulo")));
    }
}
