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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final boolean isAuthorized;
    private final StateUserService stateUserService = new StateUserService();
    private final OrderService orderService = new OrderService();

    public CreateOrderTest(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    @Parameterized.Parameters(name = "Пользователь авторизован: {0}")
    public static Object[][] getData() {
        return new Object[][] {
                {true},
                {false}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        if (isAuthorized) {
            UserRequestBody requestBody = new UserRequestBody(
                    "chereder@yan.ru",
                    "password",
                    "User"
            );
            stateUserService.createUser(requestBody);
        }
    }

    @After
    public  void tearDown() {
        if (isAuthorized) {
            stateUserService.deleteUser();
        }
    }

    @Test
    @DisplayName("Создание закза без авторизации")
    public void createOrderWithAuthorizationTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6c")
        );
        Response response = isAuthorized
                ? orderService.createOrder(requestBody, stateUserService.accessToken)
                : orderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

}
