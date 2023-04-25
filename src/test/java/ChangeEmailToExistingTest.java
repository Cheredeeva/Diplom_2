import api.Constants;
import api.user.UserRequestBody;
import api.user.StateUserService;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeEmailToExistingTest {
    private final StateUserService updatedStateUserService = new StateUserService();
    private final StateUserService existingStateUserService = new StateUserService();
    private UserRequestBody existingUserRequestBody;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        UserRequestBody requestBody = new UserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        updatedStateUserService.createUser(requestBody);

        existingUserRequestBody = new UserRequestBody(
                "chere@yan.ru",
                "password",
                "User"
        );
        existingStateUserService.createUser(existingUserRequestBody);
    }

    @After
    public void tearDown() {
        updatedStateUserService.deleteUser();
        existingStateUserService.deleteUser();
    }

    @Test
    @DisplayName("Невозможность изменения почты пользователя на уже использываемую")
    public void changeEmailToExistingTest() {
        UserRequestBody requestBody = new UserRequestBody(
                existingUserRequestBody.email,
                "wordpass",
                "UserUser"
        );
        Response response = updatedStateUserService.updateUser(requestBody);
        response.then().assertThat().statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("User with such email already exists"));
    }
}
