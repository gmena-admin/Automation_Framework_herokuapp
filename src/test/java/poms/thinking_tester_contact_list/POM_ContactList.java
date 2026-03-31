package poms.thinking_tester_contact_list;

import org.openqa.selenium.WebDriver;

import silver.POM;
import steps.hooks.Hooks;

public class POM_ContactList extends POM {

    public POM_ContactList(WebDriver driver) {
        super(driver);
    }

    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Contact List");

    }

    public void logOut() {
        clickOn("button_by_id", "logout");
    }
}
