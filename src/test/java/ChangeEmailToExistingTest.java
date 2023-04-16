import helpers.CreateUserRequestBody;
import helpers.UserServiceHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;


public class ChangeEmailToExistingTest {
    private final UserServiceHelper updatedUserServiceHelper = new UserServiceHelper();
    private final UserServiceHelper existingUserServiceHelper = new UserServiceHelper();
    private CreateUserRequestBody existingUserRequestBody;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                "chereder@yan.ru",
                "password",
                "User"
        );
        updatedUserServiceHelper.createUser(requestBody);

        existingUserRequestBody = new CreateUserRequestBody(
                "chere@yan.ru",
                "password",
                "User"
        );
        existingUserServiceHelper.createUser(existingUserRequestBody);
    }

    @After
    public  void tearDown() {
        updatedUserServiceHelper.deleteUser();
        existingUserServiceHelper.deleteUser();
    }

    @Test
    public void changeEmailToExistingTest() {
        CreateUserRequestBody requestBody = new CreateUserRequestBody(
                existingUserRequestBody.email,
                "wordpass",
                "UserUser"
        );
        Response response = updatedUserServiceHelper.updateUser(requestBody);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("User with such email already exists"))
                .and()
                .statusCode(403);
    }
}
