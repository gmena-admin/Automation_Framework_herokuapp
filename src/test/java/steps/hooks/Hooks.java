package steps.hooks;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Cucumber hooks class responsible for initializing and tearing down browser sessions.
 */
public class Hooks {

    private static WebDriver driver;
    private static boolean debugScreenshot = false;
    private static String scenarioName = "";

    /**
     * Runs before each scenario and configures the browser based on tags.
     *
     * @param scenario the current Cucumber scenario
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        if (System.getProperty("screenshot") != null)
            debugScreenshot = System.getProperty("screenshot").toLowerCase().equals("true");

        setScenarioName(scenario.getName());

        for (String tag : scenario.getSourceTagNames()) {
            switch (tag) {
                case "@WEB":
                    beforeScenarioWeb(scenario.getSourceTagNames());
                    break;
                default:
            }
        }
    }

    /**
     * Runs after each scenario and closes the browser if it was started.
     *
     * @param scenario the current Cucumber scenario
     */
    @After
    public void afterScenario(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            switch (tag) {
                case "@WEB":
                    afterScenarioWeb();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Closes the browser session for web tests.
     */
    private void afterScenarioWeb() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Starts a browser session based on the selected browser tag.
     *
     * @param collection the scenario tags to inspect for browser selection
     */
    private void beforeScenarioWeb(Collection<String> collection) {
        int typeOfBrowser = checkBrowserTags(collection);

        switch (typeOfBrowser) {
            case 1:
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("--headless");
                optionsChrome.addArguments("start-maximized");
                driver = new ChromeDriver(optionsChrome);
                break;
            case 2:
                EdgeOptions optionsEdge = new EdgeOptions();
                optionsEdge.addArguments("--headless");
                optionsEdge.addArguments("start-maximized");
                driver = new EdgeDriver(optionsEdge);
                break;
            default:
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("--headless");
                optionsFirefox.addArguments("start-maximized");
                driver = new FirefoxDriver(optionsFirefox);
                break;
        }
    }

    /**
     * Determines which browser tag is active in the scenario and ensures only one is selected.
     *
     * @param collection the scenario tags
     * @return an integer code representing the selected browser
     */
    private int checkBrowserTags(Collection<String> collection) {
        boolean isChrome = false;
        boolean isEdge = false;
        boolean isFirefox = false;

        for (String tag : collection) {
            tag = tag.toLowerCase();
            switch (tag) {
                case "@chrome":
                    isChrome = true;
                    break;
                case "@edge":
                    isEdge = true;
                    break;
                case "@firefox":
                    isFirefox = true;
                    break;
                default:
                    break;
            }
        }

        if (isChrome && !isEdge && !isFirefox)
            return 1;
        else if (!isChrome && isEdge && !isFirefox)
            return 2;
        else if (!isChrome && !isEdge && isFirefox)
            return 3;
        else if (!isChrome && !isEdge && !isFirefox)
            return 3;
        else
            Assertions.fail("Only 1 browser can be selected. Multiple browsers are not yet supported");
        return 0;
    }

    /**
     * Returns the current WebDriver instance.
     *
     * @return the active WebDriver
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Allows tests to override the WebDriver instance.
     *
     * @param driver the WebDriver to set
     */
    public static void setDriver(WebDriver driver) {
        Hooks.driver = driver;
    }

    /**
     * Returns whether debug screenshots are enabled.
     *
     * @return true when screenshot mode is enabled
     */
    public static boolean isDebugScreenshot() {
        return debugScreenshot;
    }

    /**
     * Enables or disables debug screenshots.
     *
     * @param debugScreenshot true to enable screenshot mode
     */
    public static void setDebugScreenshot(boolean debugScreenshot) {
        Hooks.debugScreenshot = debugScreenshot;
    }

    /**
     * Returns the current scenario name sanitized for attachments.
     *
     * @return the scenario name with spaces replaced by hyphens
     */
    public static String getScenarioName() {
        return scenarioName.replaceAll(" ", "-");
    }

    /**
     * Stores the current scenario name.
     *
     * @param scenarioName the scenario name to store
     */
    public static void setScenarioName(String scenarioName) {
        Hooks.scenarioName = scenarioName;
    }

}
