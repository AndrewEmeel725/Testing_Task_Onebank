package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;


public class ItemPage extends BasePage {


    By priceWhole = By.className("a-price-whole");
    By priceFraction = By.className("a-price-fraction");
    By addBtn = By.id("add-to-cart-button");
    By qtyTrigger = By.cssSelector("select[name='quantity'], #selectQuantity, #a-autoid-0-announce");
    By cart = By.id("sw-gtc");
     private String secondAvailableSwatchLocator = "(//li[@data-csa-c-content-id='twister-desktop-twister-swatch-swatchAvailable'])[%d]";


    public ItemPage(WebDriver driver) {
        super(driver);
    }



    @Step("Add item to cart with verification")
    public void addToCart() {


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartButton = wait.until(ExpectedConditions.presenceOfElementLocated(addBtn));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
            System.out.println("Add to cart button clicked normally");

        }

        catch (Exception e) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            js.executeScript("arguments[0].click();", addToCartButton);
            System.out.println("Add to cart button clicked using js");
        }

    }

    @Step("Navigate to cart page, handling post-add refresh")
    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(cart));
            cartBtn.click();
            System.out.println("Pressed cart button normally");
        } catch (Exception e) {
            System.out.println("Cart blocked or stale. Attempting JavaScript click...");
            try {
                WebElement freshCartBtn = driver.findElement(cart);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", freshCartBtn);
            } catch (Exception jsEx) {
                throw jsEx;
            }
        }
    }

    @Step("Get the item name from the product page with waiting for visibility")
    public String getItemName() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle"))).getText().trim();
    }

    @Step("Get the item price by combining whole and fraction parts with cleaning")
    public String getItemPrice() {

        String priceWhole1 = driver.findElement(priceWhole).getText();
        String priceFraction1 = driver.findElement(priceFraction).getText();
        return priceWhole1 + "." + priceFraction1;
    }

    @Step("Select Variation with dynamic loctor ")
    public void selectVariationByIndex(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By dynamicSwatch = By.xpath(String.format(secondAvailableSwatchLocator, index));

        try {
            WebElement swatchElement = wait.until(ExpectedConditions.elementToBeClickable(dynamicSwatch));
            swatchElement.click();
            System.out.println("Variation Pressed using normal press");
        } catch (TimeoutException e) {
            System.out.println("Variation not available for that item");
        }
        catch (Exception e) {
            try {
                WebElement swatchElement = driver.findElement(dynamicSwatch);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", swatchElement);
                System.out.println("Variation Pressed using js press");
            } catch (Exception jsException) {
                throw jsException;
            }
        }
    }
    @Step("Select quantity from dropdown with dynamic locator construction and robust waiting")
    public void selectQuantity(String qtyValue1) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);


        try {
            int maxAttempts = 3;
            for (int i = 0; i < maxAttempts; i++) {
                try {

                    WebElement triggerElement = wait.until(ExpectedConditions.elementToBeClickable(qtyTrigger));

                    actions.moveToElement(triggerElement).perform();
                    triggerElement.click();
                    System.out.println("Quantity Selected successfully");
                    break;

                } catch (StaleElementReferenceException e) {
                    System.out.println("DOM refreshed. Caught StaleElementReferenceException. Retrying... Attempt " + (i + 1));
                    if (i == maxAttempts - 1) {
                        throw e;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                }
            }


            By targetQty = By.xpath("//a[@class='a-dropdown-link' and normalize-space()='" + qtyValue1 + "']");
            wait.until(ExpectedConditions.elementToBeClickable(targetQty)).click();

        } catch (TimeoutException e) {

            System.out.println("SKIPPING: Quantity dropdown not found or not clickable for this item.");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred in selectQuantity1: " + e.getMessage());
        }
    }

    @Step("Force-close warranty pop-up with frame check and retry loop")
    public void declineWarrantyIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            List<WebElement> frames = driver.findElements(By.cssSelector("iframe[id*='turbo'], iframe[id*='warranty']"));
            if (!frames.isEmpty()) {
                driver.switchTo().frame(frames.get(0));
                System.out.println("Switched to warranty iframe.");
            }

            By aggressiveLocator = By.cssSelector("#attachSiNoCoverage, #attachSiNoCoverage-announce, input[aria-labelledby='attachSiNoCoverage-announce']");
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(aggressiveLocator));


            try {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                System.out.println("Decline pressed normally");
                return;
            } catch (Exception e) {
                System.out.println("Standard click failed or intercepted. Falling back to JS loop...");
            }
            // ----------------------------------------------

            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 5000) {
                try {

                    if (driver.findElements(aggressiveLocator).isEmpty() || !btn.isDisplayed()) {
                        break;
                    }

                    js.executeScript("arguments[0].click();", btn);
                    System.out.println("decline pressed using js");

                    Thread.sleep(800);

                    if (driver.findElements(aggressiveLocator).isEmpty() || !btn.isDisplayed()) {
                        System.out.println("Pop-up successfully dismissed.");
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (TimeoutException e) {
            System.out.println("Warranty pop-up didn't show up. Moving on.");
        } finally {
            driver.switchTo().defaultContent();
        }
    }
}






