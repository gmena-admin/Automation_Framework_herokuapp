package api.payloads;

import com.google.gson.Gson;

import silver.api.Payload;

public class AddUser implements Payload {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public AddUser() {
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
        return "AddUser [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName="
                + lastName + "]";
    }

    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
            case "lastName":
                setLastName(fieldValue);
                break;
            case "firstName":
                setFirstName(fieldValue);
                break;
            default:
                break;
        }

    }
}
