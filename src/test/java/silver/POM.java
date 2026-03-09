package silver;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;

public class POM {

    private WebDriver driver;
    private Reader reader;
    private int indexScreenshot = 0;

    public static final int SHORTEST_TIMEOUT = 2000;
    public static final int SHORT_TIMEOUT = 5000;
    public static final int LONG_TIMEOUT = 30000;
    public static final int LONGEST_TIMEOUT = 60000;

    public POM(WebDriver driver) {
        this.driver = driver;
        this.reader = new Reader();
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

        String property = reader.readProperty(elementName, propertyList);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(property)));

        if (takeScreenshot)
            takeScreenshot(element);

        Assertions.assertNotNull(element, "No element <" + elementName + "> has been found in the website");

        return element;

    }

    public void not_find(boolean takeScreenshot, int timeout, String elementName, String... propertyList) {

        String property = reader.readProperty(elementName, propertyList);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));

        boolean element = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(property)));

        if (takeScreenshot)
            takeScreenshot(null);

        Assertions.assertTrue(element, "An element <" + elementName + "> has been found in the website");

    }

    public void takeScreenshot(WebElement elementNode) {
        try {

            byte[] scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("screenshot_withNo_highlight" + ++indexScreenshot, "image/png",
                    new java.io.ByteArrayInputStream(scrFile), ".png");

            if (elementNode != null) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                // highlight the element with red border 5px width
                jse.executeScript("arguments[0].style.border='5px solid yellow'", elementNode);

                scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment("screenshot_with_highlight_" + indexScreenshot, "image/png",
                        new java.io.ByteArrayInputStream(scrFile), ".png");

                jse = (JavascriptExecutor) driver;
                // highlight the element with red border 5px width
                jse.executeScript("arguments[0].style.border='none'", elementNode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
