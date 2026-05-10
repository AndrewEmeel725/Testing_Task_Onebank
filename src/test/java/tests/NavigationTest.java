package tests;

import basetest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;


@Epic("Web Automation")
@Feature("Navigation & Access Control")
public class NavigationTest extends BaseTest {

    @Test(description = "Verify that restricted account pages redirect to login")
    @Severity(SeverityLevel.NORMAL)
    @Story("Restricted Page Redirection")
    @Description("This test checks multiple navigation points (Orders, Addresses, Lists) to ensure that restricted areas require authentication.")
    public void verifyRestrictedPages() {
        HomePage homePage = new HomePage(getDriver());

        homePage.hoverOverSignIn();
        homePage.clickYourOrders();
        homePage.assertLoginPageDisplayedForOrders();
        homePage.backToHomePage();

        homePage.hoverOverSignIn();
        homePage.clickYourAddress();
        homePage.assertLoginPageDisplayedForAddress();
        homePage.backToHomePage();

        homePage.hoverOverSignIn();
        homePage.clickYourList();
        homePage.assertListPageDisplayed(homePage.isYourListTabDisplayed());
    }
}