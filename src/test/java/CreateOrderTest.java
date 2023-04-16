import helpers.*;
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
    private final UserServiceHelper userServiceHelper = new UserServiceHelper();

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
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        if (isAuthorized) {
            CreateUserRequestBody requestBody = new CreateUserRequestBody(
                    "chereder@yan.ru",
                    "password",
                    "User"
            );
            userServiceHelper.createUser(requestBody);
        }
    }

    @After
    public  void tearDown() {
        if (isAuthorized) {
            userServiceHelper.deleteUser();
        }
    }

    @Test
    public void createOrderWithAuthorizationTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6c")
        );
        Response response = isAuthorized
                ? OrderService.createOrder(requestBody, userServiceHelper.accessToken)
                : OrderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
