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

public class UpdateUserDataTest extends BaseApiTest {
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
    @DisplayName("Обновление данных пользователя с авторизацией")
    @Description("Проверяет, что авторизованный пользователь может успешно обновить свои данные")
    void updateUserDataWithAuth() {
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");

        user.setName("Eugene" + System.currentTimeMillis());

        Response response = userClient.updateUserData(user, accessToken);

        response.then()
                .body("user.name", equalTo(user.getName()))
                .statusCode(200);
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    @Description("Возвращает ошибку при попытке обновить данные без авторизации")
    void updateUserDataWithoutAuthReturnError() {
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");

        Response response = userClient.updateUserData(user, "");

        response.then()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }
}
