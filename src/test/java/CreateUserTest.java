import api.Constants;
import api.user.UserRequestBody;
import api.user.UserService;
import api.user.StateUserService;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    private final StateUserService stateUserService = new StateUserService();
    private final UserService userService = new UserService();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    @After
    public void tearDown() {
        stateUserService.deleteUser();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    public void createNewUserTest() {
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yan.ru",
                "password",
                "Username"
        );
        Response response = stateUserService.createUser(requestBody);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Невозможность регистрации зарегистрированного пользователя")
    public void createTwoSameUserTest() {
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yan.ru",
                "password",
                "Username"
        );
        Response response = stateUserService.createUser(requestBody);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));

        response = userService.createUser(requestBody);
        response.then().assertThat().statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));
    }
}
