package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.LoginPage;
import com.binge.qa.utils.Utility.UtilityQA;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {
    public LoginPage loginPage;
    @BeforeMethod
    public void loginTestSetup() {
        loginPage = new LoginPage(driver);
        notNowClick();
    }

   @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(dataProvider = "supplyData")
    public void loginProcess(String RMN, String OTP) {

        loginPage.loginProcess(RMN, OTP);
        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "Login success toast is not displayed");
    }

    @DataProvider
    public Object[][] supplyData(){
        Object[][] data = UtilityQA.getTestDataFromExcel("Sheet1");
        return data;
    }

}
