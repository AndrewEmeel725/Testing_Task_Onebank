package tests;

import basetest.BaseTest;
import io.qameta.allure.*;
import models.ProductTestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;



@Epic("E-Commerce Web Automation")
@Feature("Cart Operations")
public class CartTest extends BaseTest {

    @Test(description = "Verify product details accuracy after adding to cart", dataProvider = "productData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates item name, unit price, quantity, and subtotal calculation in the cart.")

    public void addToCartTest(ProductTestData data) {
        HomePage home = new HomePage(getDriver());
        TodayPage today = new TodayPage(getDriver());
        ItemPage itemPage = new ItemPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());

        home.goToDeals();
        today.chooseCategoryByIndex(data.getCategoryIndex());
        today.selectProductByIndex(data.getProductIndex());

        itemPage.selectVariationByIndex(data.getVariationIndex());
        int selectedQty=itemPage.selectQuantity(String.valueOf(data.getQuantity()));


        String expectedName = itemPage.getItemName();
        double unitPrice = parseCurrency(itemPage.getItemPrice());

        itemPage.addToCart();
        itemPage.declineWarrantyIfPresent();
        itemPage.goToCart();

        String actualName = cartPage.getProductName();
        double actualPrice = parseCurrency(cartPage.getPrice());
        int actualQty = Integer.parseInt(cartPage.getQuantity().trim());
        double actualSubtotal = parseCurrency(cartPage.getSubtotal());

        cartPage.compareProductNames(expectedName, actualName);

        cartPage.comparePrice(unitPrice, actualPrice);
        cartPage.validateQuantity(data.getQuantity(),selectedQty,actualQty);
        cartPage.verifySubtotal(unitPrice, actualQty, actualSubtotal);


    }
    @Test(description = "Verify screenshot capture on failed cart case ", dataProvider = "productData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates item name, unit price, quantity, and subtotal calculation in the cart.")

    public void negativeAddToCartTest(ProductTestData data) {
        HomePage home = new HomePage(getDriver());
        TodayPage today = new TodayPage(getDriver());
        ItemPage itemPage = new ItemPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());

        home.goToDeals();
        today.chooseCategoryByIndex(data.getCategoryIndex());
        today.selectProductByIndex(data.getProductIndex());

        itemPage.selectVariationByIndex(data.getVariationIndex());
        int selectedQty=itemPage.selectQuantity(String.valueOf(data.getQuantity()));



        String expectedName = itemPage.getItemName();
        double unitPrice = parseCurrency(itemPage.getItemPrice());

        itemPage.addToCart();
        itemPage.declineWarrantyIfPresent();
        itemPage.goToCart();

        String actualName = cartPage.getProductName();
        double actualPrice = parseCurrency(cartPage.getPrice());
        int actualQty = Integer.parseInt(cartPage.getQuantity().trim());
        double actualSubtotal = parseCurrency(cartPage.getSubtotal());

        cartPage.compareProductNames(expectedName, actualName);

        cartPage.comparePrice(unitPrice, actualPrice);
        cartPage.validateQuantity(data.getQuantity(),selectedQty,99);
        cartPage.verifySubtotal(unitPrice, actualQty, actualSubtotal);


    }



    private double parseCurrency(String priceText) {
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }

    @DataProvider(name = "productData")
    public Object[][] getProductData() {
        return new Object[][]{
                // Format: categoryIndex, productIndex ,variationIndex, quantity
                {new ProductTestData(2,1, 2, 2)}
        };
    }
}