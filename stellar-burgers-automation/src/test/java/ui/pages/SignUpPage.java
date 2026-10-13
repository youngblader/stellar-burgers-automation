package pages;

import constants.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {
    private final WebDriver driver;

    private final By nameField = By.xpath(".//div[label='Имя']//input");
    private final By emailField = By.xpath(".//div[label='Email']//input");
    private final By passwordField = By.xpath(".//div[label='Пароль']//input");

    private final By signUpButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By signInButton = By.xpath(".//a[@class='Auth_link__1fOlj' and normalize-space()='Войти']");

    private final By passwordErrorText = By.xpath(".//p[text()='Некорректный пароль']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        String URL = Routes.REGISTER;
        driver.get(URL);
    }

    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    public void clickSignUpButton() {
        driver.findElement(signUpButton).click();
    }

    public void setEmailField(String value) {
        driver.findElement(emailField).sendKeys(value);
    }

    public void setPasswordField(String value) {
        driver.findElement(passwordField).sendKeys(value);
    }

    public void setNameField(String value) {
        driver.findElement(nameField).sendKeys(value);
    }

    @Step("Ввод данных для регистрации пользователя")
    public void signUp(String name, String email, String password) {
        setNameField(name);
        setEmailField(email);
        setPasswordField(password);
        clickSignUpButton();
    }

    @Step("Ожидание отображение ошибки в форме регистрации")
    public void waitPasswordErrorDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(passwordErrorText));
    }
}
