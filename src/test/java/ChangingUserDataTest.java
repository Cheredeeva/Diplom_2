import api.user.*;
import api.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class ChangingUserDataTest {
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
    @DisplayName("Изменение данных авторезированного пользователя")
    public void changingUserDataTest() {
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yandexx.ru",
                "wordpass",
                "UserUser"
        );
        Response response = stateUserService.updateUser(requestBody);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.name", equalTo(requestBody.name))
                .and()
                .body("user.email", equalTo(requestBody.email));

        stateUserService.loginUser(new LoginUserRequestBody(requestBody.email, requestBody.password));
    }

    @Test
    @DisplayName("Невозможность изменения данных не авторезированного пользователя")
    public void updateUserWithoutAuthorizationTest() {
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yandexx.ru",
                "wordpass",
                "UserUser"
        );
        Response response = userService.updateUserWithoutAuthorization(requestBody);
        response.then().assertThat().statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
