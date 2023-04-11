package com.avis.qa.pages;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.avis.qa.core.Configuration.URL;
import static com.avis.qa.utilities.CommonUtils.ONE_SECOND;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * This class contains all the elements and actions performed on HomePage
 *
 * @author ikumar
 */
@Log4j2
public class Homepage extends AbstractBasePage {

    @FindBy(id = "avis-logo")
    private WebElement avisLogo;

    public Homepage(WebDriver driver) {
        super(driver);
    }

    public boolean isAvisLogoDisplayed() {
        log.info("Verify Avis Logo displayed on page header");
        return avisLogo.isDisplayed();
    }

    @Override
    public void isOnPage() {
        log.info("Verify Home Page");
        waitForVisibilityOfElement(avisLogo);
    }
}
