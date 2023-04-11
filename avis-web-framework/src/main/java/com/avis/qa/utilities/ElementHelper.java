package com.avis.qa.utilities;
import com.avis.qa.core.BrowserInstance;
import com.avis.qa.core.Configuration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.avis.qa.core.Configuration.DEFAULT_IMPLICIT_TIMEOUT;


/**
 * Helper class contains all the element related generic methods
 *
 * @author ikumar
 */
@Log4j2
public class ElementHelper {

    private final WebDriver driver;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
    }


    public void clearTextUsingRepeatedBackspace(WebElement webElement, int repeatCount) {
        String backspace = Keys.BACK_SPACE.toString();
        webElement.sendKeys(StringUtils.repeat(backspace, repeatCount));
    }

    public void selectValueFromDropDown(WebElement dropDownElement, String dropDownValue) {
        Select dropDown = new Select(dropDownElement);
        dropDown.selectByVisibleText(dropDownValue);
    }

    public void selectValueFromDropDown(WebElement dropDownElement, int index) {
        Select dropDown = new Select(dropDownElement);
        dropDown.selectByIndex(index);
    }

    public String getValueFromDropDown(WebElement dropDownElement, int optionIndex) {
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        return options.get(optionIndex).getText();
    }

    public void clickIfElementIsDisplayed(WebElement element) {
        if (isElementDisplayed(element)) {
            element.click();
        }
    }

    public boolean isElementDisplayed(WebElement element) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Configuration.DEFAULT_EXPLICIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean isElementDisplayed(WebElement element, int timeout) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean isImageDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Configuration.DEFAULT_EXPLICIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(element));
            return (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    /*
     * This method is used to wait until webElement to be Clickable
     */
    public void waitUntilClickabilityOfElement(WebElement webElement) {
        new WebDriverWait(driver, Configuration.DEFAULT_EXPLICIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /*
     * This method is used to wait implicitly
     */
    public void implicitWait() {
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
    }

    public List<String> getAllElementsText(List<WebElement> webElements) {
        List<String> elementsText = new ArrayList<>();

        for (WebElement we : webElements) {
            elementsText.add(we.getText());
        }
        return elementsText;
    }

    public void mouseHover(WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).build().perform();
    }

    /*
     * This method is used to Scroll to WebElement
     */
    public void scrollToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView()", webElement);
    }

    /*
     * This method is used to Scroll vertically by value
     */
    public void scrollBy(String value) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0, " + value + ")", "");
        } catch (Exception e) {
            log.info("Exception occurred while performing vertical scroll on the page by value \"" + value + "\" .");
        }
    }
}