package com.avis.qa.pages;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.avis.qa.utilities.CommonUtils.TWO_SECONDS;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * This class contains all the elements and actions performed on Vehicles Page
 *
 * @author ikumar
 */
@Log4j2
public class Vehicles extends AbstractBasePage {

    @FindBy(xpath = "(//a[contains(text(),'Pay Now')])[4]|(//a[contains(text(),'Payer maintenant')])[1]|(//a[contains(text(),'Pagar ahora')])[1]|(//a[contains(text(),'Select')])[1]")
    private WebElement step2PayNowSubmitButton;

    @FindBy(xpath = "(//a[contains(text(),'Select')])[4]|(//a[contains(text(),'Select')])[3]|(//a[@id='res-vehicles-pay-later'])[1] | (//a[contains(text(),'Pay Later')])[2]|(//a[contains(text(),'Pay at Counter')])[1]|(//a[contains(text(),'Paiement au comptoir')])[1]|(//a[contains(text(),'Pagar en el mostrador')])[1]|((//p[contains(@class,'totalPay')]/../a)[1])")
    private WebElement step2SubmitButton;

    @FindBy(css = "li[title='Select a Car']")
    private WebElement selectACarText;

    @FindBy(xpath = "(//a[contains(text(),'Pay Later')])[4]|(//a[contains(text(),'Pay at Counter')])[4]|(//a[contains(text(),'Select')])[4]|(//a[contains(text(),'Paiement au comptoir')])[1]|(//a[contains(text(),'Pagar en el mostrador')])[1]|((//p[contains(@class,'totalPay')]/../a)[4]|(//a[contains(text(),'Select')])[4])")
    private WebElement payAtCounter2;

    @FindBy(xpath = "(//li[@ng-click='vm.selectCurrency(option)']/a)[1]")
    private WebElement Currencyvalue1;

    @FindBy(xpath = "(//li[@ng-click='vm.selectCurrency(option)']/a)[2]")
    private WebElement Currencyvalue2;

    @FindBy(xpath = "//a[contains(@class,'dropdown-toggle vehAllVehDrpDwn s-dropdown currencyF&S')]")
    private WebElement currencyField;

    @FindBy(xpath = "//div[contains(@class,'info-error-msg-text')]/span")
    private WebElement underAgeMsg_Step2;

    @FindBy(xpath = "(//del[@ng-if='car.displayRate.displayStkPayLater'])[1]|(//p[@ng-if='car.displayRate.displayStkSelect'])[1]")
    private WebElement StrikreThroughPriceIndicator;

    @FindBy(xpath = "//div[contains(@class,'info-key-drop-text')]/p")
    private WebElement keyDropInfo;

    public Vehicles(WebDriver driver) {
        super(driver);
    }


    public Extras step2Submit() {
        threadSleep(TWO_SECONDS);
        helper.scrollToElement(step2SubmitButton);
        clickUsingJS(step2SubmitButton);
        return new Extras(driver);
    }


    /**
     * Method to click on Pay Now
     */
    public Extras step2SubmitPayNow() {
        threadSleep(TWO_SECONDS);
        helper.scrollToElement(step2PayNowSubmitButton);
        clickUsingJS(step2PayNowSubmitButton);
        return new Extras(driver);
    }

    public Extras payLater() {
        payAtCounter2.click();
        return new Extras(driver);
    }

    /**
     * Method on click on Currency Label
     *
     * @return
     */
    public boolean isCurrencyValueDisplayed() {
        clickUsingJS(currencyField);
        return Currencyvalue2.isDisplayed() && Currencyvalue1.isDisplayed();
    }

    public boolean isStrikreThroughPriceIndicatorDisplayed() {
        return StrikreThroughPriceIndicator.isDisplayed();
    }

    public Vehicles verifyUnderAgeMsg() {
        underAgeMsg_Step2.getText().contains("An underage surcharge is applicable except at participating locations.");
        return this;
    }


    @Override
    public void isOnPage() {
        waitForVisibilityOfElement(selectACarText);
    }
}
