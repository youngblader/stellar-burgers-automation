package tests;

import clients.UserClient;
import io.qameta.allure.Description;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.SignUpPage;
import utils.BrowserFactory;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUpTest {
    private WebDriver driver;
    private User user;
    private SignUpPage signUpPage;
    private LoginPage loginPage;

    private UserClient userClient;
    private String accessToken;

    @BeforeEach
    void setUp() {
        initDriver();
        prepareUser();
        initPages();
    }

    private void prepareUser() {
        userClient = new UserClient();
        user = UserGenerator.generateUser();
    }

    private void initDriver() {
        driver = BrowserFactory.createDriver();
    }

    private void initPages() {
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
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
    @DisplayName("Регистрация пользователя")
    @Description("Успешная регистрация нового пользователя с последующим переходом на страницу входа")
    void signUpReturnsUserToLoginPage() {
        signUpPage.open();
        signUpPage.signUp(user.getName(), user.getEmail(), user.getPassword());

        accessToken = userClient.loginUser(user).path("accessToken");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));

        assertTrue(driver.getCurrentUrl().contains("/login"));

        loginPage.waitForSignInButtonDisplayed();
    }

    @Test
    @DisplayName("Ошибка при пароле меньше 6 символов")
    @Description("Отображается ошибка при попытке регистрации с паролем меньше 6 символов")
    void signUpWithShortPasswordShowsError() {
        signUpPage.open();
        signUpPage.signUp(user.getName(), user.getEmail(), "12345");

        signUpPage.waitPasswordErrorDisplayed();
    }
}
