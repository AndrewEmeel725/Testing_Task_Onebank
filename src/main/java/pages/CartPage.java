package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CartPage extends BasePage {

    By productName = By.xpath("//span[contains(@class,'a-truncate-cut')][1]");
    By quantity = By.xpath("//span[@data-a-selector='inner-value']");
    By price=By.xpath("//div[@class='sc-badge-price sc-apex-cart-price']//span[@class='a-price a-text-price sc-product-price sc-white-space-nowrap a-size-medium']");
    By subtotal=By.xpath("//span[contains(@class,'sc-price') and contains(@class,'sc-white-space-nowrap')]");


    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get the product name in the cart")
    public String getProductName() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(productName));
        return driver.findElement(productName).getText();
    }


    @Step("Get the price of the product in the cart")
    public String getPrice() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(price));
        return driver.findElement(price).getText();
    }

    @Step("Get the subtotal price in the cart")
    public String getSubtotal() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(subtotal));
        return driver.findElement(subtotal).getText();
    }


    @Step("Get the quantity of the product in the cart")
    public String getQuantity() {
        return driver.findElement(quantity).getText();
    }



    @Step("Validate quantity: Expected {expected} vs Actual {actual}")
    public void compareQuantity(int expected, int actual) {
        Assert.assertEquals(actual, expected, "Quantity mismatch!");
    }

    @Step("Validate unit price: Expected {expected} vs Actual {actual}")
    public void comparePrice(double expected, double actual) {
        Assert.assertEquals(actual, expected, "Unit price mismatch!");
    }

    @Step("Validate subtotal calculation: {unitPrice} * {qty} should be {actualSubtotal}")
    public void verifySubtotal(double unitPrice, int qty, double actualSubtotal) {
        double expectedTotal = unitPrice * qty;
        Assert.assertEquals(actualSubtotal, expectedTotal, "Subtotal calculation mismatch!");
    }



    @Step("Compare product names from Item Page and Cart Page with normalization and bidirectional check")
    public void compareProductNames(String expected, String actual) {
        if (expected == null || actual == null) {
            Assert.fail("Comparison failed: One of the strings is null.");
        }

        String str1 = expected.toLowerCase().trim().replaceAll("\\.{2,}|'", "");
        String str2 = actual.toLowerCase().trim().replaceAll("\\.{2,}|'", "");

        boolean isMatch = str1.contains(str2) || str2.contains(str1);

        if (!isMatch) {
            int comparisonLength = Math.min(Math.min(str1.length(), str2.length()), 30);
            if (comparisonLength >= 10) {
                isMatch = str1.substring(0, comparisonLength).equals(str2.substring(0, comparisonLength));
            }
        }

        Assert.assertTrue(isMatch, String.format("\n[MISMATCH]\nExpected: %s\nActual: %s", expected, actual));
    }
}