package com.avis.qa.pages;

import com.avis.qa.core.AbstractBasePage;
import com.avis.qa.utilities.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.avis.qa.utilities.CommonUtils.TWO_SECONDS;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * This class contains all the elements and actions performed on Extras Page
 *
 * @author ikumar
 */
@Log4j2
public class Extras extends AbstractBasePage {

    @FindBy(xpath = "//button[@class='btn btn-primary-avis pull-right'] | //button[contains(@id,'res-extras-continue-bottom')] | (//button[contains(text(),'Continue')])[2] | (//button[contains(text(),'Continue')])[1]")
    private WebElement submitStep3;

    @FindBy(xpath = "(//label[@for='RSNchk'])[1]")
    private WebElement TieredBundle;

    @FindBy(id = "Step3-Services")
    private WebElement Step3ServicesTab;


    public Extras(WebDriver driver) {
        super(driver);
    }

    public ReviewAndBook Step3Submit() {
        threadSleep(TWO_SECONDS);
        helper.scrollToElement(submitStep3);
        clickUsingJS(submitStep3);
        return new ReviewAndBook(driver);
    }

    public boolean isSubmitStepThreeDisplayed() {
        return submitStep3.isDisplayed();
    }

    public Extras selectTierBundle() {
        waitForVisibilityOfElement(Step3ServicesTab).click();
        if (helper.isElementDisplayed(TieredBundle))
            clickUsingJS(TieredBundle);
        CommonUtils.threadSleep(TWO_SECONDS);
        return this;
    }

    @Override
    public void isOnPage() {
        log.info("Verify Extras Page");
        waitForVisibilityOfElement(submitStep3);
    }
}
