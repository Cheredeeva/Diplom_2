package helpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {
    public static Response createUser(CreateUserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/auth/register");
    }

    public static Response loginUser(LoginUserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/auth/login");
    }

    public static Response updateUser(CreateUserRequestBody requestBody, String accessToken) {
        return given()
                .auth()
                .oauth2(getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .patch("/api/auth/user");
    }

    public static Response updateUserWithoutAuthorization(CreateUserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .patch("/api/auth/user");
    }

    public static Response deleteUser(String accessToken) {
        return given()
                .auth()
                .oauth2(getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .when()
                .delete("/api/auth/user");
    }

    public static String getBearerToken(String accessToken) {
        return accessToken.replaceFirst("Bearer ", "");
    }
}
