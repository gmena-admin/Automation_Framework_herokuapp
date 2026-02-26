package steps.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.*;
import steps.hooks.Preconditions;

public class WebSteps {
 
    WebDriver driver = Preconditions.getDriver();

    @Given("Access to the url {string}")
    public void access_to_url(String url){
        System.out.println("Navegating to the page: <" + url + "> ...");
        driver.get(url);
    }

    @Then("Assert that the user is in the page")
    public void verifyPage(){
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Asserting...");
    }

}
