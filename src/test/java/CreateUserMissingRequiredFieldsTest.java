import helpers.CreateUserRequestBody;
import helpers.UserService;
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
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    public void createUserMissingRequiredFieldsTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(email, password, name);
        Response response = UserService.createUser(requestBody);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}
