package silver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class POM {

    private WebDriver driver;
    private XpathReader xpathReader;
    private int indexScreenshot = 0;

    public static final int SHORTEST_TIMEOUT = 2000;
    public static final int SHORT_TIMEOUT = 5000;
    public static final int LONG_TIMEOUT = 30000;
    public static final int LONGEST_TIMEOUT = 60000;

    public POM(WebDriver driver) {
        this.driver = driver;
        this.xpathReader = new XpathReader();
    }

    public void clickOn(String elementName, String... propertyList) {

        WebElement element = find(false, SHORTEST_TIMEOUT, elementName, propertyList);

        element.click();

    }

    public void writeIn(String elementName, String text, String... propertyList) {

        WebElement element = find(false, SHORTEST_TIMEOUT, elementName, propertyList);

        element.sendKeys(text);

    }

    public WebElement find(boolean takeScreenshot, int timeout, String elementName, String... propertyList) {

        String property = xpathReader.readProperty(elementName, propertyList);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(property)));

        Assert.assertNotNull("The element <" + property + "> has not been found in the website", element);

        if (takeScreenshot)
            takeScreenshot(element);

        return element;

    }

    public void not_find(boolean takeScreenshot, int timeout, String elementName, String... propertyList) {

        String property = xpathReader.readProperty(elementName, propertyList);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(property)));

        Assert.assertNull("The element <" + property + "> has been found in the website", element);

        if (takeScreenshot)
            takeScreenshot(null);

    }

    public void takeScreenshot(WebElement elementNode) {
        try {
            if (elementNode != null) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                // highlight the element with red border 3px width
                jse.executeScript("arguments[0].style.border='5px solid yellow'", elementNode);
            }
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(scrFile, new File("screenshot_" + ++indexScreenshot + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
