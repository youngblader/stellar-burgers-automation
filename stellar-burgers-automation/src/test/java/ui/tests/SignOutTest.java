package tests;

import clients.UserClient;
import io.qameta.allure.Description;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.BaseTest;
import utils.BrowserFactory;
import utils.UserGenerator;

public class SignOutTest extends BaseTest {
    private WebDriver driver;

    private UserClient userClient;
    private User user;
    private String accessToken;

    private ProfilePage profilePage;
    private HomePage homePage;
    private LoginPage loginPage;

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
        profilePage = new ProfilePage(driver);
        homePage = new HomePage(driver);
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
    @DisplayName("Выход из аккаунта")
    @Description("Успешный выход из аккаунта через кнопку Выйти в личном кабинете")
    void signOut() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        homePage.clickPersonalAccountButton();
        profilePage.clickSignOutButton();
        loginPage.waitForSignInButtonDisplayed();
    }
}
