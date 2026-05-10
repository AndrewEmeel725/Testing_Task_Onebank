package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;



public class TodayPage extends BasePage {

    public TodayPage(WebDriver driver) {
        super(driver);
    }




    private String categoryXpathTemplate = "(//i[contains(@class,'a-icon-radio')])[%d]";

    private final String productXpathTemplate = "(//a[contains(@class, 'a-link-normal') and .//img])[%d]";







    @Step("Select product at index {index}")
    public void selectProductByIndex(int index) {

        By dynamicProduct = By.xpath(String.format(productXpathTemplate, index));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        wait.until(ExpectedConditions.elementToBeClickable(dynamicProduct)).click();
    }

    @Step("Choose category at index {index}")
    public void chooseCategoryByIndex(int index) {

        By dynamicCategory = By.xpath(String.format(categoryXpathTemplate, index));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        wait.until(ExpectedConditions.elementToBeClickable(dynamicCategory)).click();
    }
















}
