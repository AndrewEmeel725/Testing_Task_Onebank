package basetest;

import util.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.time.Duration;

public class BaseTest {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String EXPECTED_URL = "https://www.amazon.eg/";
    private static final int MAX_RETRIES = 3;

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setup() {
        driver.set(DriverFactory.initDriver());
        getDriver().manage().window().maximize();
        navigateToAmazonWithRetry();
    }

    private void navigateToAmazonWithRetry() {
        int attempts = 0;
        boolean landed = false;

        while (attempts < MAX_RETRIES && !landed) {
            attempts++;
            System.out.println("Attempt " + attempts + ": Navigating to " + EXPECTED_URL);
            getDriver().get(EXPECTED_URL);

            landed = waitForCorrectPage();

            if (landed) {
                System.out.println("Successfully reached the correct Amazon Egypt page.");
            } else {
                System.out.println("Redirected to wrong page. Retrying...");
            }
        }

        if (!landed) {
            throw new RuntimeException(
                    "Failed to reach the correct Amazon Egypt page after " + MAX_RETRIES + " attempts. " +
                            "Last URL: " + getDriver().getCurrentUrl()
            );
        }
    }

    private boolean waitForCorrectPage() {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.and(
                            ExpectedConditions.urlContains("amazon.eg"),
                            ExpectedConditions.presenceOfElementLocated(By.id("nav-logo-sprites"))
                    ));
            return isCorrectPage(getDriver().getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Page did not load correctly: " + e.getMessage());
            return false;
        }
    }

    private boolean isCorrectPage(String currentUrl) {
        return currentUrl != null
                && currentUrl.contains("amazon.eg")
                && !currentUrl.contains("amazon.com")
                && !currentUrl.contains("amazon.co.uk");
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        driver.remove();
    }
}