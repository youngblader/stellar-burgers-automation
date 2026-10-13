package api;

import clients.OrderClient;
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

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class CreateOrderTest extends BaseTest {
    private UserClient userClient;
    private OrderClient orderClient;
    private User user;
    private String accessToken;

    @BeforeEach
    void setUpTest() {
        userClient = new UserClient();
        orderClient = new OrderClient();

        user = UserGenerator.generateUser();
    }

    @AfterEach
    void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Успешное создание заказа авторизованным пользователем")
    void createOrderByAuthorized() {
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");

        Response ingredientsResponse = orderClient.getIngredients();
        List<String> ingredientIds = ingredientsResponse.path("data._id");

        Response response = orderClient.createOrder(ingredientIds, accessToken);

        response.then()
                .body("order.number", instanceOf(Integer.class))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    @Description("Успешное создание заказа без авторизации")
    void createOrderByUnAuthorized() {
        Response ingredientsResponse = orderClient.getIngredients();
        List<String> ingredientIds = ingredientsResponse.path("data._id");

        Response response = orderClient.createOrder(ingredientIds, "");

        response.then()
                .body("order.number", instanceOf(Integer.class))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Успешное создание заказа при передаче списка ингредиентов")
    void createOrderWithIngredients() {
        Response ingredientsResponse = orderClient.getIngredients();
        List<String> ingredientIds = ingredientsResponse.path("data._id");

        Response response = orderClient.createOrder(ingredientIds, "");

        response.then()
                .body("order.number", instanceOf(Integer.class))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Возвращает ошибку, если создать заказ без ингредиентов")
    void createOrderWithoutIngredients() {
        Response response = orderClient.createOrder(null, "");

        response.then()
                .body("message", equalTo("Ingredient ids must be provided"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа с невалидными хэшами ингредиентов")
    @Description("Проверяет, что при передаче невалидных hash-идентификаторов ингредиентов возвращается ошибка")
    void createOrderWithInvalidIngredientHash() {
        List<String> invalidIds = List.of("61c0c5a71d1f82001bdaaa6", "61c0c5a71d1f82001bdaaa6");

        Response response = orderClient.createOrder(invalidIds, "");

        response.then().statusCode(500);
    }
}
