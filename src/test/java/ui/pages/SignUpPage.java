package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.config.Routes;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SignUpPage {
    private final SelenideElement nameField = $x(".//div[label='Имя']//input");
    private final SelenideElement emailField = $x(".//div[label='Email']//input");
    private final SelenideElement passwordField = $x(".//div[label='Пароль']//input");

    private final SelenideElement signUpButton = $x(".//button[text()='Зарегистрироваться']");
    private final SelenideElement signInButton = $x(".//a[@class='Auth_link__1fOlj' and normalize-space()='Войти']");

    private final SelenideElement passwordErrorText = $x(".//p[text()='Некорректный пароль']");

    @Step("Открыть страницу регистрации")
    public void open() {
        Selenide.open(Routes.REGISTER);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void setEmailField(String value) {
        emailField.setValue(value);
    }

    public void setPasswordField(String value) {
        passwordField.setValue(value);
    }

    public void setNameField(String value) {
        nameField.setValue(value);
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
        passwordErrorText.shouldBe(visible);
    }
}
