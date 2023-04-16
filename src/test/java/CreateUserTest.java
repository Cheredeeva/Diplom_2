import helpers.CreateUserRequestBody;
import helpers.UserService;
import helpers.UserServiceHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    private final UserServiceHelper userServiceHelper = new UserServiceHelper();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public  void tearDown() {
        userServiceHelper.deleteUser();
    }

    @Test
    public void createNewUserTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yan.ru",
                "password",
                "Username"
        );
        Response response = userServiceHelper.createUser(requestBody);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void createTwoSameUserTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yan.ru",
                "password",
                "Username"
        );
        Response response = userServiceHelper.createUser(requestBody);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        response = UserService.createUser(requestBody);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }
}
