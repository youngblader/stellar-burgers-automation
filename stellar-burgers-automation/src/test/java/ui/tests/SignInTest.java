package tests;

import clients.UserClient;
import io.qameta.allure.Description;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utils.BaseTest;
import utils.BrowserFactory;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInTest extends BaseTest {
    private WebDriver driver;

    private User user;
    private UserClient userClient;
    private String accessToken;

    private HomePage homePage;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private ForgotPasswordPage forgotPasswordPage;
    private ProfilePage profilePage;

    @BeforeEach
    void setUp() {
        initDriver();
        prepareUser();
        initPages();
    }

    private void initDriver() {
        driver = BrowserFactory.createDriver();
    }

    private void prepareUser() {
        userClient = new UserClient();
        user = UserGenerator.generateUser();
        userClient.createUser(user);
        accessToken = userClient.loginUser(user).path("accessToken");
    }

    private void initPages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signUpPage = new SignUpPage(driver);
        profilePage = new ProfilePage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }

        if (driver != null) {
            driver.quit();
        }
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

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/account/profile"));

        assertTrue(driver.getCurrentUrl().contains("/account/profile"));

        profilePage.waitSignOutButtonDisplayed();
    }
}
