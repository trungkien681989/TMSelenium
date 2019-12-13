package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import pageinterfaces.AbstractPageUI;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AbstractPage {

    /* Web Browser */
    public void openAnyUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getCurrentPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getCurrentPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentPageSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPreviousPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToNextPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public void cancelAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public String getTextInAlert(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public void sendKeysToAlert(WebDriver driver, String value) {
        driver.switchTo().alert().sendKeys(value);
    }

    /* Web Element */
    protected void clickToElement(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        element.click();
    }

    protected void clickToElement(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        element.click();
    }

    protected void sendKeysToElement(WebDriver driver, String locator, String valueToSend) {
        WebElement element = driver.findElement(By.xpath(locator));
        element.clear();
        element.sendKeys(valueToSend);
    }

    protected void sendKeysToElement(WebDriver driver, String locator, String valueToSend, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        element.clear();
        element.sendKeys(valueToSend);
    }

    protected void selectItemInDropDown(WebDriver driver, String locator, String value) {
        WebElement element = driver.findElement(By.xpath(locator));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    protected void selectItemInDropDown(WebDriver driver, String locator, String value, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    protected String getSelectedItemInDropDown(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    protected String getSelectedItemInDropDown(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    protected void selectItemInCustomDropDown(WebDriver driver, String parentLocator, String allItemsLocator, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement parentElement = driver.findElement(By.xpath(parentLocator));
        js.executeScript("arguments[0].click();", parentElement);
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsLocator)));
        List<WebElement> allItems = driver.findElements(By.xpath(allItemsLocator));
        for (WebElement item : allItems) {
            if (item.getText().equals(value)) {
                if (item.isDisplayed()) {
                    item.click();
                } else {
                    js.executeScript("arguments[0].scrollIntoView(true);", item);
                    js.executeScript("arguments[0].click();", item);
                }
                break;
            }
        }
    }

    protected void selectMultipleItemsInCustomDropDown(WebDriver driver, String parentLocator, String allItemsLocator, List<String> expectedItemsValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement parentElement = driver.findElement(By.xpath(parentLocator));
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        js.executeScript("arguments[0].click();", parentElement);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsLocator)));
        List<WebElement> allItems = driver.findElements(By.xpath(allItemsLocator));
        for (String itemValue : expectedItemsValue) {
            for (WebElement item : allItems) {
                if (item.getText().equals(itemValue)) {
                    if (item.isDisplayed()) {
                        item.click();
                    } else {
                        js.executeScript("arguments[0].scrollIntoView(true);", item);
                        js.executeScript("arguments[0].click();", item);
                    }
                    break;
                }
            }
        }
    }

    protected String getAttributeValue(WebDriver driver, String locator, String attribute) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attribute);
    }

    protected String getAttributeValue(WebDriver driver, String locator, String attribute, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attribute);
    }

    protected List<String> getAttributeValueOfElements(WebDriver driver, String locator, String attribute) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements.stream().map(WebElement -> WebElement.getAttribute(attribute)).collect(Collectors.toList());
    }

    protected String getTextOfElement(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getText();
    }

    protected String getTextOfElement(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getText();
    }

    protected List<String> getTextOfElements(WebDriver driver, String locator) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    protected List<String> getTextOfElements(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    protected int countNumberOfElements(WebDriver driver, String locator) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements.size();
    }

    protected void checkToCheckBox(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }

    protected void uncheckToCheckBox(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        if (element.isSelected()) {
            element.click();
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        if (elements.size() == 0) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        } else if (elements.size() > 0 && elements.get(0).isDisplayed()) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        if (elements.size() == 0) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        } else if (elements.size() > 0 && elements.get(0).isDisplayed()) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        }
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        if (elements.size() == 0) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        }
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator, String... dynamicValue) {
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        locator = String.format(locator, (Object[]) dynamicValue);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        if (elements.size() == 0) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return true;
        } else {
            overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
            return false;
        }
    }

    protected boolean isElementSelected(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element.isSelected();
    }

    protected boolean isElementEnabled(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element.isEnabled();
    }

    protected boolean isElementDisabled(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return !element.isEnabled();
    }

    protected boolean isElementDisabled(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        return !element.isEnabled();
    }

    /* Windows /Frame /iFrame */
    protected void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String currentWindow : allWindows) {
            driver.switchTo().window(currentWindow);
            String currentTitle = driver.getTitle();
            if (currentTitle.equals(title)) {
                break;
            }
        }

    }

    protected void closeAllWindowsExceptParent(WebDriver driver, String parentWindow) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String currentWindow : allWindows) {
            if (!currentWindow.equals(parentWindow)) {
                driver.switchTo().window(currentWindow);
                driver.close();
            }
        }
    }

    protected String getWindowID(WebDriver driver) {
        return driver.getWindowHandle();
    }

    protected int getNumberOfOpeningWindow(WebDriver driver) {
        Set<String> allWindows = driver.getWindowHandles();
        return allWindows.size();
    }

    protected void switchToFrame(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        driver.switchTo().frame(element);
    }

    protected void backToTopWindow(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    /* User Interactions */
    protected void doubleClickToElement(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
        ;
    }

    protected void hoverMouseToElement(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    protected void hoverMouseToElement(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.contextClick(element).perform();
        ;
    }

    protected void dragAndDropFromSourceToTarget(WebDriver driver, String sourceLocator, String targetLocator) {
        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));
        Actions action = new Actions(driver);
        action.dragAndDrop(source, target).perform();
        ;
    }

    protected void sendKeyboardToElement(WebDriver driver, String locator, Keys key) {
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.sendKeys(element, key).perform();
    }

    /* Upload Files */
    protected void uploadFile(WebDriver driver, String locator, String filePath) {
        WebElement element = driver.findElement(By.xpath(locator));
        element.sendKeys(filePath);
    }

    protected void uploadMultipleFiles(WebDriver driver, String locator, List<String> filesPath) {
        WebElement element = driver.findElement(By.xpath(locator));
        for (String path : filesPath) {
            element.sendKeys(path);
        }
    }

    /* Javascript Executor */
    protected Object executeJavascriptToBrowser(WebDriver driver, String javascript) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript(javascript);
    }

    protected Object executeJavascriptToElement(WebDriver driver, String locator, String javascript) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        return javascriptExecutor.executeScript(javascript, element);
    }

    protected void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight");
    }

    protected void scrollToElement(WebDriver driver, String locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void highlightElement(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String originalStyle = driver.findElement(By.xpath(locator)).getAttribute("style");
        javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", driver.findElement(By.xpath(locator)), "style", "border: 3px solid red; border-style: dashed;");
        sleepInTime(200);
        javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", driver.findElement(By.xpath(locator)), "style", originalStyle);
        overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
    }

    protected void highlightElement(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        String originalStyle = element.getAttribute("style");
        javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 3px solid red; border-style: dashed;");
        sleepInTime(200);
        javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void removeAttributeOfElement(WebDriver driver, String locator, String attribute) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
    }

    protected void removeAttributeOfElement(WebDriver driver, String locator, String attribute, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
    }

    protected void setValueAttribute(WebDriver driver, String locator, String attribute, String valueToSet, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        javascriptExecutor.executeScript("document.getElementByXpath('" + locator + "').setAttribute('" + attribute + "', '" + valueToSet + "')");
    }

    protected boolean checkAnyImageLoaded(WebDriver driver, String locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        boolean status = (Boolean) javascriptExecutor.executeScript("\"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0\"", element);
        return status;
    }

    protected boolean verifyTextInInnerText(WebDriver driver, String textExpected) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String textActual = (String) javascriptExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        System.out.println("Text actual = " + textActual);
        return textActual.equals(textExpected);
    }

    /* Wait */
    protected void waitForElementToBePresent(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        By elementBy = By.xpath(locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
        // highlightElement(driver, locator);
    }

    protected void waitForElementToBePresent(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        By elementBy = By.xpath(locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
        // highlightElement(driver, locator);
    }

    protected void waitForElementToBeVisible(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));
            highlightElement(driver, locator);
        } catch (Exception e) {
            Reporter.log("==================== Element not found ====================");
            Reporter.log(e.getMessage());
            System.err.println("==================== Element not found ====================");
            System.err.println(e.getMessage());
        }
    }

    protected void waitForElementToBeVisible(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));
            highlightElement(driver, locator);
        } catch (Exception e) {
            Reporter.log("==================== Wait for element to become visible ====================");
            Reporter.log(e.getMessage());
            System.err.println("==================== Wait for element to become visible ====================");
            System.err.println(e.getMessage());
        }
    }

    protected void waitForElementToBeInvisible(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.SHORT_TIMEOUT);
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
        overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
    }

    protected void waitForElementToBeInvisible(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebDriverWait wait = new WebDriverWait(driver, Constants.SHORT_TIMEOUT);
        overrideGlobalTimeout(driver, Constants.SHORT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
        overrideGlobalTimeout(driver, Constants.LONG_TIMEOUT);
    }

    protected void waitForElementToBeClickable(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(locator))));
        highlightElement(driver, locator);
    }

    protected void waitForElementToBeClickable(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(locator))));
        highlightElement(driver, locator);
    }

    protected void waitForElementToBeRefreshed(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(locator)))));
    }

    protected void waitForElementToBeRefreshed(WebDriver driver, String locator, String... dynamicValue) {
        locator = String.format(locator, (Object[]) dynamicValue);
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(locator)))));
    }

    protected void waitForNestedElementsToBeVisible(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(driver.findElement(By.xpath(locator)), By.tagName("tr")));
    }

    protected void waitForAlertToBePresent(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(ExpectedConditions.alertIsPresent());
    }

    protected void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, Constants.LONG_TIMEOUT);
        wait.until(pageLoadCondition);
    }

    /* Override Implicit Wait Timeout */
    private void overrideGlobalTimeout(WebDriver driver, long timeout) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    private void sleepInTime(long millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* Sorting */
    protected boolean isStringSortedAscending(WebDriver driver, String locator) {
        ArrayList<String> actualList = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        for (WebElement element : elements) {
            actualList.add(element.getText());
        }
        ArrayList<String> expectedList = new ArrayList<String>();
        for (String child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList, Collator.getInstance(Locale.ENGLISH));
        return actualList.equals(expectedList);
    }

    protected boolean isStringSortedDescending(WebDriver driver, String locator) {
        ArrayList<String> actualList = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        for (WebElement element : elements) {
            actualList.add(element.getText());
        }
        ArrayList<String> expectedList = new ArrayList<String>();
        for (String child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList, Collator.getInstance(Locale.ENGLISH));
        Collections.reverse(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isDateSortedAscending(WebDriver driver, String locator) {
        ArrayList<Date> actualList = new ArrayList<Date>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        for (WebElement element : elements) {
            try {
                actualList.add(formatter.parse(element.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Date> expectedList = new ArrayList<Date>();
        for (Date child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isDateSortedDescending(WebDriver driver, String locator) {
        ArrayList<Date> actualList = new ArrayList<Date>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        for (WebElement element : elements) {
            try {
                actualList.add(formatter.parse(element.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Date> expectedList = new ArrayList<Date>();
        for (Date child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList);
        Collections.reverse(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isNumberSortedAscending(WebDriver driver, String locator) {
        ArrayList<Float> actualList = new ArrayList<Float>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        for (WebElement element : elements) {
            actualList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "")));
        }
        ArrayList<Float> expectedList = new ArrayList<Float>();
        for (Float child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isNumberSortedDescending(WebDriver driver, String locator) {
        ArrayList<Float> actualList = new ArrayList<Float>();
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        for (WebElement element : elements) {
            actualList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "")));
        }
        ArrayList<Float> expectedList = new ArrayList<Float>();
        for (Float child : actualList) {
            expectedList.add(child);
        }
        Collections.sort(expectedList);
        Collections.reverse(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isDataSortedAscending(WebDriver driver, String locator) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        List<String> actualList = elements.stream().map(n -> n.getText()).collect(Collectors.toList());
        List<String> expectedList = actualList;
        Collections.sort(expectedList);
        return actualList.equals(expectedList);
    }

    protected boolean isDataSortedDescending(WebDriver driver, String locator) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        List<String> actualList = elements.stream().map(n -> n.getText()).collect(Collectors.toList());
        List<String> expectedList = actualList;
        Collections.sort(expectedList);
        Collections.reverse(expectedList);
        return actualList.equals(expectedList);
    }

    /* Dynamic functions */
    public AbstractPage navigateToDynamicPage(WebDriver driver, String pageName) {
        waitForElementToBeClickable(driver, AbstractPageUI.DYNAMIC_PAGE, pageName);
        clickToElement(driver, AbstractPageUI.DYNAMIC_PAGE, pageName);
        switch (pageName) {
            case "login":
                return PageFactoryManager.getLoginPage(driver);
            default:
                return PageFactoryManager.getHomePage(driver);
        }
    }

    public boolean isDynamicPopUpMessageDisplayed(WebDriver driver, String message) {
        waitForElementToBeVisible(driver, AbstractPageUI.DYNAMIC_POPUP_MESSAGE, message);
        return isElementDisplayed(driver, AbstractPageUI.DYNAMIC_POPUP_MESSAGE, message);
    }

    public void clickToDynamicButton(WebDriver driver, String buttonName) {
        waitForElementToBeClickable(driver, AbstractPageUI.DYNAMIC_BUTTON, buttonName);
        clickToElement(driver, AbstractPageUI.DYNAMIC_BUTTON, buttonName);
    }

    public void clickToDynamicLink(WebDriver driver, String linkName) {
        waitForElementToBeClickable(driver, AbstractPageUI.DYNAMIC_LINK, linkName);
        clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, linkName);
    }

    public boolean isDynamicInlineErrorMessageDisplayed(WebDriver driver, String fieldName, String errorMessage) {
        waitForElementToBeVisible(driver, AbstractPageUI.INLINE_ERROR_MESSAGE, fieldName, errorMessage);
        return isElementDisplayed(driver, AbstractPageUI.INLINE_ERROR_MESSAGE, fieldName, errorMessage);
    }

    public void inputToDynamicTextBox(WebDriver driver, String fieldName, String valueToInput) {
        waitForElementToBeVisible(driver, AbstractPageUI.DYNAMIC_TEXT_BOX, fieldName);
        sendKeysToElement(driver, AbstractPageUI.DYNAMIC_TEXT_BOX, valueToInput, fieldName);
    }

    public void inputToDynamicAmountTextBox(WebDriver driver, String fieldName, String amount) {
        waitForElementToBeVisible(driver, AbstractPageUI.DYNAMIC_AMOUNT_TEXT_BOX, fieldName);
        sendKeysToElement(driver, AbstractPageUI.DYNAMIC_AMOUNT_TEXT_BOX, amount, fieldName);
    }

    public void waitForDialogPopupDisappeared(WebDriver driver) {
        waitForElementToBeInvisible(driver, AbstractPageUI.DIALOG_POPUP);
    }

    public void clickPopupCloseButton(WebDriver driver) {
        waitForElementToBeClickable(driver, AbstractPageUI.POPUP_CLOSE_BUTTON);
        clickToElement(driver, AbstractPageUI.POPUP_CLOSE_BUTTON);
    }

    public boolean isDynamicButtonDisabled(WebDriver driver, String buttonName) {
        waitForElementToBeVisible(driver, AbstractPageUI.DYNAMIC_BUTTON, buttonName);
        return isElementDisabled(driver, AbstractPageUI.DYNAMIC_BUTTON, buttonName);
    }

}