package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ui.config.Routes;
import ui.pages.*;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class SignInTest extends BaseUITestWithUser {
    private HomePage homePage;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private ForgotPasswordPage forgotPasswordPage;
    private ProfilePage profilePage;

    @BeforeEach
    void setupUITest() {
        initPages();
    }

    private void initPages() {
        homePage = new HomePage();
        loginPage = new LoginPage();
        signUpPage = new SignUpPage();
        profilePage = new ProfilePage();
        forgotPasswordPage = new ForgotPasswordPage();
    }

    @Test
    @DisplayName("Вход через кнопку Войти в аккаунт на главной")
    @Description("Успешный вход в аккаунт через кнопку Войти в аккаунт на главной странице")
    void loginViaEnterAccountButtonOnHomePage() {
        homePage.open();
        homePage.clickLoginButton();

        loginPage.login(user.getEmail(), user.getPassword());
        homePage.waitSuccessOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход через кнопку Личный кабинет")
    @Description("Успешный вход в аккаунт через кнопку Личный кабинет на главной странице")
    void loginViaPersonalAccountButton() {
        homePage.open();
        homePage.clickPersonalAccountButton();

        loginPage.login(user.getEmail(), user.getPassword());
        homePage.waitSuccessOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход через кнопку Войти в форме регистрации")
    @Description("Успешный вход в аккаунт через кнопку Войти на странице регистрации")
    void loginViaButtonInRegistrationForm() {
        signUpPage.open();
        signUpPage.clickSignInButton();

        loginPage.login(user.getEmail(), user.getPassword());
        homePage.waitSuccessOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход через кнопку Войти со страницы восстановления пароля")
    @Description("Успешный вход в аккаунт через кнопку Войти на странице восстановления пароля")
    void loginViaButtonInPasswordRecoveryForm() {
        forgotPasswordPage.open();
        forgotPasswordPage.clickSignInButton();

        loginPage.login(user.getEmail(), user.getPassword());
        homePage.waitSuccessOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Переход в личный кабинет со страницы восстановления пароля")
    @Description("После авторизации пользователь успешно переходит в личный кабинет")
    void shouldNavigateToPersonalAccount() {
        forgotPasswordPage.open();
        forgotPasswordPage.clickSignInButton();

        loginPage.login(user.getEmail(), user.getPassword());
        homePage.clickPersonalAccountButton();

        webdriver().shouldHave(urlContaining(Routes.PROFILE));

        profilePage.waitSignOutButtonDisplayed();
    }
}
