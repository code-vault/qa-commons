package com.avis.qa.components;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.avis.qa.utilities.CommonUtils.*;


/**
 * This class contains all the elements and actions performed on ReservationWidget
 *
 * @author ikumar
 */
@Log4j2
public class ReservationWidget extends AbstractBasePage {

    @FindBy(xpath = "//*[contains(@id,'DropLoc_value')]")
    private WebElement dropOffLocation;

    @FindBy(xpath = "(//div[@class='angucomplete-description'])[1]")
    private WebElement dropOffSuggestion;

    @FindBy(id = "PicLoc_value")
    private WebElement pickUpLocation;

    @FindBy(xpath = "((//div[@class='angucomplete-results'])[1]//div[@class='angucomplete-description'])[1] | ((//div[@id='PicLoc_dropdown'])[1]//div[@class='angucomplete-row'])[1]")
    private WebElement suggestionLocation;

    @FindBy(xpath = "//*[contains(@id,'from')] | //input[@id='from']")
    private WebElement pickupDate;

    @FindBy(xpath = "//div[contains(@class,'ui-datepicker-group ui-datepicker-group-last')]/div/a")
    private WebElement nextMonthSelection;

    @FindBy(xpath = "//table[contains(@class,'ui-datepicker-calendar uitable ui-datepicker-table-first ')]//a[contains(text(),16)]")
    private WebElement pickupDateSelection;

    @FindBy(xpath = "//table[contains(@class,'datepicker-calendar')]//a[contains(text(),10)]")
    private WebElement returnDateSelection;

    @FindBy(xpath = "(//*[contains(@ng-click,'getVehicles.submit')])[1]")
    private WebElement selectMyCarButton;

    @FindBy(id = "avis-logo")
    private WebElement avisLogo;

    @FindBy(xpath = "//span[@class='mainErrorText info-error-msg-text' and @ng-bind-html='error.message | htmlFilter']")
    private WebElement ErrorMessagePath;

    @FindBy(xpath = "//*[contains(@id,'to')]")
    private WebElement returnDate;

    @FindBy(xpath = "(//table[contains(@class,'datepicker-calendar')]//a[contains(text(),25)])")
    private WebElement returnDateSelection_new4;

    @FindBy(xpath = "//div[@class='res-discFld form-controlD']")
    private WebElement Discount_Codes;

    @FindBy(id = "awd")
    private WebElement AWDOrBCDOrPDN_TextField;

    @FindBy(xpath = "//input[@id='partnerMembershipId']")
    private WebElement membershipTextField;

    @FindBy(xpath = "(//div[contains(@class,'row res-inputFldPrt res-inputFldBack')]/div[4]/div[4]/div[3]/div)|(//div[contains(@class,'res-wizardFld form-controlD')])")
    private WebElement AvisWizardNumberLink;

    @FindBy(id = "res-home-wizNum")
    private WebElement WizardNoTextField;

    @FindBy(id = "res-home-lastName")
    private WebElement LastNameTextField;

    @FindBy(id = "coupon")
    private WebElement CouponTextField;

    @FindBy(xpath = "(//*[@id='reservationModel.personalInfoRQ.residency'])[1]")
    private WebElement selectCountry;

    @FindBy(xpath = "(//*[contains(@id,'from')])[1]")
    private WebElement pickupDateField;

    @FindBy(xpath = "(//*[contains(@id,'from')])[1]")
    private WebElement returnDateField;

    @FindBy(xpath = "//span[@class='mainErrorText info-error-msg-text']")
    private WebElement nullPickUpLocationMessageElement;

    @FindBy(xpath = "//*[contains(@name,'reservationModel.personalInfoRQ.age')]")
    private WebElement selectAge;

    @FindBy(xpath = "//*[contains(@name,'reservationModel.dropTime')]")
    private WebElement dropOffTime;

