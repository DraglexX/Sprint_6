package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final By orderButtonTop = By.cssSelector(".Button_Button__ra12g");
    private static final By orderButtonBottom = By.xpath("//div[@class='Home_FinishButton__1_cWm']//button[text()='Заказать']");
    private static final By YANDEX_LOGO = By.className("Header_LogoYandex__3TSOI");
    private static final By SCOOTER_LOGO = By.className("Header_LogoScooter__3lsAR");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By questionLocator(int questionNumber) {
        return By.id("accordion__heading-" + (questionNumber - 1));
    }

    private By answerLocator(int questionNumber) {
        return By.id("accordion__panel-" + (questionNumber - 1));
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickQuestion(int questionNumber) {
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(questionLocator(questionNumber)));
        scrollToElement(question);
        question.click();
    }

    public String getAnswerText(int questionNumber) {
        wait.until(driver -> !driver.findElement(answerLocator(questionNumber)).getText().isEmpty());
        return driver.findElement(answerLocator(questionNumber)).getText();
    }

    public void clickOrderButtonTop() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        scrollToElement(button);
        button.click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        scrollToElement(button);
        button.click();
    }

    public void clickYandexLogo() {
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(YANDEX_LOGO));
        logo.click();
    }

    public void clickScooterLogo() {
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(SCOOTER_LOGO));
        logo.click();
    }
}
