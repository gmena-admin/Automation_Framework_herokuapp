package poms.thinking_tester_contact_list;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import silver.ui.POM;
import steps.hooks.Hooks;

/**
 * Page Object Model for the sign-up screen of the Contact List app.
 */
public class POM_SignUp extends POM {

    private String password;

    public POM_SignUp(WebDriver driver) {
        super(driver);
    }

    /**
     * Verifies the sign-up page is visible.
     */
    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Add User");
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "img_footer");
    }

    /**
     * Writes a value into the sign-up input field.
     *
     * @param fieldName the field identifier in the locator repository
     * @param fieldValue the text to enter
     */
    public void writeField(String fieldName, String fieldValue) {
        if (fieldName == null)
            writeIn("", "input_by_id", fieldName);

        writeIn(fieldValue, "input_by_id", fieldName);

        if (fieldName.equals("password"))
            this.password = fieldValue;
    }

    /**
     * Clicks the requested button on the sign-up page.
     *
     * @param buttonName the button identifier such as Submit or Cancel
     */
    public void pushButton(String buttonName) {
        switch (buttonName) {
            case "Submit":
                clickOn("button_by_id", "submit");
                break;
            case "Cancel":
                clickOn("button_by_id", "cancel");
                break;
            default:
                Assertions.fail("The button <" + buttonName + "> is not currently usable in the tool");
        }
    }

    /**
     * Verifies that the expected error is shown on the sign-up screen.
     *
     * @param reasonStr the comma-separated reason keys to validate
     */
    public void errorAppeared(String reasonStr) {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "span_by_id", "error");

        String errorText = getText("span_by_id", "error");
        String[] listReasons = reasonStr.split(",");

        for (String reason : listReasons) {
            String expectedReason = reader.readErrorProperty(reason);

            if (reason.equals("wrongPasswordLength"))
                expectedReason = reader.readErrorProperty(reason, password);

            Assertions.assertTrue(errorText.contains(expectedReason), "The error_message < " + errorText
                    + "> does not contain the reason for <" + reason + ">  = <" + expectedReason + ">");
        }
    }

    /**
     * Ensures that no error banner is visible on the sign-up form.
     */
    public void checkNoErrors() {
        try {
            not_find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, "span_by_id", "error");
        } catch (AssertionError e) {
            throw new AssertionError("There is a problem with the data that has been used in the previous step");
        }
    }

}
