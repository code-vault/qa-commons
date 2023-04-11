package com.avis.qa.components;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * This class contains all the elements and actions performed on Footer
 *
 * @author ikumar
 */
@Log4j2
public class Footer extends AbstractBasePage {

    @FindBy(xpath = "//a[contains(text(),'Best Price Pledge')]")
    private WebElement bestPricePledge;

    public Footer(WebDriver driver) {
        super(driver);
    }

    @Override
    public void isOnPage() {
        log.info("Verify Footer Component");
        waitForVisibilityOfElement(bestPricePledge);
    }
}
