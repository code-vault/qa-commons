package com.avis.qa.components;

import com.avis.qa.core.AbstractBasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.avis.qa.utilities.CommonUtils.TWO_SECONDS;
import static com.avis.qa.utilities.CommonUtils.threadSleep;


/**
 * This class contains all the elements and actions performed on Login Widget
 *
 * @author ikumar
 */
@Log4j2
public class LoginWidget extends AbstractBasePage {

    @FindBy(xpath = "((//a[@id='res-login-profile'])[2]) | ((//a[contains(text(),'Sign In')])[1])")
    private WebElement HeaderLoginButton;

    @FindBy(id = "username")
    private WebElement UserName;

    @FindBy(id = "password")
    private WebElement Password;

    @FindBy(xpath = "//button[@id='res-login-profile']")
    private WebElement LoginButton;

    @FindBy(id = "PicLoc_value")
    private WebElement pickUpLocation;


    public LoginWidget(WebDriver driver) {
        super(driver);
    }

    public LoginWidget loginHeaderclick() {
        clickUsingJS(waitForVisibilityOfElement(HeaderLoginButton));
        return this;
    }

    public LoginWidget login(String emailNew, String passwordNew) {
        waitForVisibilityOfElement(UserName);
        UserName.sendKeys(emailNew);
        waitForVisibilityOfElement(Password);
        Password.sendKeys(passwordNew);
        LoginButton.click();
        threadSleep(TWO_SECONDS);
        return this;
    }

    public ReservationWidget clickPickupLocation() {
        threadSleep(TWO_SECONDS);
        waitForVisibilityOfElement(pickUpLocation).click();
        return new ReservationWidget(driver);
    }


    @Override
    public void isOnPage() {
        log.info("Verify Login Widget");
        waitForVisibilityOfElement(HeaderLoginButton);
    }
}
