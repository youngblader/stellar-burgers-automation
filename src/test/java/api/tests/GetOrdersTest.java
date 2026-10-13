package api.tests;

import clients.OrderClient;
import clients.UserClient;
import api.utils.UserGenerator;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest extends BaseApiTest {
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
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Успешное получение списка заказов авторизованного пользователя")
    void getOrdersByAuthorizedUser() {
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");

        Response ingredientsResponse = orderClient.getIngredients();
        List<String> ingredientIds = ingredientsResponse.path("data._id");

        Response createOrderResponse = orderClient.createOrder(ingredientIds, accessToken);
        createOrderResponse.then().statusCode(200);

        Response orderResponse = orderClient.getOrders(accessToken);

        orderResponse.then()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Получить заказ пользователя без авторизации")
    @Description("Возвращает ошибку при попытке запроса заказа пользователя без авторизации")
    void getOrdersByUnauthorizedUser() {
        Response ordersResponse = orderClient.getOrders("");

        ordersResponse.then()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }
}
