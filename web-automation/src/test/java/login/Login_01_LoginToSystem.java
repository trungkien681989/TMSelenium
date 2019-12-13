package login;

import commons.*;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageobjects.HomePageObject;
import pageobjects.LoginPageObject;

import javax.xml.crypto.Data;
import java.io.IOException;

public class Login_01_LoginToSystem extends AbstractTest {
    private WebDriver driver;
    private LoginPageObject loginPage;
    private HomePageObject homePage;
    private Environment env;
    private DataReader dataReader;

    @Parameters({"browser", "environment", "dataPath"})
    @BeforeClass
    public void setup(String browserName, String environment, String dataPath) throws IOException {
        ConfigFactory.setProperty("env", environment);
        env = ConfigFactory.create(Environment.class);
        dataReader = DataReader.get(dataPath);
        driver = openBrowser(browserName);
        navigateToSite(driver, env.getUrl());
        loginPage = PageFactoryManager.getLoginPage(driver);
    }

    @Test
    public void Login_01_LoginToSystem_EmptyIDNumber() {
        log.info("Login - Step 01: Input empty to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", dataReader.getEmptyIDNumber());

        log.info("Login - Step 02: Input Password to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "Password", env.getPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Please enter a ID number to continue.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver,"ID Number", Constants.EMPTY_ID_NUMBER_ERROR));
    }

    @Test
    public void Login_02_LoginToSystem_EmptyPassword() {
        log.info("Login - Step 01: Input valid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", env.getIDNumber());

        log.info("Login - Step 02: Input empty to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", dataReader.getEmptyPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Please enter a password to continue.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver,"Password", Constants.EMPTY_PASSWORD_ERROR));
    }

    @Test
    public void Login_03_LoginToSystem_InvalidIDNumber_ValidPassword() {
        log.info("Login - Step 01: Input invalid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", dataReader.getInvalidIDNumber());

        log.info("Login - Step 02: Input valid to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", env.getPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Invalid ID number, please try again.' displayed");
        verifyTrue(loginPage.isDynamicInlineErrorMessageDisplayed(driver,"ID Number", Constants.INVALID_ID_NUMBER_ERROR));
    }

    @Test
    public void Login_04_LoginToSystem_ValidIDNumber_InvalidPassword() {
        log.info("Login - Step 01: Input valid ID Number to ID Number text box");
        loginPage.inputToDynamicTextBox(driver, "ID Number", env.getIDNumber());

        log.info("Login - Step 02: Input invalid to Password text box");
        loginPage.inputToDynamicTextBox(driver, "Password", dataReader.getInvalidPassword());

        log.info("Login - Step 03: Click to Login button");
        loginPage.clickToDynamicButton(driver, "Login");

        log.info("Login - Step 04: Verify error message 'Invalid ID number, please try again.' displayed");
        verifyTrue(loginPage.isLoginErrorMessageDisplayed(Constants.INVALID_PASSWORD_ERROR));
    }

    @Test
    public void Login_05_LoginToSystem_ValidAccount() {
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
