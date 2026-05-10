package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.*;
import java.time.Duration;

public class HomePage extends BasePage {

    By signInBtn = By.id("nav-link-accountList");
    By todaysDeals = By.cssSelector("#nav-xshop a[data-csa-c-content-id='nav_cs_gb']");
    By yourOrders = By.id("nav_prefetch_yourorders");
    By yourAddress = By.id("nav_prefetch_youraddresses");
    By lists = By.xpath("//span[normalize-space()='Your Lists']");
    By listsTab = By.id("my-lists-tab");
    By loginForm = By.id("claim-collection-container");


    public HomePage(WebDriver driver) {
        super(driver);
    }


    @Step("Click Sign In Button")
    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(signInBtn)).click();
    }

    @Step("Navigate to Today's Deals")
    public void goToDeals() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(todaysDeals)).click();
    }

    @Step("Hover over Sign In Button")
    public void hoverOverSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInBtn));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(signInBtn)).perform();
    }

    @Step("Click Your Orders")
    public void clickYourOrders() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(yourOrders)).click();
    }

    @Step("Click Your Address")
    public void clickYourAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(yourAddress)).click();
    }

    @Step("Click Your Lists")
    public void clickYourList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(lists)).click();
    }

    @Step("Check if Your Lists Tab is Displayed")
    public boolean isYourListTabDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(listsTab)).isDisplayed();
    }
    @Step("Verify redirect to login after clicking Orders")
    public void assertLoginPageDisplayedForOrders() {
        Assert.assertTrue(isLoginFormDisplayed(), "Login form not visible after clicking Orders.");
        Assert.assertTrue(driver.getCurrentUrl().contains("orders"), "URL does not contain 'orders' reference.");
    }

    @Step("Verify redirect to login after clicking Addresses")
    public void assertLoginPageDisplayedForAddress() {
        Assert.assertTrue(isLoginFormDisplayed(), "Login form not visible after clicking Address.");
        Assert.assertTrue(driver.getCurrentUrl().contains("address"), "URL does not contain 'address' reference.");
    }

    @Step("Verify redirect to login after clicking Lists")
    public void assertListPageDisplayed(boolean isDisplayed) {

        Assert.assertTrue(driver.getCurrentUrl().contains("wishlist") || driver.getCurrentUrl().contains("list"),
                "URL does not contain 'list' reference.");
        Assert.assertTrue(isDisplayed, "Login form not visible after clicking Lists.");

    }
    @Step("back to home page")
    public void backToHomePage()    {
        driver.navigate().back();
    }


    private boolean isLoginFormDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }




}

