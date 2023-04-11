package com.avis.qa;


import com.avis.qa.core.TestBase;
import com.avis.qa.helpers.ReservationHelper;
import com.avis.qa.pages.Confirmation;
import com.avis.qa.pages.ReviewAndBook;
import com.avis.qa.utilities.CSVUtils;
import org.testng.annotations.Test;

import static com.avis.qa.constants.AvisConstants.*;
import static com.avis.qa.constants.ComparisonConstants.DUMMY_CC_ERROR_MESSAGE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Class to test the reservation functionality
 *
 * @author ikumar
 */
public class ReservationTests extends TestBase {


    /**
     * ALM Testcase: Res_Avis_US_007_Reservation_Outbound_PayNowAnonymous
     * ALM Testcase: Res_Avis_US_007_Reservation_Outbound_PayNow
     */
    @Test(groups = {REGRESSION}, dataProvider = TEST_DATA, dataProviderClass = CSVUtils.class)
    public void Reservation_Outbound_PayNow(String pickUpLocation, String firstName, String lastName, String email, String phoneNumber, String ccNumber, String cvv) {
        launchUrl();
        ReservationHelper reservationHelper = new ReservationHelper(getDriver());
        reservationHelper.Reservation_DomesticOrOutbound_PayNow(pickUpLocation, firstName, lastName, email, phoneNumber,
                ccNumber, cvv);
        Confirmation confirmation = new Confirmation(getDriver());
        assertTrue(confirmation.isConfirmationNumberDisplayed(), "Confirmation Number is not displayed");
        confirmation.cancelReservationWithConfirmationBox();
    }

    /**
     * ALM Testcase: Res_Avis_US_005_Reservation_Domestic_PayNow
     */
    @Test(groups = {REGRESSION,SMOKE}, dataProvider = TEST_DATA, dataProviderClass = CSVUtils.class)
    public void Reservation_CreditCard_ErrorMessage_PayNow(String pickUpLocation, String firstName, String lastName, String email,
                                            String phoneNumber, String ccNumber, String cvv) {
          launchUrl();
           ReservationHelper reservationHelper = new ReservationHelper(getDriver());
           reservationHelper.Reservation_DomesticOrOutbound_PayNow(pickUpLocation, firstName, lastName, email, phoneNumber,
                 ccNumber, cvv);
         ReviewAndBook reviewAndBook = new ReviewAndBook(getDriver());
         assertEquals(reviewAndBook.getDummyCreditCardErrorMessage(), DUMMY_CC_ERROR_MESSAGE, "Credit card error message is incorrect");

    }

}
