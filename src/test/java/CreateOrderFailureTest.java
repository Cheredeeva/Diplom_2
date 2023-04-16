import helpers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderFailureTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    public void createOrderWithoutIngredientsTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of()
        );
        Response response = OrderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    @Test
    public void createOrderWithInvalidIngredientsTest() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                List.of("invalidhash", "61c0c5a71d1f82001bdaaa6f")
        );
        Response response = OrderService.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat()
                .statusCode(500);
    }

}
