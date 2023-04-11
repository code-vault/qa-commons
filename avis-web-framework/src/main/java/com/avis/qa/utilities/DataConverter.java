package com.avis.qa.utilities;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestNGMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class contains helpers for data conversion and combination for DataProviders
 *
 * @author ikumar
 */

@Log4j2
public class DataConverter {


    public static List<String[]> getTestMethodDataAsList(List<String[]> list, Method method) {
        List<String[]> stringList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++)  {   //rows or lines
            if(method.getName().equalsIgnoreCase(list.get(i)[0].trim()))
            {
                stringList.add(list.get(i));
                }
            }
        System.out.println(stringList);
        return stringList;
    }

    public static List<String[]> getTestMethodDataAsList(String[][] arr, Method method) {
        List<String[]> stringList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++)  {   //rows or lines
            if(method.getName().equalsIgnoreCase(arr[i][0]) || i==0)
            {
                stringList.add(arr[i]);
            }
        }
        System.out.println(stringList);
        return stringList;
    }

    public static List<String[]> getTestMethodDataAsList(String[][] arr, Method method, int columnNo) {
        List<String[]> stringList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++)  {   //rows or lines
            if(method.getName().equalsIgnoreCase(arr[i][columnNo-1].trim()) || i==0)
            {
                stringList.add(arr[i]);
            }
        }
        System.out.println(stringList);
        return stringList;
    }

    //Method to get map of all methods in excel mapped from method name to list of parameters
    public static Map<String, String[]> getAllMethodsDataAsMap(String[][] arr, int columnNo){
        Map<String, String[]> methodsToDataMap = new HashMap<>();

        for (int i = 1; i < arr.length; i++) {
            methodsToDataMap.put(arr[i][columnNo - 1].trim(), arr[i]);
        }
        return methodsToDataMap;
    }


    public static String[][] convertListToTwoDArray(List<String[]> list) {

        String[][] stringArr = new String[list.size()][list.get(0).length-1];

        for (int i = 0; i < list.size(); i++) {    //rows or lines
            for (int j = 0; j < list.get(0).length-1; j++) { //columns
                stringArr[i][j] = list.get(i)[j+1].trim();
            }
        }
        //System.out.println(Arrays.deepToString(stringArr));
        return stringArr;
    }


    public static Map<String, String>[][] convertListToTwoDHashMap(List<String[]> list) {

        Map<String, String>[][] twoDHashMap = new HashMap[list.size()-1][1];

        for (int i = 1; i < list.size(); i++) {    //rows or lines
            HashMap<String, String> map = new HashMap<>();
            for (int j = 0; j < list.get(i).length; j++) {   //columns
                map.put(list.get(0)[j].trim(), list.get(i)[j].trim());
            }
            twoDHashMap[i-1][0] = map;
        }
        return twoDHashMap;
    }

    public static Object[][] combinationOfData(Object[][] firstObject, Object[][] secondObject) {
        int combinedLength =  firstObject.length* secondObject.length;
        Object[][] combinationObject = new Object[combinedLength][2];

        int k = 0;
        for (int i = 0; i < firstObject.length ; i++) {
            for (int j = 0; j < secondObject.length; j++) {
                combinationObject[k][0] = firstObject[i][0];
                combinationObject[k][1] = secondObject[j][0];
                k++;
            }
        }
        return combinationObject;
    }


    public static Object[][] combinationOfData(Object[][] firstObject, Object[][] secondObject, Object[][] thirdObject) {
        int combinedLength = firstObject.length * secondObject.length * thirdObject.length;
        Object[][] combinationObject = new Object[combinedLength][2+thirdObject[0].length];

        int l = 0;
        for (int i = 0; i < firstObject.length; i++) {
            for (int j = 0; j < secondObject.length; j++) {
                for (int k = 0; k < thirdObject.length; k++) {
                    combinationObject[l][0] = firstObject[i][0];
                    combinationObject[l][1] = secondObject[j][0];
                    for (int m = 0; m < thirdObject[0].length ; m++) {
                        combinationObject[l][2+m] = thirdObject[k][m];
                    }
                    l++;
                }
            }
        }
        return combinationObject;
    }



}
