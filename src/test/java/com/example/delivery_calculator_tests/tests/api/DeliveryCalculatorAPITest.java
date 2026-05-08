package com.example.delivery_calculator_tests.tests.api;

import com.example.delivery_calculator_tests.config.TestConfig;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("API-тесты калькулятора доставки")
@Feature("Эндпоинт /api/calculate")
public class DeliveryCalculatorAPITest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
    }

    @Test
    @Story("Успешный запрос")
    @Description("Проверка валидного запроса на расчёт стоимости")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidCalculation() {
        String requestBody = """
            {
                "weight": 10.5,
                "dimensions": "MEDIUM",
                "distance": 120.0
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/calculate")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("result", notNullValue());
    }

    @Test
    @Story("Ошибка валидации")
    @Description("Проверка запроса с отрицательным весом")
    @Severity(SeverityLevel.NORMAL)
    public void testNegativeWeight() {
        String requestBody = """
            {
                "weight": -1.0,
                "dimensions": "SMALL",
                "distance": 10.0
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/calculate")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsStringIgnoringCase("вес"));
    }
}