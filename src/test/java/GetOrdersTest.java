import helpers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {

    private final UserServiceHelper userServiceHelper = new UserServiceHelper();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        userServiceHelper.createUser(requestBody);

        CreateOrderRequestBody orderRequestBody = new CreateOrderRequestBody(
                List.of("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6c")
        );
        OrderService.createOrder(orderRequestBody, userServiceHelper.accessToken);
    }

    @After
    public  void tearDown() {
        userServiceHelper.deleteUser();
    }

    @Test
    public void getOrdersTest() {
        Response response = OrderService.getOrders(userServiceHelper.accessToken);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void getOrdersWithoutAuthorizationTest() {
        Response response = OrderService.getOrdersWithoutAuthorization();
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
