package poms.thinking_tester_contact_list;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import silver.POM;
import steps.hooks.Hooks;

public class POM_SignUp extends POM {

    private String password;

    public POM_SignUp(WebDriver driver) {
        super(driver);
    }

    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Add User");
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "img_footer");
    }

    public void writeField(String fieldName, String fieldValue) {
        if (fieldName == null)
            writeIn("", "input_by_id", fieldName);

        writeIn(fieldValue, "input_by_id", fieldName);

        if (fieldName.equals("password"))
            this.password = fieldValue;
    }

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

    public void checkNoErrors() {
        try {
            not_find(Hooks.isDebugScreenshot(), SHORTEST_TIMEOUT, "span_by_id", "error");
        } catch (AssertionError e) {
            throw new AssertionError("There is a problem with the data that has been used in the previous step");
        }

    }

}
