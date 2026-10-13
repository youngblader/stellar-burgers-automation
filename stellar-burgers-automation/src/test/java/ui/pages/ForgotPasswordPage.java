package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.config.Routes;

import static com.codeborne.selenide.Selenide.$x;

public class ForgotPasswordPage {
    private final SelenideElement signInButton = $x(".//a[@class='Auth_link__1fOlj' and normalize-space()='Войти']");

    @Step("Открыть страницу восстановления пароля")
    public void open() {
        Selenide.open(Routes.FORGOT_PASSWORD);
    }

    @Step("Нажатие на кнопку входа на странице восстановления пароля")
    public void clickSignInButton() {
        signInButton.click();
    }
}
