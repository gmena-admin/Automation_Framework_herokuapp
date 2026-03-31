package silver;

import com.google.gson.annotations.SerializedName;

public class Environment {

    @SerializedName("name_env")
    String nameEnv;
    @SerializedName("base_url")
    String baseUrl;

    @SerializedName("user")
    String username;
    @SerializedName("pass")
    String password;
    

    public Environment() {
        nameEnv = "";
        baseUrl = "";
        username = "";
        password = "";
    }

    public String getNameEnv() {
        return nameEnv;
    }

    public void setNameEnv(String nameEnv) {
        this.nameEnv = nameEnv;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Environment [nameEnv=" + nameEnv + ", baseUrl=" + baseUrl + ", username=" + username + ", password="
                + password + "]";
    }
}
