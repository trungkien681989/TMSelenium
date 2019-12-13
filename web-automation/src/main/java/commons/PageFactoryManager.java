package commons;

import org.openqa.selenium.WebDriver;
import pageobjects.HomePageObject;
import pageobjects.LoginPageObject;

public class PageFactoryManager {
    public static LoginPageObject getLoginPage(WebDriver driver) {
        return new LoginPageObject(driver);
    }

    public static HomePageObject getHomePage(WebDriver driver) { return new HomePageObject(driver); }

}
