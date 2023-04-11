package com.avis.qa.core;
import com.avis.qa.core.BrowserInstance;

import com.avis.qa.listeners.report.ExtentListener;
import com.avis.qa.listeners.report.ExtentManager;
import com.avis.qa.utilities.CSVUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.avis.qa.core.Configuration.*;
import static com.avis.qa.utilities.CSVUtils.filepath;


/**
 * Class contains the Pre-requisite setup before running a Test Case
 *
 * @author ikumar
 */
@Listeners({ExtentListener.class})
@Log4j2
public class TestBase {

    private final ThreadLocal<BrowserInstance> appInstance = new ThreadLocal<>();
    
    @BeforeSuite(alwaysRun = true)
	public void beforeGroupsTest(XmlTest xmlTest) {
		deleteFile();
	}

	@AfterSuite(alwaysRun = true)
	public void afterGroupsTest(XmlTest xmlTest) throws IOException {
		String reportContent = readFile();
		String modifyContent = "<testsuite hostname=\"TestResult\" ignored=\"2\" name=\"Test\" tests=\"1\" failures=\"0\" timestamp=\"0000-00-00T00:00:00 IST\" time=\"00.000\" errors=\"0\">\n"
				+ reportContent + "\n</testsuite>";
		reportContent = reportContent.replace(reportContent, modifyContent);
		readWriteIntoFile(reportContent,false);
	}

	private void readWriteIntoFile(String reportContent, boolean isFileWritable) throws IOException {
		File myObj = new File(System.getProperty("user.dir") + "/testResult.xml");
		if (myObj.createNewFile()) {
			log.info("Test Result File is created");
		} else {
		}
		FileWriter fr = new FileWriter(myObj, isFileWritable);
		BufferedWriter f_writer = new BufferedWriter(fr);
		f_writer.write(reportContent);
		f_writer.newLine();
		f_writer.close();
	}

    @BeforeTest(alwaysRun = true)
    public void startTest(XmlTest xmlTest) {
        Configuration.setTestNGParameters(xmlTest);
        Configuration.setURL();
        ExtentListener.extent = ExtentManager.createInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodTestBase() {
        log.info("beforeMethodTestBase() called");
        if (DOCKER.equalsIgnoreCase("true"))
            appInstance.set(new DockerInstance(BROWSER));
        else
            appInstance.set(new BrowserInstance(BROWSER));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethodTestBase(ITestResult result) throws IOException {
        log.info("afterMethodTestBase() called");
        try {
            if (result.getStatus() == 1) {
                System.out.println(result.getThrowable());
                String reportContent = "<testcase name=\""+result.getMethod().getMethodName()+"\"  classname=\""+result.getTestClass()+"\"/>";
                reportContent = reportContent.replace("[TestClass name=class", "").replace("]", "");
                readWriteIntoFile(reportContent,true);
            }
            else if (result.getStatus() == 2) {
                String reportContent = "<testcase name=\""+result.getMethod().getMethodName()+"\"  classname=\""+result.getTestClass()+"\">";
                reportContent = reportContent.replace("[TestClass name=class", "").replace("]", "");
                reportContent = reportContent+"\n<failure type=\""+result.getThrowable().toString().split("Exception:")[0]+"Exception\" message=\""+result.getThrowable().toString().split("Exception:")[1].split(":")[0]+"\">\n<![CDATA["+result.getThrowable().toString().split("Exception:")[1].split(":")[1]+result.getThrowable().getMessage()+"]]>\n</failure>\n</testcase>";
                readWriteIntoFile(reportContent,true);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        getDriver().quit();
    }

    /**
     * Delete the Test Result File from previous run
     */
    public void deleteFile() {
		File file = new File(System.getProperty("user.dir") + "/testResult.xml");
		if(file.exists()) {
		if (file.delete()) {
			log.info("File deleted successfully");
		} else {
			log.info("Failed to delete the file");
		}
		}
	}
    
    /**
     * 
     * @return
     * @throws IOException
     */
	public String readFile() throws IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader buffer = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/testResult.xml"))) {
			String str;
			while ((str = buffer.readLine()) != null) {
				builder.append(str).append("\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
    private BrowserInstance getBrowserInstance() {
        return appInstance.get();
    }

    public void launchUrl(String url) {
        getBrowserInstance().start(url);
    }

    public void launchUrl() {
        getBrowserInstance().start(URL);
    }

    public WebDriver getDriver() {
        return getBrowserInstance().getDriver();
    }

    @DataProvider(name = "runtimeTestData")
    public Object[][] runtimeTestData(Method method) throws Exception {
       return CSVUtils.getTableArray(method);
    }
}
