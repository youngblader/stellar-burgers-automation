package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.config.Routes;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement emailField = $x(".//div[label='Email']//input");
    private final SelenideElement passwordField = $x(".//div[label='Пароль']//input");
    private final SelenideElement signInButton = $x(".//button[text()='Войти']");

    @Step("Открытие страницы логина")
    public void open() {
        Selenide.open(Routes.LOGIN);
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickLoginButton() {
        signInButton.click();
    }

    public void setEmailField(String value) {
        emailField.clear();
        emailField.setValue(value);
    }

    public void setPasswordField(String value) {
        passwordField.clear();
        passwordField.setValue(value);
    }

    @Step("Ожидание появление кнопки входа")
    public void waitForSignInButtonDisplayed() {
        signInButton.shouldBe(visible);
    }

    @Step("Ввод данных логина, логин")
    public void login(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        clickLoginButton();
    }
}
