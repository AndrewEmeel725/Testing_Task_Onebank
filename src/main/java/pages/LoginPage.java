package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {

    By emailField = By.id("ap_email_login");
    By continueBtn = By.cssSelector("#continue");
    By msgError = By.cssSelector("#intent-confirmation-container h1");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Click Continue Button")

    public void clickContinue() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }


    @Step("Check if error message is displayed")
    public boolean isErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(ExpectedConditions.visibilityOfElementLocated(msgError)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {

            return false;
        }
    }




    @Step("Assert that error message is displayed for invalid credentials")
    public void assertErrorMessageIsDisplayed(boolean isDisplayed) {

        Assert.assertTrue(isDisplayed, "Error message was not displayed for invalid credentials.");
    }
}
