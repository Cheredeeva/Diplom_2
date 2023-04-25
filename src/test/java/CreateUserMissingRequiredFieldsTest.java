import api.Constants;
import api.user.UserRequestBody;
import api.user.UserService;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateUserMissingRequiredFieldsTest {
    private final String email;
    private final String password;
    private final String name;

    private final UserService userService = new UserService();

    public CreateUserMissingRequiredFieldsTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}, {2}")
    public static Object[][] getData() {
        return new Object[][] {
                { null, "12345", "Username"},
                { "cherederev@yan.ru", null, "Username"},
                { "cherederev@yan.ru", "12345", null},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    @Test
    @DisplayName("Невозможность регистрация с не заполненным полем ")
    public void createUserMissingRequiredFieldsTest() {
        UserRequestBody requestBody = new UserRequestBody(email, password, name);
        Response response = userService.createUser(requestBody);
        response.then().assertThat().statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
