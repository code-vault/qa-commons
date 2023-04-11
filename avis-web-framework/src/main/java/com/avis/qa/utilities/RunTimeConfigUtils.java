package com.avis.qa.utilities;

import com.avis.qa.core.Configuration;
import com.avis.qa.core.TestBase;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import static com.avis.qa.core.Configuration.RUNTIME_CONFIG;
import static com.avis.qa.core.Configuration.RUNTIME_CONFIG_EXCEL_FILE_NAME;

@Log4j2
public class RunTimeConfigUtils implements IInvokedMethodListener, IAnnotationTransformer{
    private static final String[][] excelData;
    private static final Map<String, Map<String, String>> testMethodURLSAndCSVPaths = new HashMap<>();
    public static final String EXCEL_PATH = RUNTIME_CONFIG_EXCEL_FILE_NAME;

    public static int testMethodColumn;
    public static int testMethodColumnNo;
    public static long paramCount;

    public static Map<String, String[]> methodsToDataMap;
    static {
        excelData = ExcelUtils.getInstance().getAllSheetsData(EXCEL_PATH);

        paramCount = Configuration.RUNTIME_CONFIG_PARAM_COUNT;
        testMethodColumn = Configuration.RUNTIME_CONFIG_TEST_METHOD_COLUMN.toCharArray()[0];
        testMethodColumnNo = testMethodColumn - 64;
        methodsToDataMap = DataConverter.getAllMethodsDataAsMap(excelData, testMethodColumnNo);
        System.out.println("Hello");
    }

    //Method to set config URL for one method
    @SneakyThrows
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        //exit if method is not a test method or runtimeconfig is not set to true;
        if(!RUNTIME_CONFIG || !iInvokedMethod.isTestMethod()) return;
        ITestNGMethod testMethod =iInvokedMethod.getTestMethod();
        String urlKey = testMethodURLSAndCSVPaths.get(testMethod.getMethodName()).get("testMethodURLKey");
        Configuration.setURL(Configuration.getValue(urlKey));
    }

    //Method for assigning custom dataProvider if required or in case data provider is absent in the test method
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
                          Method testMethod) {
        if(!RUNTIME_CONFIG) return;
        if(methodsToDataMap.containsKey(testMethod.getName())){
            if(!annotation.getDataProvider().isEmpty()) {
                annotation.setDataProviderClass(TestBase.class);
            }
            annotation.setDataProvider("runtimeTestData");
        }
    }

    //Method to get URL and testData CSV path for a method, mapped from method-name to URL and CSVPath
    public static Map<String, String> getCSVAndURLForTestMethods(Method method){
        if(!RUNTIME_CONFIG) return null;

        String[] paramList = methodsToDataMap.get(method.getName());
        StringBuilder urlKey = new StringBuilder(Configuration.ENVIRONMENT);
        StringBuilder testDataCSVPathString = new StringBuilder("./testdata/");

        for(int i = testMethodColumnNo; i < testMethodColumnNo + paramCount; i++){
            if(paramList[i].equalsIgnoreCase(""))
                continue;
            urlKey.append("_");
            urlKey.append(paramList[i]);

            if(i != testMethodColumnNo + paramCount - 1) {
                testDataCSVPathString.append(paramList[i]).append("_");
            }
            else
                testDataCSVPathString.append("DataParameter_").append(paramList[i]).append(".csv");
        }

        Map<String, String> urlAndCSVPathMap = new HashMap<String, String>(){{
            put("testMethodURLKey", urlKey.toString());
            put("testMethodCSVPath", testDataCSVPathString.toString());
        }};

        testMethodURLSAndCSVPaths.put(method.getName(), urlAndCSVPathMap);
        return urlAndCSVPathMap;
    }

}
