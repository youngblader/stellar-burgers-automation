package pages;

import constants.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By emailField = By.xpath(".//div[label='Email']//input");
    private final By passwordField = By.xpath(".//div[label='Пароль']//input");
    private final By singInButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы логина")
    public void open() {
        String URL = Routes.LOGIN;
        driver.get(URL);
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(singInButton).click();
    }

    public void setEmailField(String value) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(value);
    }

    public void setPasswordField(String value) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(value);
    }

    @Step("Ожидание появление кнопки входа")
    public void waitForSignInButtonDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(singInButton));
    }

    @Step("Ввод данных логина, логин")
    public void login(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        clickLoginButton();
    }
}
