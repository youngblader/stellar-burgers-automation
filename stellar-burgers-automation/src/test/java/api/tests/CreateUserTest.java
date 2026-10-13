package api;

import clients.UserClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.UserGenerator;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
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
    @DisplayName("Создание уникального пользователя")
    @Description("Успешное создание нового пользователя с уникальными данными")
    void createUniqueUser() {
       Response response = userClient.createUser(user);

       Response loginResponse = userClient.loginUser(user);
       accessToken = loginResponse.path("accessToken");

       response.then()
               .body("success", equalTo(true))
               .statusCode(200);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Возвращает ошибку при попытке создать пользователя, который уже существует")
    void createAlreadyRegisteredUserReturnError() {
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");

        Response response = userClient.createUser(user);

        response.then()
                .body("message", equalTo("User already exists"))
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Возвращает ошибку, если не передано имя пользователя")
    void createUserWithExistingNameReturnError() {
        user.setName(null);

        Response response = userClient.createUser(user);

        response.then().
                body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Возвращает ошибку, если не передан пароль")
    void  createUserWithExistingPasswordReturnError() {
        user.setPassword(null);

        Response response = userClient.createUser(user);

        response.then().
                body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Возвращает ошибку, если не передан email")
    void  createUserWithExistingEmailReturnError() {
        user.setEmail(null);

        Response response = userClient.createUser(user);

        response.then().
                body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }
}
