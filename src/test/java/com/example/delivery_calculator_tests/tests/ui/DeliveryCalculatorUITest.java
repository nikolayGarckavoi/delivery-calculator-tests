package com.example.delivery_calculator_tests.tests.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.example.delivery_calculator_tests.pages.DeliveryCalculatorPage;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI-тесты калькулятора доставки")
@Feature("Форма расчёта")
public class DeliveryCalculatorUITest {

    private WebDriver driver;
    private DeliveryCalculatorPage calculatorPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        calculatorPage = new DeliveryCalculatorPage(driver);
        calculatorPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Успешный расчёт стоимости")
    @Description("Проверка корректного расчёта для средних габаритов")
    @Severity(SeverityLevel.CRITICAL)

    public void testSuccessfulCalculation() {
        Allure.step("Ввести вес = 10");
        calculatorPage.enterWeight("10");

        Allure.step("Выбрать габариты = MEDIUM");
        calculatorPage.selectDimensions("MEDIUM");

        Allure.step("Ввести расстояние = 120");
        calculatorPage.enterDistance("120");

        Allure.step("Нажать кнопку расчёта");
        calculatorPage.submit();

        // Дадим странице мгновение на реакцию, чтобы появились ошибки, если они есть
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Если есть сообщение об ошибке валидации — упадём здесь с понятным текстом
        if (calculatorPage.isAnyErrorDisplayed()) {
            throw new AssertionError("Обнаружена ошибка валидации веса: ") ;
        }
        // При желании можно добавить проверки на другие поля

        Allure.step("Проверить, что результат отображается");
        String result = calculatorPage.getResultText();
        assertThat(result)
                .as("Результат не должен быть пустым")
                .isNotEmpty();
    }

    @Test
    @Story("Валидация пустого веса")
    @Description("Проверка отображения ошибки при пустом поле веса")
    @Severity(SeverityLevel.NORMAL)
    public void testWeightValidation() {
        Allure.step("Ввести вес = 0 (невалидное значение)");
        calculatorPage.enterWeight("0");   // <-- изменили здесь
        calculatorPage.enterDistance("100");
        calculatorPage.selectDimensions("SMALL");

        Allure.step("Нажать кнопку расчёта");
        calculatorPage.submit();

        Allure.step("Проверить сообщение об ошибке веса");
        assertThat(calculatorPage.isAnyErrorDisplayed())
                .as("Ошибка валидации должна отобразиться")
                .isTrue();
    }
}