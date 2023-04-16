package helpers;

import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class UserServiceHelper {
    public String accessToken;

    public Response createUser(CreateUserRequestBody requestBody) {
        Response response = UserService.createUser(requestBody);
        accessToken = response.as(CreateUserResponseBody.class).accessToken;
        Assert.assertNotNull(accessToken);
        return response;
    }

    public Response loginUser(LoginUserRequestBody requestBody) {
        Response response = UserService.loginUser(requestBody);
        accessToken = response.as(CreateUserResponseBody.class).accessToken;
        Assert.assertNotNull(accessToken);
        return response;
    }

    public Response updateUser(CreateUserRequestBody requestBody) {
        Assert.assertNotNull(accessToken);
        return UserService.updateUser(requestBody, accessToken);
    }

    public void deleteUser() {
        Assert.assertNotNull(accessToken);
        UserService.deleteUser(accessToken);
    }
}
