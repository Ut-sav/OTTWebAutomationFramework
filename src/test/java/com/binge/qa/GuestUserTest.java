package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.GuestUserPage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GuestUserTest extends BaseTest {
    GuestUserPage guestUserPage;
    @BeforeMethod
    public void classDeclaration() {
        guestUserPage = new GuestUserPage(driver);
        notNowClick();
    }

@AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test (groups = {"smoke"})
    public void heroBannerClick(){
        guestUserPage.heroBannerClick();
        Assert.assertTrue(guestUserPage.contentPageView());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(guestUserPage.contentTitle());
        softAssert.assertAll();
        navigateBack();

    }

    @Test (groups = {"smoke"})
    public void bingeTopTenRail(){

        guestUserPage.scrollToBingeTopTenRail();
        guestUserPage.openFirstBingeTopTenContent();
        Assert.assertTrue(guestUserPage.contentPageView());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(guestUserPage.contentTitle());
        softAssert.assertAll();

        navigateBack();
    }

}
