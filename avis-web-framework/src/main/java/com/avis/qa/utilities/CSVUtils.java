package com.avis.qa.utilities;

import com.avis.qa.core.Configuration;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.avis.qa.core.Configuration.BRAND;
import static com.avis.qa.core.Configuration.DOMAIN;

/**
 * Reads CSV and contains a general purpose dataProvider
 *
 * @author ikumar
 */
public class CSVUtils {
    public static String method = null;
    static Object[][] obj1;
    static Object[][] obj2;
    static Object[][] obj3;
    static Object[][] obj4;
    static Object[][] obj5;
    static Object[][] obj6;
    static Object[][] obj7;
    static Object[][] obj8;
    static Object[][] obj9;
    static Object[][] obj10;
    public static String filepath = null;
    private static String cellValue;

    @SuppressWarnings("resource")
    public static Object[][] getTableArray(Method method) throws Exception {
        //filepath = ConstantsPath.DataParameter;
        loadDataParameter(method);
        String methodName = method.getName();
//        System.out.println(loadDataParameter(method));
        String[][] tabArray = null;
        BufferedReader br = null;
        String line = "";
        String[] value = null;
        int rowcount = 0;
        int colcount = 0;
        System.out.println(methodName);
        try {
            Thread.sleep(1000);
            br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null) {
                value = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (value[0].equals(methodName)) {
                    rowcount++; // count total no of rows
                    colcount = value.length; // count total no of columns

                }
            }

            if (rowcount == 0) {
                if (filepath.contains("nonUS")) {
                    filepath = filepath.replace("nonUS", "US");
                    Object[][] temp = getTableArray(method);
                    filepath = filepath.replace("US", "nonUS");
                    return temp;
                } else {
                    Assert.fail();
                }
            }
            line = "";
            br = new BufferedReader(new FileReader(filepath));
            int i = 0;

            tabArray = new String[rowcount][colcount - 1];
            while ((line = br.readLine()) != null) {
                value = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // ignore comma between double quotes
                int p = 1;
                if (value[0].equals(methodName)) {
                    for (int j = 0; j < colcount - 1; j++) {
                        tabArray[i][j] = value[p].replaceAll("^\"|\"$", ""); // remove double quotes
                        p++;
                    }
                    i++;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (tabArray); // returns the complete rows which has same rowName
    }

    /**
     * loads the data parameter file path
     */
    public static void loadDataParameter(Method method) {
        if (Configuration.RUNTIME_CONFIG) {
            filepath = RunTimeConfigUtils.getCSVAndURLForTestMethods(method).get("testMethodCSVPath");
            return;
        }
        filepath = "./testdata/" + BRAND + "_DataParameter" + "_" + DOMAIN + ".csv";
    }

    @DataProvider
    public static Object[][] testData(Method method) throws Exception {
        return CSVUtils.getTableArray(method);
    }


}