package api.user;

public class LoginUserRequestBody {
    public final String email;
    public final String password;

    public LoginUserRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
