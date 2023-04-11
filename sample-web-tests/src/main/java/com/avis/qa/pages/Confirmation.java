package com.avis.qa.pages;

import com.avis.qa.components.ReservationWidget;
import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.avis.qa.utilities.CommonUtils.*;


/**
 * This class contains all the elements and actions performed on Confirmation Page
 *
 * @author ikumar
 */
@Log4j2
public class Confirmation extends AbstractBasePage {

    @FindBy(xpath = "//span[@class='confirmation-num']")
    private WebElement confirmationNumber;

    @FindBy(xpath = "//button[contains(@id,'res-cancelReservation')]")
    private WebElement cancelResCTA;

    @FindBy(xpath = "//label[@class='step4-checkbox-custom-label']")
    private WebElement CancelConfirmationCheckBox;

    @FindBy(xpath = "//button[contains(@id,'res-cancelreservation')]")
    private WebElement confirmCancelResCTA;

    @FindBy(xpath = "(//div[@class='col-lg-4 col-md-4 col-sm-4 col-xs-12 pad-zero']/p)[1]")
    private WebElement AWDConfirmationPage;

    @FindBy(id = "res-cancelreservation-popUp")
    private WebElement cancelReservationPopup;

    @FindBy(xpath = "//div[@class='col-lg-12 message-success']")
    private WebElement OneClickGPSAdded;

    @FindBy(xpath = "(//p[contains(text(),'Coupon savings applied')])[2]|//span[contains(@class,'coupon-value')]")
    private WebElement ConfirmationCouponValidation;

    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElement getFreeCouponPopup;

    @FindBy(xpath = "//iframe[@title = 'Rokt placement']")
    private WebElement roktPlacementFrame;

    @FindBy(xpath = "//iframe[@title = 'Rokt offer']")
    private WebElement roktOfferFrame;

    @FindBy(xpath = "//strong[contains(text(),'Equipment & Services')]")
    private WebElement EquipmentAndServiceProductsInfo;

    @FindBy(xpath = "(//span[contains(text(),'Snow Chains')])[2]")
    private WebElement SnowChainProductInfo;

    @FindBy(xpath = "//span[contains(text(),'Selected')]")
    private WebElement SnowChainProductSelectedStatus;

    @FindBy(xpath = "(//*[contains(text(),'Modify')])[4]")
    private WebElement modifyTimeAndPlace;

    @FindBy(xpath = "//div[@class='flyout-image-wrapper']//span[@class='close-icon-black']")
    private WebElement flyoutClosebtn;

    @FindBy(xpath = "(//div[@class='col-xs-6']//span)[1]")
    private WebElement CurrencyConfirmationPage;

    @FindBy(xpath = "//span[contains(@ng-if,'product.code != carRentalConstant.equipmentCodeCSSG')]")
    private WebElement rentalOptionsRSN;

    @FindBy(xpath = "(//h3)[1]")
    private WebElement NameDetails;

    @FindBy(xpath = "((//div[@class='col-lg-4 col-sm-6 col-xs-12 pad-zero'])[1]//p)[1]")
    private WebElement EmailConfirmationPage;


    public Confirmation(WebDriver driver) {
        super(driver);
    }

    public boolean isConfirmationNumberDisplayed() {
        return confirmationNumber.isDisplayed();
    }

    public Confirmation cancelReservationWithConfirmationBox() {
        threadSleep(TWO_SECONDS);
        clickUsingJS(waitForVisibilityOfElement(cancelResCTA));
        threadSleep(TWO_SECONDS);
        clickUsingJS(waitForVisibilityOfElement(CancelConfirmationCheckBox));
        clickUsingJS(waitForVisibilityOfElement(confirmCancelResCTA));
        return this;
    }

    public Confirmation cancelReservation() {
        helper.scrollToElement(cancelResCTA);
        clickUsingJS(cancelResCTA);
        threadSleep(ONE_SECOND);
        cancelReservationPopup.click();
        return this;
    }

    public boolean isExtrasAddedMessageDisplayed() {
        return OneClickGPSAdded.getText().contains("Hands-Free Navigation (GPS) is successfully added to your reservation.");
    }

    public boolean isAwdConfirmationPageTextDisplayed() {
        return AWDConfirmationPage.getText().contains("AWD");
    }

    public boolean isCouponAppliedMessageDisplayed(String couponApplied) {
        String actualText = ConfirmationCouponValidation.getText();
        return actualText.contains("Coupon savings applied") || actualText.contains(couponApplied);
    }

    public boolean isSnowChainProductPresent() {
        return helper.isElementDisplayed(EquipmentAndServiceProductsInfo) && helper.isElementDisplayed(SnowChainProductInfo) && helper.isElementDisplayed(SnowChainProductSelectedStatus);
    }

    public ReservationWidget modifyTimeAndPlaceClick() {
        threadSleep(THREE_SECONDS);
        helper.scrollToElement(modifyTimeAndPlace);
        clickUsingJS(waitForVisibilityOfElement(modifyTimeAndPlace));
        return new ReservationWidget(driver);
    }

    public Confirmation closeGetFreeCouponPopup() {
        threadSleep(THREE_SECONDS);
        if (helper.isElementDisplayed(roktPlacementFrame)) {
            driver.switchTo().frame(roktPlacementFrame);
            driver.switchTo().frame(roktOfferFrame);
            helper.clickIfElementIsDisplayed(getFreeCouponPopup);
            driver.switchTo().defaultContent();
        }
        return this;
    }

    public Confirmation flyOutClose() {
        helper.clickIfElementIsDisplayed(flyoutClosebtn);
        return this;
    }

    public boolean verifyCurrencyOnConfirmationPage() {
        return CurrencyConfirmationPage.getText().contains("NZD");
    }

    public boolean verifyRentalOptionsText() {
        return rentalOptionsRSN.getText().contains("Cover Roadside Issues (RSN)");
    }

    public boolean verifypersonalInfo(String firstName, String email) {
        boolean verifyFirstName = NameDetails.getText().contains(firstName);
        boolean verifyEmail = EmailConfirmationPage.getText().contains(email);
        return verifyEmail && verifyFirstName;
    }

    @Override
    public void isOnPage() {
        log.info("Verify Confirmation Page");
        waitForVisibilityOfElement(confirmationNumber);
    }
}
