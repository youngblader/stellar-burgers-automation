package clients;

import api.config.ApiRoutes;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Создание заказа")
    public Response createOrder(List<String> ingredients, String accessToken) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("ingredients", ingredients);

        return given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(body)
                .post(ApiRoutes.ORDERS_ROUTE);
    }

    @Step("Получение ингредиентов")
    public Response getIngredients() {
        return given()
                .contentType("application/json")
                .get(ApiRoutes.INGREDIENTS_ROUTE);
    }

    @Step("Получить заказы")
    public Response getOrders(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .get(ApiRoutes.ORDERS_ROUTE);
    }
}
