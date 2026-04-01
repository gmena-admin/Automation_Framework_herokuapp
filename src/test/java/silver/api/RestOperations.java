package silver.api;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Utility class to build and send REST API requests using REST Assured.
 */
public class RestOperations {

    private static String token = null;

    /**
     * Sends an HTTP request to the configured endpoint using the selected method.
     *
     * @param baseURL  the base URI for the API
     * @param endpoint the endpoint definition containing path, method, and payload
     * @return the REST Assured response object
     */
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

    /**
     * Stores the bearer token used for authenticated API calls.
     *
     * @param token the token string to use for future requests
     */
    public static void setToken(String token) {
        RestOperations.token = token;
    }
}
