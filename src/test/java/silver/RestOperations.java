package silver;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestOperations {

    private static String token = null;

    public static Response sendRequest(String baseURL, Endpoint endpoint) {

        String method = endpoint.getMethod();
        String path = endpoint.getPath();

        RequestSpecification specs = given()
                .filter(new AllureRestAssured())
                .baseUri(baseURL)
                .basePath(path)
                .contentType(ContentType.JSON);

        if (endpoint.getPayload() != null)
            specs = specs.body(endpoint.getPayload().toJson());

        if (token != null)
            specs = specs.header(new Header("Authorization", "Bearer " + token));

        switch (method) {
            case "POST":
                return specs.post();
            case "PUT":
                return specs.put();
            case "GET":
                return specs.get();
            case "PATCH":
                return specs.patch();
            case "DELETE":
                return specs.delete();
            default:
                Assertions.fail("The operation <" + method + "> is not supported");
                return null;
        }
    }

    public static void setToken(String token) {
        RestOperations.token = token;
    }
}
