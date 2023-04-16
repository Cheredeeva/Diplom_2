package helpers;

public class CreateUserRequestBody {

    public final String email;
    public final String password;
    public final String name;

    public CreateUserRequestBody(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
