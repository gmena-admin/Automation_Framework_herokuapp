package steps.api;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import silver.Utilities.UtilityReader;
import silver.api.Endpoint;
import silver.api.Payload;
import silver.api.REST;
import silver.api.RestOperations;

public class ApiSteps {

    private String[] headerNameList;
    private String[] valueNameList;
    private REST apiServices;
    private Endpoint endpoint;
    private Response response;
    private int numElements;

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
        int expectedStatusCode = 200;
        if (responseLogin.getStatusCode() == 401) {
            Endpoint endpointCreate = null;
            for (Endpoint endpointFromList : apiLoginServices.getCatalog().getListEndpoint())
                if (endpointFromList.getName().equals("Create User")) {
                    endpointCreate = endpointFromList;
                }

            responseLogin = RestOperations.sendRequest(apiLoginServices.getBaseURL(), endpointCreate);
            expectedStatusCode = 201;
        }

        String token = responseLogin.then()
                .statusCode(expectedStatusCode)
                .extract().body().path("token");
        RestOperations.setToken(token);
    }

    @Given("I delete my user")
    public void deleteUser() {
        REST apiLoginServices = new REST(apiServices.getEnvironmentName(), "Users");

        Endpoint endpointLogin = null;
        for (Endpoint endpointFromList : apiLoginServices.getCatalog().getListEndpoint())
            if (endpointFromList.getName().equals("Delete User")) {
                endpointLogin = endpointFromList;
            }

        RestOperations.sendRequest(apiLoginServices.getBaseURL(), endpointLogin);

    }

    @Given("I include the information for a {string} operation using the dataset {string} for row {int}")
    public void sayText(String operationName, String datasetFileName, int rowNumber) {
        this.endpoint = apiServices.getEndpoint(operationName);

        Payload payload = this.endpoint.setValuesInPayload(datasetFileName, rowNumber, endpoint.getPayload());
        this.endpoint.setPayload(payload);
    }

    @Given("I changed the field\\(s) {string} in the dataset {string} for row {int} to have value\\(s) {string}")
    public void changeFieldsInDataSet(String headerName, String datasetFileName, int rowNumber, String valueName) {

        this.headerNameList = headerName.split(",");
        this.valueNameList = valueName.split(",");

        Assertions.assertEquals(headerNameList.length, valueNameList.length,
                "There is a difference in the elements of headerName and valueName parameters. Both lists should have the same amount of items");

        for (int i = 0; i < headerNameList.length; i++) {
            String fieldName = headerNameList[i];
            String value = valueNameList[i];

            UtilityReader.writeValuesFromDataSet(datasetFileName, rowNumber, fieldName, value);
        }

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

    @Then("The response contains the same value in the field {string} using the data in the dataset {string} for row {int}")
    public void checkFieldValueInResponse(String fieldName, String datasetFileName, int rowNumber) {

        String[] fieldNameList = fieldName.split(",");

        for (String field : fieldNameList) {
            String fieldNameFromResponse = this.response.then().extract().body().path(field);
            String value = UtilityReader.getValueFromRow(datasetFileName, rowNumber, field);

            Assertions.assertEquals(value, fieldNameFromResponse,
                    "The values do not match: expected <" + value + "> - actual < " + fieldNameFromResponse + ">");
        }

    }

    @Then("Update dataset {string} in row {int} to include {string} field from response")
    public void updateDataSet(String datasetFileName, int rowNumber, String fieldName) {
        String fieldNameFromResponse = this.response.then().extract().body().path(fieldName);
        UtilityReader.writeValuesFromDataSet(datasetFileName, rowNumber, fieldName, fieldNameFromResponse);
    }

    @Then("With the data in dataset {string} for row {int}, the reason is {string}")
    public void checkReasonError(String datasetFileName, int rowNumber, String reasonName) {

        UtilityReader reader = new UtilityReader();
        String[] reasonNameList = reasonName.split(",");

        for (String reasonInList : reasonNameList) {

            String reason = reader.readErrorProperty(reasonInList);

            if (reasonInList.equals("addContact_longerPostalCode")) {
                String value = UtilityReader.getValueFromRow(datasetFileName, rowNumber, "postalCode");
                reason = reader.readErrorProperty(reasonInList, value);
            }

            if (reasonInList.equals("addContact_longerPhone")) {
                String value = UtilityReader.getValueFromRow(datasetFileName, rowNumber, "phone");
                reason = reader.readErrorProperty(reasonInList, value);
            }

            String message = this.response.then().extract().body().path("message");

            Assertions.assertTrue(message.contains(reason), "The message from the error < " + message
                    + " > does not contain the specific kind of error <" + reasonName + ">");
        }

    }

    @Then("I save the number of elements in the response")
    public void saveNumberElementsResponse() {
        List<Object> listElements = this.response.then().extract().jsonPath().getList("");
        this.numElements = listElements.size();
    }

    @Then("The response contains {int} {string} element from the previous call")
    public void compareNumElementsWithLastResponse(int numElementsExpected, String incrementType) {
        List<Object> listElements = this.response.then().extract().jsonPath().getList("");

        if (incrementType.equals("more"))
            Assertions.assertEquals(numElementsExpected + this.numElements, listElements.size(),
                    "The values do not match. expected <" + numElementsExpected + this.numElements + "> - actual <"
                            + listElements.size() + ">");
        if (incrementType.equals("less"))
            Assertions.assertEquals(this.numElements - numElementsExpected, listElements.size(),
                    "The values do not match. expected <" + (this.numElements - numElementsExpected) + "> - actual <"
                            + listElements.size() + ">");

        this.numElements = listElements.size();
    }
}
