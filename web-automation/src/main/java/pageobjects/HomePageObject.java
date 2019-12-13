package pageobjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageinterfaces.HomePageUI;

public class HomePageObject extends AbstractPage {
    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isWelcomeMessageDisplayed() {
        waitForElementToBeVisible(driver, HomePageUI.WELCOME_MESSAGE);
        return isElementDisplayed(driver, HomePageUI.WELCOME_MESSAGE);
    }

    public void waitForProfileLoaded() {
        waitForElementToBeVisible(driver, HomePageUI.ACCOUNT_LABEL);
    }

}
