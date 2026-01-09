package com.binge.qa.utils.Utility;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Random;

public class UtilityQA{


    public static Object[][] getTestDataFromExcel(String sheetName) {
        Object[][] data = null;
        try {
            File xlsx = new File(System.getProperty("user.dir") +
                    "\\src\\main\\java\\com\\binge\\qa\\testData\\LoginData.xlsx");

            FileInputStream fis = new FileInputStream(xlsx);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int rows = sheet.getLastRowNum();
            int col = sheet.getRow(0).getLastCellNum();

         data = new Object[rows][col];
            for(int i=0; i<rows; i++){
                XSSFRow row = sheet.getRow(i+1);
                for(int j=0; j<col; j++){
                    XSSFCell cell = row.getCell(j);
                    CellType cellType = cell.getCellType();
                    switch (cellType){
                        case STRING :
                            data[i][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[i][j]=String.valueOf((long) cell.getNumericCellValue());
                            break;
                    }
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static String getRandomString(int length) {
            String chars = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < length; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        }
    }


