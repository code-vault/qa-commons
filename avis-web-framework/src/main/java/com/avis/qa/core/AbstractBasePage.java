package com.avis.qa.core;
import com.avis.qa.utilities.ElementHelper;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static com.avis.qa.utilities.CommonUtils.ONE_SECOND;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * Abstract class contains all the common methods used in screen classes
 *
 * @author ikumar
 */
@Log4j2
public abstract class AbstractBasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ElementHelper helper;

    public AbstractBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Configuration.DEFAULT_EXPLICIT_TIMEOUT);
        helper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
        isOnPage();
    }

    /**
     * Added loop here to support cross browser
     * Allowing the driver to wait for one second if element is not found on the page
     *
     * @param element
     * @return
     */
    protected WebElement waitForVisibilityOfElement(WebElement element) {
        for (int i = 0; i < 2; i++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                return element;
            } catch (Exception e) {
                if (i == 0) {
                    threadSleep(ONE_SECOND);
                } else {
                    throw new RuntimeException(element + "Element is not found");
                }
            }
        }
        return element;
    }

    protected WebElement waitForVisibilityOfElement(WebElement element, int timeout) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
    }

    protected void switchToLatestWindow() {
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!parentWindow.equals(window)) {
                driver.switchTo().window(window);
            }
        }
    }

    /**
     * Javascript Methods - clickUsingJS, clearTextUsingJS
     */
    protected void clickUsingJS(WebElement webElement) {
        log.info("Click using javascript");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", webElement);
    }

    protected void clearTextUsingJS(WebElement webElement) {
        log.info("Clear text using javascript");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", webElement);
    }

    /**
     * Navigation Methods - pageRefresh, navigateBack, navigateForward
     */
    public void pageRefresh() {
        log.info("Page Refresh");
        driver.navigate().refresh();
    }

    public void navigateBack() {
        log.info("Navigate Back");
        driver.navigate().back();
    }

    public void navigateForward() {
        log.info("Navigate Forward");
        driver.navigate().forward();
    }

    public String getPageTitle() {
        log.info("Get Page Title");
        return driver.getTitle();
    }

    /**
     * Every class should implement its own isOnPage which verifies the
     * screen is loaded.
     */
    public abstract void isOnPage();
}
