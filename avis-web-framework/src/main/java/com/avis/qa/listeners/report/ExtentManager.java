package com.avis.qa.listeners.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.avis.qa.core.Configuration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class ExtentManager {

    public static String screenshotPath;
    public static String screenshotName;
    private static ExtentReports extent;
    public static WebDriver driver;

    public static ExtentReports createInstance() {

        Date d = new Date();
        String fileName = "";
        if(Configuration.CHRONOLOGICAL_REPORT){
          fileName = "./extent-reports/" + "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
        }else {
            fileName = "./extent-reports/" + "extent-report.html";
        }
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment", Configuration.ENVIRONMENT);
        extent.setSystemInfo("URL", Configuration.URL);
        extent.setSystemInfo("Brand", Configuration.BRAND);
        extent.setSystemInfo("Country", Configuration.DOMAIN);
        extent.setSystemInfo("Browser", Configuration.BROWSER);

        return extent;
    }

    public static void captureScreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        screenshotPath = "./screenshots/" + screenshotName;


        try {
            FileUtils.copyFile(scrFile, new File("./extent-reports/screenshots/" + screenshotName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

