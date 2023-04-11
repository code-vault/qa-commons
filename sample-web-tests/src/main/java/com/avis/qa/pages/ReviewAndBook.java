package com.avis.qa.pages;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Random;

import static com.avis.qa.utilities.CommonUtils.TWO_SECONDS;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * This class contains all the elements and actions performed on HomePage
 *
 * @author ikumar
 */
@Log4j2
public class ReviewAndBook extends AbstractBasePage {

    @FindBy(xpath = "//*[contains(@id,'firstname')]")
    private WebElement firstName;

    @FindBy(id = "lastname")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "comboPhone")
    private WebElement comboPhone;

    @FindBy(xpath = "//label[contains(@for,'termsCheck')]/preceding-sibling::input")
    private WebElement termsCheckInput;

    @FindBy(xpath = "//label[contains(@for,'termsCheck')]")
    private WebElement termsCheck;

    @FindBy(xpath = "//*[contains(@id,'submit_button')]")
    private WebElement step4Submit;

    @FindBy(css = "button[type='reset']")
    private WebElement continueReservationButton;

    @FindBy(xpath = "//*[contains(@name,'expmonthselectedItem')]")
    private WebElement month;

    @FindBy(xpath = "//*[contains(@name,'selectedExpYear')]")
    private WebElement year;

    @FindBy(xpath = "//input[contains(@id,'cardnumber')]")
    private WebElement cardNumber;

    @FindBy(xpath = "//input[contains(@id,'securitycode')]")
    private WebElement step4_CVV;

    @FindBy(xpath = "//*[contains(@for,'creditcard')]")
    private WebElement creditCardCheckBox;

    @FindBy(xpath = "//*[contains(@id,'address1')]")
    private WebElement address1;

    @FindBy(xpath = "//*[@id='States']")
    private WebElement state;

    @FindBy(xpath = "(//*[contains(@id,'city')])|(//*[contains(@name,'city')])")
    private WebElement city;

    @FindBy(xpath = "//*[contains(@id,'zip')]")
    private WebElement zip;

    @FindBy(xpath = "//div[contains(@ng-repeat,'error in messageList')]")
    private WebElement DummyCreditCardErrorMessage_Paylesscar;

    @FindBy(xpath = "//button[@id='res-review-keepMod']")
    private WebElement keepModifications;

    @FindBy(id = "expirydate")
    private WebElement creditCardExpiryDateField;

    @FindBy(xpath = "//button[@id='res-review-keepMod'] | //button[@type='submit' and contains(.,'Review Modification')]|//button[@type='submit' and contains(.,'Revoir les modifications')]|//button[@type='submit' and contains(.,'Revisar modificaciones')]")
    private WebElement reviewModification;

    @FindBy(xpath = "//*[contains(@name,'flightnumberMob')]")
    private WebElement step4_flightNumber;

    @FindBy(xpath = "//*[contains(@name,'airlineobj')]")
    private WebElement step4_flightInfo;

    @FindBy(xpath = "//*[contains(@for,'profile-msg')]")
    private WebElement phoneCheckbox;

    @FindBy(id = "iatanumber")
    private WebElement IataTextFiled;

    @FindBy(id = "selectedCountry")
    private WebElement selectedCountry;

    private String selectedCountryText;


    public ReviewAndBook(WebDriver driver) {
        super(driver);
    }


    /**
     * To click continue Reservation pop-up on Review and Book Page
     */
    public ReviewAndBook clickContinueReservationButton() {
        helper.clickIfElementIsDisplayed(continueReservationButton);
        return this;
    }

    /**
     * @param fname To enter first name on review an book page
     */
    public ReviewAndBook firstname(String fname) {
        firstName.sendKeys(fname);
        return this;
    }

    /**
     * To enter first name on review an book page
     */
    public ReviewAndBook lastname(String lname) {
        lastName.sendKeys(lname);
        return this;
    }

    /**
     * To enter email on review an book page
     */
    public ReviewAndBook email(String email) {
        String emailID = email + new Random().nextInt() + "@gmail.com";
        if (emailID.contains("-")) {
            emailID = emailID.replace("-", "");
        }
        emailField.sendKeys(emailID);
        return this;
    }

    /**
     * To enter email on review an book page
     *
     * @return
     */
    public String emailReturnType(String email) {
        String emailID = email + new Random().nextInt() + "@gmail.com";
        if (emailID.contains("-")) {
            emailID = emailID.replace("-", "");
        }
        emailField.sendKeys(emailID);
        return emailID;
    }

    /**
     * To enter Phone number
     */
    public ReviewAndBook phone(String pno) {
        if (helper.isElementDisplayed(phoneField)) {
            phoneField.sendKeys(pno);
        } else {
            comboPhone.sendKeys(pno);
        }
        return this;
    }

    public ReviewAndBook smsOptInCheckbox() {
        waitForVisibilityOfElement(phoneCheckbox);
        if (!phoneCheckbox.isSelected())
            clickUsingJS(phoneCheckbox);
        return this;
    }

    public ReviewAndBook iataNumber(String iataNo) {
        IataTextFiled.sendKeys(iataNo);
        return this;

    }

    /**
     * To check T&C check box
     */
    public ReviewAndBook checkTermsAndConditions() {
        //helper.scrollToElement(termsCheckInput);
        clickUsingJS(termsCheck);
        return this;
    }

    /**
     * @param cardNo To enter credit card no
     */
    public ReviewAndBook enterCardNumber(String cardNo) {
        helper.scrollToElement(cardNumber);
        cardNumber.sendKeys(cardNo, Keys.TAB);
        return this;
        //CF.tabOut("cardNumber");
    }

    /**
     * To select expiry date
     */
    public ReviewAndBook selectExpiryDate() {
        creditCardExpiryDateField.sendKeys("1129");
        //helper.selectValueFromDropDown(month,5);
        //helper.selectValueFromDropDown(year,5);
        return this;
    }

    /**
     * @param secCode to enter CVV code
     */
    public ReviewAndBook enterSecurityCode(String secCode) {
        step4_CVV.sendKeys(secCode);
        return this;
    }

    public ReviewAndBook step4_CreditCardCheckBox() {
        if (helper.isElementDisplayed(creditCardCheckBox))
            creditCardCheckBox.click();
        return this;
    }

    public ReviewAndBook enterAddress() {
        address1.sendKeys("701 West St");
        city.sendKeys("San Antonio");
        helper.selectValueFromDropDown(state, 47);
        zip.sendKeys("99022");
        return this;
    }

    public ReviewAndBook enterAddressInboundSpecific(String country) {
        if (country.equalsIgnoreCase("Canada")) {
            address1.sendKeys("701 West St");
            city.sendKeys("Toronto");
            helper.selectValueFromDropDown(state, "Ontario");
            zip.sendKeys("M5A4R5");
        }
        if (country.equals("U S A")) {
            address1.sendKeys("701 West St");
            city.sendKeys("San Antonio");
            helper.selectValueFromDropDown(state, 47);
            zip.sendKeys("99022");
        }
        return this;
    }

    public Confirmation keepModification() {
        waitForVisibilityOfElement(keepModifications);
        clickUsingJS(keepModifications);
        return new Confirmation(driver);
    }

    /**
     * To enter flight Info
     */
    public ReviewAndBook flightInfo() {
        waitForVisibilityOfElement(step4_flightInfo);
        helper.selectValueFromDropDown(step4_flightInfo, 2);
        return this;
    }

    /**
     * To enter flight number
     */
    public ReviewAndBook enterflightNumber(String flightNumber) {
        waitForVisibilityOfElement(step4_flightNumber).sendKeys(flightNumber);
        return this;
    }

    /**
     * step 4 submit
     *
     * @return
     */
    public ReviewAndBook step4Submit() {
        helper.scrollToElement(step4Submit);
        clickUsingJS(step4Submit);
        return this;
    }

    public ReviewAndBook reviewModifications() {
        threadSleep(TWO_SECONDS);
        clickUsingJS(reviewModification);
        return this;
    }

    public ReviewAndBook setSelectedCountryText() {
        selectedCountryText = selectedCountry.getText();
        return this;
    }

    public Boolean verifySelectedCountryText(String country) {
        return selectedCountryText.contains(country);
    }

    public boolean cvvCCOValidation() {
        return helper.isElementDisplayed(step4_CVV);
    }

    public String getDummyCreditCardErrorMessage() {
        return DummyCreditCardErrorMessage_Paylesscar.getText();
    }

    @Override
    public void isOnPage() {
        log.info("Verify Review And Book Page");
        waitForVisibilityOfElement(firstName);
    }
}
