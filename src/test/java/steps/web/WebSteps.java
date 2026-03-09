package steps.web;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import poms.nopCommerce.POM_HeaderLogin;
import steps.hooks.Preconditions;

public class WebSteps {

    WebDriver driver = Preconditions.getDriver();

    POM_HeaderLogin pomHeaderLogin = new POM_HeaderLogin(driver);

    @Given("Access to the url {string}")
    public void access_to_url(String url) {
        System.out.println("Navegating to the page: <" + url + "> ...");
        driver.get(url);
    }

    @Then("Assert that the user is in the page")
    public void verifyPage() {
        pomHeaderLogin.checkLogo();
   }


}
