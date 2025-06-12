package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
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
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    public void checkAnswerIsDisplayedForQuestion(int questionNumber) {
        mainPage.clickQuestion(questionNumber);
        String answerText = mainPage.getAnswerText(questionNumber);
        assertFalse(answerText.isEmpty(), "Ответ на вопрос " + questionNumber + " должен отображаться");
    }
}
