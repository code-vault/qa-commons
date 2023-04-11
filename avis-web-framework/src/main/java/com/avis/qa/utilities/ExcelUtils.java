package com.avis.qa.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

/**
 * Reads Excel and returns as 2D String Array
 *
 * @author ikumar
 */

public class ExcelUtils {
    private static ExcelUtils instance;
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;
    // Get sheet row num and column num
    private ExcelUtils(){

    }

    public static ExcelUtils getInstance(){
        if(instance==null) {
            synchronized (ExcelUtils.class) {
                if (instance == null) {
                    instance = new ExcelUtils();
                }
            }
        }
        return instance;
    }
    public int getSheetRowNum(String fileName,int index){

        setExcelWBook(fileName);
        ExcelWSheet = ExcelWBook.getSheetAt(index);
        return ExcelWSheet.getPhysicalNumberOfRows();
    }

    public int getSheetColNum(String fileName,int index){

        setExcelWBook(fileName);
        ExcelWSheet = ExcelWBook.getSheetAt(index);
        return ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
    }
    // Get the sheet data in 2 dimensional array from a sheet
    // Pass file path as argument and sheet Name from which data need to be retrieved.
    public String[][] getSheetData(String fileName,String sheetName){

        setExcelWBook(fileName);


        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        DataFormatter df = new DataFormatter();
        int rownum = ExcelWSheet.getPhysicalNumberOfRows();
        int colnum = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
        String[][] sheetDataObject = new String[rownum][colnum];
        for(int i=0 ;i<rownum;i++){
            for (int j=0;j<colnum;j++){

                //System.out.println("row: "+rownum + "col: "+colnum);
                //GET CELL
                XSSFCell cell1 = ExcelWSheet.getRow(i).getCell(j);
                //String value = df.formatCellValue(cell1);
                sheetDataObject[i][j]= df.formatCellValue(cell1);
               // System.out.println("Values: rownum: "+rownum + " col: "+colnum + " value: "+sheetDataObject[i][j]);
            }
        }
        return sheetDataObject;
    }

    public String[][] getSheetData(String fileName,int sheetIndex){

        setExcelWBook(fileName);
        ExcelWSheet = ExcelWBook.getSheetAt(sheetIndex);
        DataFormatter df = new DataFormatter();
        int rownum = ExcelWSheet.getPhysicalNumberOfRows();
        int colnum = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
        String[][] sheetDataObject = new String[rownum][colnum];
        for(int i=0 ;i<rownum;i++){
            for (int j=0;j<colnum;j++){

                //System.out.println("row: "+rownum + "col: "+colnum);
                //GET CELL
                XSSFCell cell1 = ExcelWSheet.getRow(i).getCell(j);
                //String value = df.formatCellValue(cell1);
                sheetDataObject[i][j]= df.formatCellValue(cell1);
                // System.out.println("Values: rownum: "+rownum + " col: "+colnum + " value: "+sheetDataObject[i][j]);
            }
        }
        return sheetDataObject;
    }

    private void setExcelWBook(String fileName) {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream("../avis-web-tests/src/main/resources/excel/"+ fileName +".xlsx");
            ExcelWBook = new XSSFWorkbook(fs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getAllSheetsData(String fileName){

        setExcelWBook(fileName);
        int numberOfSheets = ExcelWBook.getNumberOfSheets();

        int totalRows = 0;

        for (int i = 0; i < numberOfSheets ; i++) {
            totalRows = totalRows + getSheetRowNum(fileName,i);
        }
        int totalColumns = getSheetColNum(fileName,0);

        String[][] result = new String[totalRows][totalColumns];
        int l = 0;
        System.out.println(totalRows+"col"+totalColumns);

        for (int i = 0; i < numberOfSheets ; i++) {
            String[][] indexArray = getSheetData(fileName,i) ;
            for(int j=0 ;j<indexArray.length;j++){
                for (int k=0;k<indexArray[0].length;k++){
                    result[l][k] = indexArray[j][k];
                }
                l++;
            }
        }
        //System.out.println(Arrays.deepToString(result));
        return result;
        }
    }


