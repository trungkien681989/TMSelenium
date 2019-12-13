package pageobjects;

import commons.AbstractPage;
import commons.Constants;
import commons.PageFactoryManager;
import org.openqa.selenium.WebDriver;
import pageinterfaces.LoginPageUI;

public class LoginPageObject extends AbstractPage {
    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginErrorMessageDisplayed(String message) {
        waitForElementToBeVisible(driver, LoginPageUI.LOGIN_ERROR_MESSAGE, message);
        return isElementDisplayed(driver, LoginPageUI.LOGIN_ERROR_MESSAGE, message);
    }

    public HomePageObject clickToLoginButton() {
        clickToDynamicButton(driver, Constants.LOGIN_BUTTON_NAME);
        return PageFactoryManager.getHomePage(driver);
    }

    public HomePageObject loginToSystem(String said, String password) {
        inputToDynamicTextBox(driver, "ID Number", said);
        inputToDynamicTextBox(driver, "Password", password);
        clickToDynamicButton(driver, Constants.LOGIN_BUTTON_NAME);
        return PageFactoryManager.getHomePage(driver);
    }

}
