import helpers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import static org.hamcrest.Matchers.*;

public class LoginUserTest {

    private final UserServiceHelper userServiceHelper = new UserServiceHelper();

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
    public void createNewUserTest() {
        LoginUserRequestBody requestBody = new LoginUserRequestBody(
                "chereder@yan.ru",
                "password"
        );
        Response response = UserService.loginUser(requestBody);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
}
