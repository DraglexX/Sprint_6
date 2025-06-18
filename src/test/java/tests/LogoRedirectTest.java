package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LogoRedirectTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

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

    /**
     * Проверяет, что при клике на логотип Яндекса открывается новая вкладка с сайтом Yandex/Dzen.
     */
    @Test
    public void testClickYandexLogoOpensInNewTab() {
        mainPage.clickYandexLogo();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        assertEquals(2, tabs.size(), "Ожидалось, что откроется вторая вкладка после клика по логотипу Яндекса.");

        driver.switchTo().window(tabs.get(1));
        String currentUrl = driver.getCurrentUrl();

        assertTrue(
                currentUrl.contains("dzen.ru") || currentUrl.contains("yandex.ru") || currentUrl.contains("ya.ru"),
                "После клика по логотипу Яндекс не открылся ожидаемый сайт (Dzen или Яндекс)."
        );
    }

    /**
     * Проверяет, что при клике на логотип Самоката происходит переход на главную страницу.
     */
    @Test
    public void testClickScooterLogoRedirectsToMainPage() {
        mainPage.clickScooterLogo();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(
                currentUrl.contains("scooter"),
                "После клика по логотипу Самокат не произошёл переход на главную страницу."
        );
    }
}
