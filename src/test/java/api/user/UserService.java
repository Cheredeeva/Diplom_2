package api.user;

import api.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {
    @Step("Создание пользователя")
    public Response createUser(UserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.REGISTER_PATH);
    }

    @Step("Аутентификация пользователя")
    public Response loginUser(LoginUserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.LOGIN_PATH);
    }

    @Step("Обновление данных пользователя")
    public Response updateUser(UserRequestBody requestBody, String accessToken) {
        return given()
                .auth()
                .oauth2(getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .patch(Constants.USER_PATH);
    }

    @Step("Обновление данных пользователя без авторизации")
    public Response updateUserWithoutAuthorization(UserRequestBody requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .patch(Constants.USER_PATH);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .auth()
                .oauth2(getBearerToken(accessToken))
                .header("Content-type", "application/json")
                .when()
                .delete(Constants.USER_PATH);
    }

    public static String getBearerToken(String accessToken) {
        return accessToken.replaceFirst("Bearer ", "");
    }
}
