package steps.api;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import silver.REST;

public class ApiSteps {

    private REST apiServices;
    private Response response;

    @Given("Having {string} as the endpoint")
    public void setEndpoint(String endpoint) {

        apiServices = new REST(endpoint);
    }

    @When("Using \"{string}\" file as the payload")
    public void loadPayload(String filename) {
        apiServices.setPayload(filename);
    }

    @When("The request is sent by {string}")
    public void sendRequest(String operation) {
        switch (operation) {
            case "POST":
                response = apiServices.post("");
                break;
            case "GET":
                response = apiServices.get("");
                break;

            case "PUT":
                response = apiServices.put("");
                break;

            case "DELETE":
                response = apiServices.delete("");
                break;

            default:
                Assert.fail("The operation <" + operation + "> is not yet supported");
                break;
        }
    }

    @Then("The {string} should be {string}")
    public void validateParameter(String parameterName, String expectedValue) {
        switch (parameterName) {
            case "StatusCode":
                apiServices.validateStatusCode(response, Integer.parseInt(expectedValue));
                break;

            default:
                Assert.fail("The operation <" + parameterName + "> is not yet supported");
                break;
        }
    }

}
