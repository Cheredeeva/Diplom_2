package helpers;

public class LoginUserRequestBody {

    private final String email;
    private final String password;

    public LoginUserRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
