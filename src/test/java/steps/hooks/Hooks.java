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

public class Hooks {

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
                    beforeScenarioWeb(scenario.getSourceTagNames());
                    break;
                case "@Mobile":
                    break;
                default:
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

                    break;
                default:
                    break;
            }

        }
    }

    private void afterScenarioWeb() {
        if (driver != null) {
            driver.quit();
        }
    }

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

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        Hooks.driver = driver;
    }

    public static boolean isDebugScreenshot() {
        return debugScreenshot;
    }

    public static void setDebugScreenshot(boolean debugScreenshot) {
        Hooks.debugScreenshot = debugScreenshot;
    }

    public static String getScenarioName() {
        return scenarioName.replaceAll(" ", "-");
    }

    public static void setScenarioName(String scenarioName) {
        Hooks.scenarioName = scenarioName;
    }

}
