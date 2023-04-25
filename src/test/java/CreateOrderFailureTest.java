import api.Constants;
import api.order.CreateOrderRequestBody;
import api.order.OrderService;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderFailureTest {
    private final OrderService orderService = new OrderService();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    @Test
    @DisplayName("Невозможность создания заказа без ингридиентов")
    public void createOrderWithoutIngredientsTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of()
        );
        Response response = orderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat().statusCode(400)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Невозможность создания заказа с невалидныим хешом ингредиента")
    public void createOrderWithInvalidIngredientsTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of("invalidhash", "61c0c5a71d1f82001bdaaa6f")
        );
        Response response = orderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat()
                .statusCode(500);
    }

}
