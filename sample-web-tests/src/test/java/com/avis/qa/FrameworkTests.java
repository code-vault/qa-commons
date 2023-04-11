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


    @Test(groups = {SANITY, REGRESSION, SMOKE})
    public void verifyHomepageLogo() {
        launchUrl();
        System.out.println("Hello");
    }

    @Test(groups = {REGRESSION, SANITY, SMOKE}, dataProvider = TEST_DATA, dataProviderClass = CSVUtils.class)
    public void verifyVanityURLPageTitle(String url, String title) {
        launchUrl(getAvisUrl(url));
        assertEquals("Bye", "Hello");
    }

}
