package clients;

import api.config.ApiRoutes;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserClient {
    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(ApiRoutes.CREATE_USER_ROUTE);
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(ApiRoutes.LOGIN_USER_ROUTE);
    }

    @Step("Обновление данных пользователя")
    public Response updateUserData(User user, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(user)
                .patch(ApiRoutes.USER_ROUTE);
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(ApiRoutes.USER_ROUTE);
    }
}
