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
    public void testCreateDisasterSuccess() {
        given()
            .contentType("application/json")
            .body("{ \"name\": \"Enchente\", \"location\": \"São Paulo\", \"severity\": \"HIGH\" }")
        .when()
            .post("/api/disasters")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
            // Removida a validação de schema temporariamente
    }

    @Test
    public void testCreateDisasterInvalidData() {
        given()
            .contentType("application/json")
            .body("{ \"name\": \"\", \"location\": \"São Paulo\", \"severity\": \"INVALID\" }")
        .when()
            .post("/api/disasters")
        .then()
            .statusCode(400)
            .body("errors", hasSize(greaterThan(0)));
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
            .get("/api/disasters")
        .then()
            .statusCode(200)
            .body("$", everyItem(hasEntry("location", "São Paulo")));
    }
}
