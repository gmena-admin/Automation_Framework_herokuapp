package silver.ui;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;
import silver.Utilities.UtilityReader;
import steps.hooks.Hooks;

public class POM {

    protected WebDriver driver;
    protected UtilityReader reader;

    private int indexScreenshot = 0;

    public static final int SHORTEST_TIMEOUT = 2000;
    public static final int SHORT_TIMEOUT = 5000;
    public static final int LONG_TIMEOUT = 30000;
    public static final int LONGEST_TIMEOUT = 60000;

    public POM(WebDriver driver) {
        this.driver = driver;
        this.reader = new UtilityReader();
    }

    public void clickOn(String elementName, String... propertyList) {

        WebElement element = find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, elementName, propertyList);

        element.click();

    }

    public void writeIn(String text, String elementName, String... propertyList) {

        WebElement element = find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, elementName, propertyList);

        element.sendKeys(text);

    }

    public String getText(String elementName, String... propertyList) {

        WebElement element = find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, elementName, propertyList);

        return element.getText();

    }

    public WebElement find(boolean takeScreenshot, int timeout, String elementName, String... propertyList) {

        String property = reader.readProperty(elementName, propertyList);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        try {

            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(property)));

            if (takeScreenshot)
                takeScreenshot(element);

            return element;
        } catch (TimeoutException e) {
            takeScreenshot(null);
            Assertions.fail("No element <" + elementName + "> has been found in the website for this xpath: <"
                    + property + ">");
        }
        return null;
    }

    public void not_find(boolean takeScreenshot, int timeout, String elementName, String... propertyList) {
        String property = reader.readProperty(elementName, propertyList);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));

        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(property)));
            
            if (takeScreenshot)
                takeScreenshot(element);
            

            Assertions.fail("An element <" + elementName + "> has been found in the website");
            
        } catch (TimeoutException e) {
            if (takeScreenshot) {
                takeScreenshot(null);
            }
        }
    }

    public void takeScreenshot(WebElement elementNode) {
        try {

            if (elementNode == null) {
                byte[] scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(
                        Hooks.getScenarioName() + "_" + this.getClass().getSimpleName() + "_"
                                + ++indexScreenshot,
                        "image/png",
                        new java.io.ByteArrayInputStream(scrFile), ".png");

            } else {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                // highlight the element with red border 5px width
                jse.executeScript("arguments[0].style.border='5px solid yellow'", elementNode);

                byte[] scrFileWithJS = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(
                        Hooks.getScenarioName() + "__" + this.getClass().getSimpleName() + "_"
                                + ++indexScreenshot,
                        "image/png",
                        new java.io.ByteArrayInputStream(scrFileWithJS), ".png");

                jse = (JavascriptExecutor) driver;
                // highlight the element with red border 5px width
                jse.executeScript("arguments[0].style.border='none'", elementNode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
