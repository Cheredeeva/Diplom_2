import api.Constants;
import api.order.CreateOrderRequestBody;
import api.order.OrderService;
import api.user.UserRequestBody;
import api.user.StateUserService;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {
    private final StateUserService stateUserService = new StateUserService();
    private final OrderService orderService = new OrderService();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        stateUserService.createUser(requestBody);

        CreateOrderRequestBody orderRequestBody = new CreateOrderRequestBody(
                List.of("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6c")
        );
        orderService.createOrder(orderRequestBody, stateUserService.accessToken);
    }

    @After
    public void tearDown() {
        stateUserService.deleteUser();
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getOrdersTest() {
        Response response = orderService.getOrders(stateUserService.accessToken);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Невозможность получения заказов не авторизованного пользователя")
    public void getOrdersWithoutAuthorizationTest() {
        Response response = orderService.getOrdersWithoutAuthorization();
        response.then().assertThat().statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
