package poms.thinking_tester_contact_list;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import silver.POM;
import steps.hooks.Hooks;

public class POM_Login extends POM {

    public POM_Login(WebDriver driver) {
        super(driver);
    }

    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Contact List App");
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "img_footer");

    }

    public void accessToSignUp() {
        clickOn("button_by_id", "signup");
    }

    public void logIn() {
        clickOn("button_by_id", "submit");
    }

    public void writeField(String fieldName, String fieldValue) {
        if (fieldName == null)
            writeIn("", "input_by_id", fieldName);

        writeIn(fieldValue, "input_by_id", fieldName);
    }

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

    public void checkNoErrors() {
        try {
            not_find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, "span_by_id", "error");
        } catch (AssertionError e) {
            throw new AssertionError("There is a problem with the data that has been used in the previous step");
        }

    }

}
