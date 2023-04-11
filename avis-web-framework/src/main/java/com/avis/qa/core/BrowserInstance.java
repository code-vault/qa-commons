package com.avis.qa.core;

import com.avis.qa.listeners.report.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class Contains local driver setup
 *
 * @author ikumar
 */
@Log4j2
public class BrowserInstance {

    protected WebDriver webDriver;
    protected String browser;
    private String url;

    public BrowserInstance(String browser) {
        this.browser = browser;
    }

    protected void initializeDriver() {

        log.info("Initializing browser: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                initializeChrome();
                break;
            case "firefox":
                initializeFirefox();
                break;
            case "edge":
                initializeEdge();
                break;
            case "safari":
                initializeSafari();
                break;
            case "pixel":
                initializeMobileEmulation("Pixel 2");
                break;
            case "iphone":
                initializeMobileEmulation("iPhone X");
                break;
            case "ipad":
                initializeMobileEmulation("iPad Pro");
                break;
            default:
                throw new RuntimeException("Invalid Browser Input. Valid Browsers are chrome, firefox, safari, edge, pixel, iphone, ipad");
        }
    }

    protected void initializeChrome() {
        System.out.println("Setting up chromedriver instance");
        WebDriverManager.chromedriver().setup();
        System.out.println("Adding chrome options");
        ChromeOptions chromeOptions = new ChromeOptions();
//        System.out.println("CHROME IS STARTED3");
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.addArguments("--no-sandbox");
//        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--proxy-server='direct://'");
        chromeOptions.addArguments("--proxy-bypass-list=*");
        System.out.println("Launching chrome");
        webDriver = new ChromeDriver(chromeOptions);
//        System.out.println("CHROME IS STARTED5");
    }

    protected void initializeFirefox() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setAcceptInsecureCerts(true);

        webDriver = new FirefoxDriver(firefoxOptions);
    }

    protected void initializeEdge() {
        WebDriverManager.edgedriver().setup();
        webDriver = new EdgeDriver();
    }

    protected void initializeMobileEmulation(String deviceName) {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", deviceName);

        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        webDriver = new ChromeDriver(chromeOptions);
    }

    /**
     * INFO: Parallel tests is not possible locally on Safari due to restriction
     * https://developer.apple.com/documentation/webkit/about_webdriver_for_safari
     */
    protected void initializeSafari() {
        if (Configuration.getValue("os.name").toLowerCase().contains("mac")) {
            webDriver = new SafariDriver();
        } else {
            throw new RuntimeException("Safari is supported only on Mac Operating System.");
        }
    }

    /**
     * Configures the driver instance
     */
    protected void configureDriver() {
        log.info("Maximizing Browser and Setting Implicit Wait Timeout to :" + Configuration.DEFAULT_IMPLICIT_TIMEOUT);
        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Configuration.DEFAULT_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
    }

    protected WebDriver getDriver() {
        return webDriver;
    }

    public void start(String url) {
        initializeDriver();
        configureDriver();
        ExtentManager.driver = webDriver;
        webDriver.get(url);
    }
}