    public ReservationWidget(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to verify pickup location
     **/
    public ReservationWidget pickUpLocation(String pickUpLocationValue) {
        enterLocation(pickUpLocationValue, pickUpLocation);
        if (helper.isElementDisplayed(suggestionLocation))
            clickUsingJS(suggestionLocation);
        else {
            clearTextUsingJS(pickUpLocation);
            enterLocation(pickUpLocationValue, pickUpLocation);
            if (helper.isElementDisplayed(suggestionLocation))
                clickUsingJS(suggestionLocation);
        }
        return this;
    }


    /**
     * Method to verify dropOff location
     **/
    public ReservationWidget dropOffLocation(String dropOffLocationValue) {
        enterLocation(dropOffLocationValue, dropOffLocation);

        if (helper.isElementDisplayed(dropOffSuggestion))
            clickUsingJS(dropOffSuggestion);
        else {
            clearTextUsingJS(pickUpLocation);
            enterLocation(dropOffLocationValue, dropOffLocation);
            if (helper.isElementDisplayed(dropOffSuggestion))
                clickUsingJS(dropOffSuggestion);
        }
        return this;
    }

    private void enterLocation(String location, WebElement element) {
        element.click();
        element.clear();
        element.sendKeys(location);
        threadSleep(TWO_SECONDS);
    }

    /**
     * Method to select calendar
     **/
    public ReservationWidget calendarSelection() {
        helper.scrollBy("-600");
        threadSleep(TWO_SECONDS);
        pickupDate.click();

        for (int i = 0; i < 5; i++) {
            threadSleep(ONE_SECOND);
            nextMonthSelection.click();
        }

        pickupDateSelection.click();
        threadSleep(THREE_SECONDS);
        returnDateSelection.click();
        return this;
    }

    /**
     * Method to select calendar
     **/
    public ReservationWidget calendarSelectionSnowChain() {
        helper.scrollBy("-600");
        threadSleep(TWO_SECONDS);
        pickupDate.click();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String Date1 = "";
        String Date2 = "";

        try {

            Date1 = "12/24/" + year;
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
            Date pdate = formatter1.parse(Date1);
            Date1 = formatter1.format(pdate); // pick-up date

            Date2 = "01/04/" + (year + 1);
            Date rdate = formatter1.parse(Date2);
            Date2 = formatter1.format(rdate);// return date

        } catch (ParseException e) {
            log.info("Error occured : In snow chain calendar Selection.");
        }

        threadSleep(ONE_SECOND);
        pickupDateField.sendKeys(Date1);
        threadSleep(ONE_SECOND);
        returnDateField.sendKeys(Date2);

        return this;
    }

    public ReservationWidget selectCountry(String country) {
        helper.selectValueFromDropDown(selectCountry, country);
        return this;
    }

    public ReservationWidget dropOffTime(String dot) {
        helper.selectValueFromDropDown(dropOffTime, dot);
        return this;
    }

    /**
     * Method to click on Home page submit i.e Select my Car Button.
     *
     * @return
     **/
    public ReservationWidget selectMyCar() {
        waitForVisibilityOfElement(selectMyCarButton);
        selectMyCarButton.click();
        return this;
    }

    public ReservationWidget aboveThirtyDaysCalendarSelection(String months) {
        returnDate.click();
        threadSleep(TWO_SECONDS);

        for (int i = 1; i <= Integer.parseInt(months); i++) {
            nextMonthSelection.click();
            threadSleep(ONE_SECOND);
            waitForVisibilityOfElement(nextMonthSelection);
        }

        returnDateSelection_new4.click();
        return this;
    }



    public ReservationWidget expandDiscountCode() {
        Discount_Codes.click();
        return this;
    }

    public ReservationWidget enterCouponCode(String couponNo) {
        CouponTextField.sendKeys(couponNo);
        return this;
    }

    public ReservationWidget enterAwd(String awd) {
        AWDOrBCDOrPDN_TextField.sendKeys(awd, Keys.TAB);
        //event.tabOut("AWDOrBCDOrPDN_TextField");
        return this;
    }

    public ReservationWidget enterMembershipNo(String membershipNo) {
        membershipTextField.sendKeys(membershipNo);
        return this;
    }

    /**
     * Method to expand Avis Wizard number
     **/
    public ReservationWidget expandAvisWizardNumber() {
        AvisWizardNumberLink.click();
        return this;
    }

    public ReservationWidget enterAwdAndLastname(String wizardNo, String lastName) {
        WizardNoTextField.sendKeys(wizardNo);
        LastNameTextField.sendKeys(lastName);
        return this;
    }

    public boolean verifyNullPickUpLocationMessage() {
        return nullPickUpLocationMessageElement.getText().contains("Please enter a Pick-up Location");
    }

    public ReservationWidget selectAge(String age) {
        helper.selectValueFromDropDown(selectAge, age);
        return this;
    }


    @Override
    public void isOnPage() {
        log.info("Verify Reservation Widget");
        waitForVisibilityOfElement(pickUpLocation);
    }
}
