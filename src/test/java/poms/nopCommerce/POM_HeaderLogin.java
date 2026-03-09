package poms.nopCommerce;

import org.openqa.selenium.WebDriver;

import silver.POM;

public class POM_HeaderLogin extends POM {

    public POM_HeaderLogin(WebDriver driver) {
        super(driver);
    }

    public void checkLogo() {
        find(true, SHORT_TIMEOUT, "logo");
    }

}
