package steps.hooks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Preconditions {

    private static WebDriver  driver;

    public static WebDriver getDriver() {
        return driver;
    }
    public static void setDriver(WebDriver driver) {
        Preconditions.driver = driver;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            switch (tag) {
                case "@WEB":
                    beforeScenarioWeb();
                    break;
                case "@Mobile":
                    beforeScenarioMobile();
                    break;
                default:
                    beforeScenarioDefault();
            }

        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            switch (tag) {
                case "@WEB":
                    afterScenarioWeb();
                    break;
                case "@Mobile":
                    afterScenarioMobile();
                    break;
                default:
                    afterScenarioDefault();
            }

        }
    }

    private void afterScenarioDefault() {
    }

    private void afterScenarioMobile() {

    }

    private void afterScenarioWeb() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void beforeScenarioDefault() {
        
    }

    private void beforeScenarioMobile() {
    }

    private void beforeScenarioWeb() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
    }

}
