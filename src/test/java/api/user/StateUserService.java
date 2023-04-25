package api.user;

import io.restassured.response.Response;
import org.junit.Assert;

public class StateUserService {
    public String accessToken;
    private final UserService service = new UserService();

    public Response createUser(UserRequestBody requestBody) {
        Response response = service.createUser(requestBody);
        accessToken = response.as(UserResponseBody.class).accessToken;
        Assert.assertNotNull(accessToken);
        return response;
    }

    public Response loginUser(LoginUserRequestBody requestBody) {
        Response response = service.loginUser(requestBody);
        accessToken = response.as(UserResponseBody.class).accessToken;
        Assert.assertNotNull(accessToken);
        return response;
    }

    public Response updateUser(UserRequestBody requestBody) {
        Assert.assertNotNull(accessToken);
        return service.updateUser(requestBody, accessToken);
    }

    public void deleteUser() {
        Assert.assertNotNull(accessToken);
        service.deleteUser(accessToken);
    }
}
