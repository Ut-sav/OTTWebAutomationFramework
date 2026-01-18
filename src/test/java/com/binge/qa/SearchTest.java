package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.GuestUserPage;
import com.binge.qa.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class SearchTest extends BaseTest {
    SearchPage sp;
    GuestUserPage gp;
    @BeforeMethod
    public void webAppLaunch() {

        sp = new SearchPage(driver);
        gp = new GuestUserPage(driver);
        notNowClick();
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void searchResult(){

        sp.searchIconClick();
        sp.searchBarKeys();
        sp.searchResult();

        Assert.assertTrue(gp.contentPageView());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(gp.contentTitle());
        navigateBackWithoutCheckingWindow();
        navigateBackWithoutCheckingWindow();

    }

    @Test
    public void autoSuggestions(){

        sp.searchIconClick();
        sp.searchBarAutoSuggestionKeys();

        Assert.assertTrue(sp.providerCard());
        sp.providerCardClick();

        Assert.assertTrue(sp.providePage());

    }


}
