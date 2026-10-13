package pages;

import constants.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;

    private final By singInButton = By.xpath(".//button[text()=\"Войти в аккаунт\"]");
    private final By personalAccountButton = By.xpath(".//a[@class='AppHeader_header__link__3D_hX' and normalize-space()='Личный Кабинет']");
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']");

    private final By constructorHeader = By.xpath("//h1[text()='Соберите бургер']");
    private final By bunsTab = By.xpath("//span[text()='Булки']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']");
    private final By activeTabSpan = By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span");
    private final By bunsHeader = By.xpath("//h2[text()='Булки']");
    private final By saucesHeader = By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeader = By.xpath("//h2[text()='Начинки']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public void open() {
        String URL = Routes.BASE_URL;
        driver.get(URL);
    }

    @Step("Ожидание появление кнопки оформления заказа")
    public void waitSuccessOrderButtonDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    @Step("Нажатие на кнопку войти в аккаунт")
    public void clickLoginButton() {
        driver.findElement(singInButton).click();
    }

    @Step("Нажатие на кнопку Личный кабинет")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    public void waitForConstructorPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
    }

    public void clickBunsTab() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    public void clickSaucesTab() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    public void clickFillingsTab() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    public void waitForActiveTab(String tabName) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(activeTabSpan, tabName));
    }

    public String getActiveTabText() {
        return driver.findElement(activeTabSpan).getText();
    }

    public boolean isBunsHeaderDisplayed() {
        return driver.findElement(bunsHeader).isDisplayed();
    }

    public boolean isSaucesHeaderDisplayed() {
        return driver.findElement(saucesHeader).isDisplayed();
    }

    public boolean isFillingsHeaderDisplayed() {
        return driver.findElement(fillingsHeader).isDisplayed();
    }
}
