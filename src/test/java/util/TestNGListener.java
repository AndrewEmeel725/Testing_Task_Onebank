package util;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = basetest.BaseTest.getDriver();
        if (driver != null) {
            ScreenshotUtil.takeScreenshot(result.getMethod().getMethodName(),driver);
            System.out.println("Test Failed: " + result.getMethod().getMethodName());
        }else {
            System.out.println("Test Failed: " + result.getMethod().getMethodName() + " - Could not capture screenshot (driver was null).");
        }


    }
}
