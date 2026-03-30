package steps.hooks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Preconditions {

    private static WebDriver driver;
    private static boolean debugScreenshot = false;
    private static String scenarioName = "";

    @Before
    public void beforeScenario(Scenario scenario) {

        if (System.getProperty("screenshot") != null)
            debugScreenshot = System.getProperty("screenshot").toLowerCase().equals("true");

        setScenarioName(scenario.getName());
        
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
        // To be Implemented
    }

    private void afterScenarioMobile() {
        // To be Implemented
    }

    private void afterScenarioWeb() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void beforeScenarioDefault() {
        // To be Implemented
    }

    private void beforeScenarioMobile() {
        // To be Implemented
    }

    private void beforeScenarioWeb() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        Preconditions.driver = driver;
    }

    public static boolean isDebugScreenshot() {
        return debugScreenshot;
    }

    public static void setDebugScreenshot(boolean debugScreenshot) {
        Preconditions.debugScreenshot = debugScreenshot;
    }

    public static String getScenarioName() {
        return scenarioName.replaceAll(" ", "-");
    }

    public static void setScenarioName(String scenarioName) {
        Preconditions.scenarioName = scenarioName;
    }

}
