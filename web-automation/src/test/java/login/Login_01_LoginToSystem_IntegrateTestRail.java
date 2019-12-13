package login;

import commons.AbstractTest;
import commons.Environment;
import commons.PageFactoryManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageobjects.HomePageObject;
import pageobjects.LoginPageObject;

public class Login_01_LoginToSystem_IntegrateTestRail extends AbstractTest {
    private WebDriver driver;
    private LoginPageObject loginPage;
    private HomePageObject homePage;
    private Environment env;

    @Parameters({"browser", "version", "environment"})
    @BeforeClass
    public void setup(String browserName, String driverVersion, String environment) {
        ConfigFactory.setProperty("env", environment);
        env = ConfigFactory.create(Environment.class);
        driver = openBrowser(browserName, driverVersion);
        navigateToSite(driver, env.getUrl());
        loginPage = PageFactoryManager.getLoginPage(driver);
    }

    @Test(priority = 0)
    //@TestRails(testCaseID = 132204)
    public void C132204_LoginToSystem_EmptyIDNumber() {
        log.info("Login - Step 01: Input empty to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", "");

        log.info("Login - Step 02: Input Password to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "Password", env.getPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Please enter a ID number to continue.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver, "ID Number", "Please enter a ID number to continue."));
    }

    @Test(priority = 1)
    //@TestRails(testCaseID = 91634)
    public void C91634_LoginToSystem_EmptyPassword() {
        log.info("Login - Step 01: Input valid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", env.getIDNumber());

        log.info("Login - Step 02: Input empty to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", "");

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Please enter a password to continue.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver, "Password", "Please enter a password to continue."));
    }

    @Test(priority = 2)
    //@TestRails(testCaseID = 91618)
    public void C91618_LoginToSystem_InvalidIDNumber_ValidPassword() {
        log.info("Login - Step 01: Input invalid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", "9274222282992818");

        log.info("Login - Step 02: Input valid to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", env.getPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Invalid ID number, please try again.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver, "ID Number", "Invalid ID number, please try again."));
    }

    @Test(priority = 3)
    //@TestRails(testCaseID = 91600)
    public void C91600_LoginToSystem_ValidIDNumber_InvalidPassword() {
        log.info("Login - Step 01: Input valid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", env.getIDNumber());

        log.info("Login - Step 02: Input invalid to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", "ABc123@#4");

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Invalid SAID or password' displayed");
        verifyTrue(loginPage.isLoginErrorMessageDisplayed("Invalid SAID or password"));
    }

    @Test(priority = 4)
    //@TestRails(testCaseID = 153796)
    public void C153796_LoginToSystem_ValidAccount() {
        log.info("Login - Step 01: Input SAID to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", env.getIDNumber());

        log.info("Login - Step 02: Input Password to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "Password", env.getPassword());

        log.info("Login - Step 03: Click to Login button");
        homePage = loginPage.clickToLoginButton();

        log.info("Login - Step 04: Verify Welcome message displayed");
        verifyTrue(homePage.isWelcomeMessageDisplayed());
    }

    @AfterClass
    public void cleanUp() {
        closeBrowserAndDriver(driver);
    }
}
