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
        System.out.println("After Scenario Default");
    }

    private void afterScenarioMobile() {
        System.out.println("After Scenario Mobile");

    }

    private void afterScenarioWeb() {
        System.out.println("After Scenario Web");
        if (driver != null) {
            driver.close();
        }
    }

    private void beforeScenarioDefault() {
        System.out.println("Scenario Default");
    }

    private void beforeScenarioMobile() {
        System.out.println("Scenario Mobile");
    }

    private void beforeScenarioWeb() {
        System.out.println("Scenario Web");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
    }

}
