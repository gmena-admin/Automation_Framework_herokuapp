package api.payloads;

import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;

import silver.Payload;

public class Login implements Payload {
    private String email;
    private String password;

    public Login() {
        email = "";
        password = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "\nLogin [email=" + email + ", password=" + password + "]";
    }

    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);

    }

    @Override
    public void setValue(String fieldName, String fieldValue) {

        switch (fieldName) {
            case "email":
                setEmail(fieldValue);
                break;
            case "password":
                setPassword(fieldValue);
                break;
            default:
                break;
        }

    }
}
