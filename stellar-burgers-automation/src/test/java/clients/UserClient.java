package api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String CREATE_USER_ROUTE = "/auth/register";
    private static final String USER_ROUTE = "/auth/user";
    private static final String LOGIN_USER_ROUTE = "/auth/login";

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(CREATE_USER_ROUTE);
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(LOGIN_USER_ROUTE);
    }

    @Step("Обновление данных пользователя")
    public Response updateUserData(User user, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(user)
                .patch(USER_ROUTE);
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(USER_ROUTE);
    }
}
