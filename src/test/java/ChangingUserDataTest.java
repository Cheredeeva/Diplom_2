import helpers.CreateUserRequestBody;
import helpers.LoginUserRequestBody;
import helpers.UserService;
import helpers.UserServiceHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class ChangingUserDataTest {

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
    public void changingUserDataTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yandexx.ru",
                "wordpass",
                "UserUser"
        );
        Response response = userServiceHelper.updateUser(requestBody);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .body("user.name", equalTo(requestBody.name))
                .and()
                .body("user.email", equalTo(requestBody.email))
                .and()
                .statusCode(200);

        userServiceHelper.loginUser(new LoginUserRequestBody(requestBody.email, requestBody.password));
    }

    @Test
    public void updateUserWithoutAuthorizationTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yandexx.ru",
                "wordpass",
                "UserUser"
        );
        Response response = UserService.updateUserWithoutAuthorization(requestBody);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
