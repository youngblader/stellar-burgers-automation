package api.tests;

import clients.UserClient;
import api.utils.UserGenerator;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseApiTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @BeforeEach
    void setUpTest() {
        userClient = new UserClient();
        user = UserGenerator.generateUser();
    }

    @AfterEach
    void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    @Description("Успешная авторизация уже зарегистрированного пользователя")
    void loginWithExistingUser() {
        userClient.createUser(user);

        Response response = userClient.loginUser(user);
        accessToken = response.path("accessToken");

        response.then()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация с невалидным паролем")
    @Description("Возвращает ошибку при попытке авторизации с неверным паролем")
    void loginWithInvalidPassword() {
        userClient.createUser(user);
        user.setPassword(null);

        Response response = userClient.loginUser(user);

        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);
    }

    @Test
    @DisplayName("Авторизация с невалидным email")
    @Description("Возвращает ошибку при попытке авторизации с неверным email")
    void loginWithInvalidEmail() {
        userClient.createUser(user);
        user.setEmail(null);

        Response response = userClient.loginUser(user);

        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);
    }
}
