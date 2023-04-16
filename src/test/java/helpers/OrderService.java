package helpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderService {

    public static Response createOrder(CreateOrderRequestBody requestBody, String accessToken) {
        return given()
                .auth()
                .oauth2(UserService.getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/orders");
    }

    public static Response createOrderWithoutAuthorization(CreateOrderRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/orders");
    }

    public static Response getOrders(String accessToken) {
        return given()
                .auth()
                .oauth2(UserService.getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders");
    }

    public static Response getOrdersWithoutAuthorization() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders");
    }

}
