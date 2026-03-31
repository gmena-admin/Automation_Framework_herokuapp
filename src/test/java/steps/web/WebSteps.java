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
import steps.hooks.Preconditions;

public class WebSteps {

    private WebDriver driver = Preconditions.getDriver();

    private POM_Login pomLogin = new POM_Login(driver);
    private POM_SignUp pomSignUp = new POM_SignUp(driver);
    private POM_ContactList pomContactList = new POM_ContactList(driver);

    @Given("Access to the url {string}")
    public void access_to_url(String url) {
        System.out.println("Navegating to the page: <" + url + "> ...");
        driver.get(url);
    }

    @And("Assert that the user is in the page")
    public void verifyPage() {
        pomLogin.assertPage();
    }

    @Given("Access to \"Sign up\" to create a new contact")
    public void accessSite() {
        pomLogin.accessToSignUp();
        pomSignUp.assertPage();

    }

    @When("Fullfill the information {string}, {string}, {string} and {string}")
    public void fullfill_the_information(String firstName, String lastName, String email, String password) {

        pomSignUp.writeField("firstName", firstName);
        pomSignUp.writeField("lastName", lastName);
        pomSignUp.writeField("email", email);
        pomSignUp.writeField("password", password);

    }

    @When("Submit the user")
    public void submit_the_user() {
        pomSignUp.pushButton("Submit");
    }

    @When("Fullfill the information {string} and {string}")
    public void fullfill_the_information_and(String email, String password) {
        pomLogin.writeField("email", email);
        pomLogin.writeField("password", password);
    }

    @When("Log in")
    public void log_in() {
        pomLogin.logIn();
    }

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

    @Then("The user is created properly")
    public void the_user_is_created_properly() {
        pomSignUp.checkNoErrors();
        pomContactList.assertPage();

    }

    @Then("The user is logged properly")
    public void the_user_is_logged_properly() {
        pomLogin.checkNoErrors();
        pomContactList.assertPage();

    }

    @Then("the user is log out")
    public void the_user_is_log_out() {
        pomContactList.logOut();
    }

}
