package poms.thinking_tester_contact_list;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import silver.ui.POM;
import steps.hooks.Hooks;

/**
 * Page Object Model for the login screen of the Contact List app.
 */
public class POM_Login extends POM {

    public POM_Login(WebDriver driver) {
        super(driver);
    }

    /**
     * Verifies the login page UI is visible.
     */
    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Contact List App");
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "img_footer");
    }

    /**
     * Clicks the button to navigate to the sign-up form.
     */
    public void accessToSignUp() {
        clickOn("button_by_id", "signup");
    }

    /**
     * Clicks the login submit button.
     */
    public void logIn() {
        clickOn("button_by_id", "submit");
    }

    /**
     * Enters a field value into the login form.
     *
     * @param fieldName the field id used by the locator repository
     * @param fieldValue the value to enter into the form field
     */
    public void writeField(String fieldName, String fieldValue) {
        if (fieldName == null)
            writeIn("", "input_by_id", fieldName);

        writeIn(fieldValue, "input_by_id", fieldName);
    }

    /**
     * Verifies that the expected error message appears on the login page.
     *
     * @param reasonStr one or multiple error reason keys separated by commas
     */
    public void errorAppeared(String reasonStr) {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "span_by_id", "error");

        String errorText = getText("span_by_id", "error");
        String[] listReasons = reasonStr.split(",");

        for (String reason : listReasons) {
            String expectedReason = reader.readErrorProperty(reason);
            Assertions.assertTrue(errorText.contains(expectedReason), "The error_message < " + errorText
                    + "> does not contain the reason for <" + reason + ">  = <" + expectedReason + ">");
        }
    }

    /**
     * Asserts that no error message is visible on the login page.
     */
    public void checkNoErrors() {
        try {
            not_find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, "span_by_id", "error");
        } catch (AssertionError e) {
            throw new AssertionError("There is a problem with the data that has been used in the previous step");
        }
    }

}
