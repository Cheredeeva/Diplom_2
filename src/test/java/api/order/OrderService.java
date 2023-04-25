package api.order;

import api.Constants;
import api.user.UserService;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderService {
    @Step("Создание заказа")
    public Response createOrder(CreateOrderRequestBody requestBody, String accessToken) {
        return given()
                .auth()
                .oauth2(UserService.getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.ORDERS_PATH);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(CreateOrderRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.ORDERS_PATH);
    }

    @Step("Получение заказа")
    public Response getOrders(String accessToken) {
        return given()
                .auth()
                .oauth2(UserService.getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .when()
                .get(Constants.ORDERS_PATH);
    }

    @Step("Получение заказа без авторизации")
    public Response getOrdersWithoutAuthorization() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(Constants.ORDERS_PATH);
    }

}
