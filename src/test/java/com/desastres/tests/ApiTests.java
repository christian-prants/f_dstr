import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
            .body("""
                {
                    "name": "Enchente",
                    "location": "São Paulo",
                    "severity": "HIGH"
                }
                """)
        .when()
            .post("/api/disasters")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/disaster-schema.json"));
    }

    @Test
    public void testCreateDisasterInvalidData() {
        given()
            .contentType("application/json")
            .body("""
                {
                    "name": "",
                    "location": "São Paulo",
                    "severity": "INVALID"
                }
                """)
        .when()
            .post("/api/disasters")
        .then()
            .statusCode(400)
            .body("errors", hasSize(greaterThan(0)));
    }

    @Test
    public void testGetDisastersByLocation() {
        // Primeiro cria alguns dados de teste
        createTestData();
        
        given()
            .param("location", "São Paulo")
        .when()
            .get("/api/disasters")
        .then()
            .statusCode(200)
            .body("$", everyItem(hasEntry("location", "São Paulo")));
    }

    private void createTestData() {
        // Implementação para criar dados de teste
    }
}