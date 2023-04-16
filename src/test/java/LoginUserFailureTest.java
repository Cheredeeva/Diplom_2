import helpers.*;
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

    private final UserServiceHelper userServiceHelper = new UserServiceHelper();
    private final String email;
    private final String password;

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
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        Response response = userServiceHelper.createUser(requestBody);
    }

    @After
    public  void tearDown() {
        userServiceHelper.deleteUser();
    }

    @Test
    public void loginUserFailureTest() {
        LoginUserRequestBody requestBody = new LoginUserRequestBody(email, password);
        Response response = UserService.loginUser(requestBody);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }
}
