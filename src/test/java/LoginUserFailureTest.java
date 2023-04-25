import api.user.*;
import api.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class LoginUserFailureTest {
    private final String email;
    private final String password;

    private final StateUserService stateUserService = new StateUserService();
    private final UserService userService = new UserService();

    public LoginUserFailureTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}")
    public static Object[][] getData() {
        return new Object[][]{
                {"cherder@yan.ru", "password"},
                {"chereder@yan.ru", "pasword"},
        };
    }

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
    @DisplayName("Невозможность авторизации с неверной почтой или паролем")
    public void loginUserFailureTest() {
        LoginUserRequestBody requestBody = new LoginUserRequestBody(email, password);
        Response response = userService.loginUser(requestBody);
        response.then().assertThat().statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}
