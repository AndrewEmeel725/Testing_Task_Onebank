package tests;

import basetest.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

@Epic("User Authentication")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {

    @Test(description = "Verify error message for unregistered email")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Invalid Login Scenarios")
    public void invalidLoginTest() {
        HomePage home = new HomePage(getDriver());
        LoginPage login = new LoginPage(getDriver());

        home.clickSignIn();
        login.enterEmail("random5555@gmail.com");
        login.clickContinue();
        login.assertErrorMessageIsDisplayed(login.isErrorDisplayed());
      }

    @Test(description = "Verify screenshot capture on failed login attempt")
    @Severity(SeverityLevel.NORMAL)
    @Story("Negative Login Tests")
    @Description("Validates that invalid credentials trigger the proper UI error state.")
    public void negativeLoginTest() {
        HomePage home = new HomePage(getDriver());
        LoginPage login = new LoginPage(getDriver());

        home.clickSignIn();
        login.enterEmail("random123@gmail.com");
        login.clickContinue();
         login.assertErrorMessageIsDisplayed(login.isErrorDisplayed());
    }
}