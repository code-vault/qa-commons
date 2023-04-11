package com.avis.qa.dataprovider;

import com.avis.qa.utilities.DataConverter;
import com.avis.qa.utilities.ExcelUtils;
import com.avis.qa.utilities.JacksonUtils;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.*;


/**
 * Class contains all custom data providers
 *
 * @author ikumar
 */
public class AvisDataProvider {

    public static final String DATA_AS_POJO = "dataAsPojo";
    private static String[][] avisDataArray;

    static {
        avisDataArray = ExcelUtils.getInstance().getSheetData("AvisData","AvisSheet");
    }

    @SneakyThrows
    @DataProvider(name = DATA_AS_POJO)
    public Object[][] dataAsPojo(Method method) {
        List<String[]> testMethodList = DataConverter.getTestMethodDataAsList(avisDataArray,method);
        Map<String, String>[][] testMethodMap = DataConverter.convertListToTwoDHashMap(testMethodList);
        AvisData[][] data = JacksonUtils.convertTwoDMapToPOJO(testMethodMap,AvisData[][].class);

        return data;
    }





}
