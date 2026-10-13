package ui.tests;

import api.tests.BaseApiTest;
import api.utils.UserGenerator;
import clients.UserClient;
import io.qameta.allure.Description;
import models.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.config.Routes;
import ui.pages.LoginPage;
import ui.pages.SignUpPage;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class SignUpTest extends BaseApiTest {
    private User user;
    private SignUpPage signUpPage;
    private LoginPage loginPage;

    private UserClient userClient;
    private String accessToken;

    @BeforeEach
    void setupUITest() {
        prepareUser();
        initPages();
    }

    private void prepareUser() {
        userClient = new UserClient();
        user = UserGenerator.generateUser();
    }

    private void initPages() {
        signUpPage = new SignUpPage();
        loginPage = new LoginPage();
    }

    @AfterEach
    void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Успешная регистрация нового пользователя с последующим переходом на страницу входа")
    void signUpReturnsUserToLoginPage() {
        signUpPage.open();
        signUpPage.signUp(user.getName(), user.getEmail(), user.getPassword());

        accessToken = userClient.loginUser(user).path("accessToken");

        webdriver().shouldHave(urlContaining(Routes.LOGIN));

        loginPage.waitForSignInButtonDisplayed();
    }

    @Test
    @DisplayName("Ошибка при пароле меньше 6 символов")
    @Description("Отображается ошибка при попытке регистрации с паролем меньше 6 символов")
    void signUpWithShortPasswordShowsError() {
        Faker faker = new Faker();
        String password = faker.credentials().password(1, 5, true, true);

        signUpPage.open();
        signUpPage.signUp(user.getName(), user.getEmail(), password);

        signUpPage.waitPasswordErrorDisplayed();
    }
}
