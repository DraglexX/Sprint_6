package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("questionsWithAnswers")
    public void checkAnswerContent(int questionNumber, String expectedAnswer) {
        mainPage.clickQuestion(questionNumber);
        String answerText = mainPage.getAnswerText(questionNumber).trim();
        assertEquals(expectedAnswer, answerText, "Неправильный ответ для вопроса " + questionNumber);
    }

    private static Stream<Arguments> questionsWithAnswers() {
        return Stream.of(
                Arguments.of(1, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(2, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(3, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(4, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(5, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(6, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(7, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(8, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }
}
