package steps.web;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import poms.thinking_tester_contact_list.POM_ContactList;
import poms.thinking_tester_contact_list.POM_Login;
import poms.thinking_tester_contact_list.POM_SignUp;
import steps.hooks.Hooks;

/**
 * WebSteps contains the Cucumber step definitions for web UI scenarios.
 */
public class WebSteps {

    private WebDriver driver = Hooks.getDriver();

    private POM_Login pomLogin = new POM_Login(driver);
    private POM_SignUp pomSignUp = new POM_SignUp(driver);
    private POM_ContactList pomContactList = new POM_ContactList(driver);

    /**
     * Navigates the browser to the provided URL.
     *
     * @param url the target web page URL
     */
    @Given("Access to the url {string}")
    public void access_to_url(String url) {
        System.out.println("Navegating to the page: <" + url + "> ...");
        driver.get(url);
    }

    /**
     * Verifies that the login page is displayed.
     */
    @And("Assert that the user is in the page")
    public void verifyPage() {
        pomLogin.assertPage();
    }

    /**
     * Opens the sign-up page from the login screen.
     */
    @Given("Access to \"Sign up\" to create a new contact")
    public void accessSite() {
        pomLogin.accessToSignUp();
        pomSignUp.assertPage();
    }

    /**
     * Completes the signup form fields with the provided user data.
     *
     * @param firstName the first name value
     * @param lastName the last name value
     * @param email the email value
     * @param password the password value
     */
    @When("Fullfill the information {string}, {string}, {string} and {string}")
    public void fullfill_the_information(String firstName, String lastName, String email, String password) {
        pomSignUp.writeField("firstName", firstName);
        pomSignUp.writeField("lastName", lastName);
        pomSignUp.writeField("email", email);
        pomSignUp.writeField("password", password);
    }

    /**
     * Submits the signup form.
     */
    @When("Submit the user")
    public void submit_the_user() {
        pomSignUp.pushButton("Submit");
    }

    /**
     * Completes the login form fields.
     *
     * @param email the user email
     * @param password the user password
     */
    @When("Fullfill the information {string} and {string}")
    public void fullfill_the_information_and(String email, String password) {
        pomLogin.writeField("email", email);
        pomLogin.writeField("password", password);
    }

    /**
     * Clicks the login button.
     */
    @When("Log in")
    public void log_in() {
        pomLogin.logIn();
    }

    /**
     * Validates that the expected error message appears on the selected screen.
     *
     * @param page the screen name where the error should appear
     * @param reason the error reason key or comma-separated reasons
     */
    @Then("An error appeared in {string} --> {string}")
    public void error_is_showed(String page, String reason) {
        switch (page) {
            case "Login Screen":
                pomLogin.errorAppeared(reason);
                break;
            case "SignUp Screen":
                pomSignUp.errorAppeared(reason);
                break;
            default:
                Assertions.fail("The error handling for the screen < " + page + "> is not yet implemented");
                break;
        }
    }

    /**
     * Verifies that the user registration completed successfully.
     */
    @Then("The user is created properly")
    public void the_user_is_created_properly() {
        pomSignUp.checkNoErrors();
        pomContactList.assertPage();
    }

    /**
     * Verifies that the user login completed successfully.
     */
    @Then("The user is logged properly")
    public void the_user_is_logged_properly() {
        pomLogin.checkNoErrors();
        pomContactList.assertPage();
    }

    /**
     * Logs the user out from the contact list page.
     */
    @Then("the user is log out")
    public void the_user_is_log_out() {
        pomContactList.logOut();
    }

}
