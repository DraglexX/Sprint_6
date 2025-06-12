package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы страницы заказа
    private By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroInput = By.className("select-search__input");
    private By metroOptions = By.className("select-search__select");
    private By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    // Второй шаг
    private By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-control");
    private By rentalPeriodOptions = By.className("Dropdown-option");
    private By colorCheckboxBlack = By.id("black");
    private By colorCheckboxGray = By.id("grey");
    private By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.cssSelector("div.Order_Buttons__1xGrp > button.Button_Button__ra12g:nth-of-type(2)");
    private By orderConfirmedText = By.xpath("//*[contains(text(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterName(String name) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        el.clear();
        el.sendKeys(name);
    }

    public void enterSurname(String surname) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(surnameInput));
        el.clear();
        el.sendKeys(surname);
    }

    public void enterAddress(String address) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
        el.clear();
        el.sendKeys(address);
    }

    public void selectMetroStation(String stationName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(metroInput));
        input.click();
        input.sendKeys(stationName);

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(metroOptions));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().contains(stationName.toLowerCase())) {
                option.click();
                break;
            }
        }
    }

    public void enterPhone(String phone) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        el.clear();
        el.sendKeys(phone);
    }

    public void clickNext() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        btn.click();
    }

    public void selectDate(String date) {
        WebElement dateEl = wait.until(ExpectedConditions.elementToBeClickable(dateInput));
        dateEl.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=arguments[1];", dateEl, date);
        dateEl.sendKeys("\n");
    }

    public void selectRentalPeriod(String periodText) {
        List<WebElement> calendars = driver.findElements(By.className("react-datepicker"));
        if (!calendars.isEmpty()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.display='none';", calendars.get(0));
        }

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        dropdown.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rentalPeriodOptions));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(periodText)) {
                option.click();
                break;
            }
        }
    }

    public void selectColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            WebElement black = wait.until(ExpectedConditions.elementToBeClickable(colorCheckboxBlack));
            black.click();
        } else if ("gray".equalsIgnoreCase(color) || "grey".equalsIgnoreCase(color)) {
            WebElement gray = wait.until(ExpectedConditions.elementToBeClickable(colorCheckboxGray));
            gray.click();
        }
    }

    public void enterComment(String comment) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(commentInput));
        input.clear();
        input.sendKeys(comment);
    }

    public void clickOrder() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

    }

    public void confirmOrder() {
        By modalWindow = By.className("Order_Modal__YZ-d3");
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        longWait.until(ExpectedConditions.presenceOfElementLocated(modalWindow));

        By yesButton = By.xpath("//div[contains(@class, 'Order_Buttons__1xGrp')]/button[text()='Да']");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        btn.click();
    }

    public boolean isOrderConfirmed() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(orderConfirmedText, "Заказ оформлен"),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Номер заказа')]"))
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
