import api.user.*;
import api.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import static org.hamcrest.Matchers.*;

public class LoginUserTest {
    private final StateUserService stateUserService = new StateUserService();
    private final UserService userService = new UserService();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        stateUserService.createUser(requestBody);
    }

    @After
    public void tearDown() {
        stateUserService.deleteUser();
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    public void loginUserTest() {
        LoginUserRequestBody requestBody = new LoginUserRequestBody(
                "chereder@yan.ru",
                "password"
        );
        Response response = userService.loginUser(requestBody);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));
    }
}
