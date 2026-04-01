package poms.thinking_tester_contact_list;

import org.openqa.selenium.WebDriver;

import silver.ui.POM;
import steps.hooks.Hooks;

/**
 * Page Object Model for the contact list screen.
 */
public class POM_ContactList extends POM {

    public POM_ContactList(WebDriver driver) {
        super(driver);
    }

    /**
     * Verifies that the contact list page is displayed.
     */
    public void assertPage() {
        find(Hooks.isDebugScreenshot(), SHORT_TIMEOUT, "h1_by_text", "Contact List");
    }

    /**
     * Clicks the logout button on the contact list page.
     */
    public void logOut() {
        clickOn("button_by_id", "logout");
    }
}
