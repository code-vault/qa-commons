package com.avis.qa.listeners.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    static int RETRYLIMIT = 2;
    int counter = 0;

    public static void setRetryLimit(int retryLimit) {
        RetryAnalyzer.RETRYLIMIT = retryLimit;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (counter < RetryAnalyzer.RETRYLIMIT) {
            counter++;
            return true;
        } else {
            return false;
        }

    }

}