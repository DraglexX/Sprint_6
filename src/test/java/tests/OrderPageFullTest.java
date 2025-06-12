package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;
import pages.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

import org.openqa.selenium.chrome.ChromeDriver;

public class OrderPageFullTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void fullOrderFlowTest(
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String period,
            String color,
            String comment,
            String buttonType
    ) {
        // Клик по нужной кнопке заказа
        if ("top".equalsIgnoreCase(buttonType)) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        orderPage = new OrderPage(driver);

        // Шаг 1
        orderPage.enterName(name);
        orderPage.enterSurname(surname);
        orderPage.enterAddress(address);
        orderPage.selectMetroStation(metro);
        orderPage.enterPhone(phone);
        orderPage.clickNext();

        // Шаг 2
        orderPage.selectDate(date);
        orderPage.selectRentalPeriod(period);
        orderPage.selectColor(color);
        orderPage.enterComment(comment);
        orderPage.clickOrder();
        orderPage.confirmOrder();

        // Проверка, что заказ оформлен (по тексту подтверждения)
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Заказ оформлен") || pageSource.contains("Номер заказа"),
                "Текст подтверждения заказа не найден на странице");
    }

    // Данные для двух разных сценариев
    private static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("Алексей", "Смирнов", "ул. Примерная, д. 42", "Тверская", "+79995553322", "12.06.2025", "двое суток", "black", "Позвоните за 10 минут", "top"),
                Arguments.of("Мария", "Петрова", "ул. Ленина, д. 7", "Черкизовская", "+79998887766", "13.06.2025", "сутки", "grey", "Оставить у двери", "bottom")
        );
    }
}