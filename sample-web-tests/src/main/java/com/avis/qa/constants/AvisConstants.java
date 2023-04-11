package com.avis.qa.constants;

import com.avis.qa.core.Configuration;

public class AvisConstants {

    public static final String REGRESSION = "regression";
    public static final String SANITY = "sanity";
    public static final String SMOKE = "smoke";
    public static final String TEST_DATA = "testData";

    public static String getAvisUrl(String path) {
        return Configuration.URL.replace("/en/home", path);
    }

}
