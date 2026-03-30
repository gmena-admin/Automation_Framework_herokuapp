package poms.thinking_tester_contact_list;

import org.openqa.selenium.WebDriver;

import silver.POM;
import steps.hooks.Preconditions;

public class POM_Login extends POM {

    public POM_Login(WebDriver driver) {
        super(driver);
    }

    public void assertPage() {
        find(Preconditions.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Contact List App");
        find(Preconditions.isDebugScreenshot(), SHORT_TIMEOUT, "img_footer");

    }

    public void accessToSignUp() {
        clickOn("button_by_id", "signup");
    }

}
