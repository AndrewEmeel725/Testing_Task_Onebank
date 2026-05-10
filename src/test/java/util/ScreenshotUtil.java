package util;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotUtil {


    public static void takeScreenshot(String screenName, WebDriver driver) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenPath = new File("screenshots/" + screenName + ".png");
            FileUtils.copyFile(screenshot, screenPath);
            AllureUtils.attachScreenshot(screenName, screenPath.getPath());
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }


    }
}


