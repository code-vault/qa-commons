package com.avis.qa;


import com.avis.qa.core.TestBase;
import com.avis.qa.pages.Homepage;
import com.avis.qa.utilities.CSVUtils;
import org.testng.annotations.Test;

import java.util.List;

import static com.avis.qa.constants.AvisConstants.*;
import static org.testng.Assert.*;

/**
 * Class to test the framework
 *
 * @author ikumar
 */
public class FrameworkTests extends TestBase {


    @Test(groups = {REGRESSION, SANITY, SMOKE}, dataProvider = TEST_DATA, dataProviderClass = CSVUtils.class)
    public void verifyHomepageLogo() {
        launchUrl();
        Homepage homePage = new Homepage(getDriver());
        assertTrue(homePage.isAvisLogoDisplayed(), "Avis logo is not displayed");
    }

    @Test(groups = {REGRESSION, SANITY, SMOKE}, dataProvider = TEST_DATA, dataProviderClass = CSVUtils.class)
    public void verifyVanityURLPageTitle(String url, String title) {
        launchUrl(getAvisUrl(url));
        assertTrue(getDriver().getTitle().contains(title), "Page title is incorrect");
    }

}
