package silver;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class REST {

    private String endpoint;
    private Reader reader;

    private String payload;

    public REST(String endpoint) {
        this.endpoint = endpoint;
        reader = new Reader();
        payload = "";
    }

    public void setPayload(String filename) {
        payload = reader.readJSON(filename);
    }

    public Response post(String path, String... params) {
        Response response = null;

        RequestSpecification specs = getSpecs(path, false, params);
        response = specs.when().post();

        return response;
    }

    public Response get(String path, String... params) {
        Response response = null;

        RequestSpecification specs = getSpecs(path, true, params);
        response = specs.when().get();

        return response;
    }

    public Response put(String path, String... params) {
        Response response = null;

        RequestSpecification specs = getSpecs(path, true, params);
        response = specs.when().put();

        return response;
    }

    public Response delete(String path, String... params) {
        Response response = null;

        RequestSpecification specs = getSpecs(path, true, params);
        response = specs.when().delete();

        return response;
    }

        public Response patch(String path, String... params) {
        Response response = null;

        RequestSpecification specs = getSpecs(path, true, params);
        response = specs.when().patch();

        return response;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private RequestSpecification getSpecs(String path, boolean isPayload, String... params) {
        RequestSpecification specs;
        if (isPayload)
            specs = given().relaxedHTTPSValidation().baseUri(endpoint).basePath(path).body(payload);
        else
            specs = given().relaxedHTTPSValidation().baseUri(endpoint).basePath(path);

        if (params.length > 0 && params.length % 2 == 0) {
            for (int i = 0; i < params.length; i = i + 2) {
                specs = specs.param(params[i], params[i + 1]);
            }
        }
        return specs;
    }

    public void validateStatusCode(Response response, int expectedCode) {

        int statusCode = response.getStatusCode();
        Assertions.assertEquals(expectedCode, statusCode, "The StatusCode received is not the expected");

    }

}
