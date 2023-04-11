package com.avis.qa.helpers;

import com.avis.qa.components.ReservationWidget;
import com.avis.qa.pages.Extras;
import com.avis.qa.pages.ReviewAndBook;
import com.avis.qa.pages.Vehicles;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ReservationHelper {

    private final WebDriver driver;
    private final ReservationWidget reservationWidget;

    public ReservationHelper(WebDriver driver) {
        this.driver = driver;
        this.reservationWidget = new ReservationWidget(driver);
    }

    public void Reservation_DomesticOrOutbound_PayNow(String pickUpLocation, String firstName, String lastName,

                                                      String email, String phoneNumber, String ccNumber, String cvv) {
        reservationWidget
                .pickUpLocation(pickUpLocation)
                .calendarSelection()
                .selectMyCar();

        Vehicles vehicles = new Vehicles(driver);
        Extras extras = vehicles.step2SubmitPayNow();
        ReviewAndBook reviewAndBook = extras.Step3Submit();

        reviewAndBook
                .clickContinueReservationButton()
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .phone(phoneNumber)
                .enterCardNumber(ccNumber)
                .selectExpiryDate()
                .enterSecurityCode(cvv)
                .enterAddress()
                .checkTermsAndConditions()
                .setSelectedCountryText()
                .step4Submit();

        Assert.assertTrue(reviewAndBook.verifySelectedCountryText("U S A"), "Country Text is incorrect");
    }
}
