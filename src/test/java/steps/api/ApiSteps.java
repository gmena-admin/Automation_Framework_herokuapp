package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import silver.Endpoint;
import silver.Payload;
import silver.REST;
import silver.RestOperations;
import silver.UtilityReader;

public class ApiSteps {

    private REST apiServices;
    private Endpoint endpoint;
    private Response response;
    private int rowNumber;

    @Given("The API tests are running under {string} environment and using the catalog {string}")
    public void setEnvironment(String environment, String catalog) {
        apiServices = new REST(environment, catalog);
    }

    @Given("I login into the system")
    public void loginIntoSystem() {
        REST apiLoginServices = new REST(apiServices.getEnvironmentName(), "Users");

        Endpoint endpointLogin = null;
        for (Endpoint endpointFromList : apiLoginServices.getCatalog().getListEndpoint())
            if (endpointFromList.getName().equals("Log In User")) {
                endpointLogin = endpointFromList;
            }

        Response responseLogin = RestOperations.sendRequest(apiLoginServices.getBaseURL(), endpointLogin);
        String token = responseLogin.then()
                .statusCode(200)
                .extract().body().path("token");
        RestOperations.setToken(token);
    }

    @Given("I include the information for a {string} operation using the dataset {string} for row {int}")
    public void sayText(String operationName, String datasetFileName, int rowNumber) {
        this.endpoint = apiServices.getEndpoint(operationName);

        Payload payload = this.endpoint.setValuesInPayload(datasetFileName, rowNumber, endpoint.getPayload());
        this.endpoint.setPayload(payload);
        this.rowNumber = rowNumber;
    }

    @When("I send the operation")
    public void sendOperation() {
        this.response = RestOperations.sendRequest(apiServices.getBaseURL(), endpoint);
    }

    @Then("The status code is {int}")
    public void checkStatusCode(int expectedStatusCode) {
        this.response.then()
                .statusCode(expectedStatusCode);
    }

    @Then("Update dataset {string} to include {string} field from response")
    public void updateDataSet(String datasetFileName, String fieldName) {
        String fieldNameFromResponse = this.response.then().extract().body().path(fieldName);
        UtilityReader.writeValuesFromDataSet(datasetFileName, rowNumber, fieldName, fieldNameFromResponse);
    }
}
