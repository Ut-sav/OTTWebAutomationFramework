package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.NavbarPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class NavbarTest extends BaseTest {
    NavbarPage np;

    @BeforeMethod
    public void webAppLaunch() {
        np = new NavbarPage(driver);
        notNowClick();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void navbarTabSwitch() {
        List<Boolean> contains = np.headerLinks();

        for (boolean c : contains) {
            Assert.assertTrue(c, "Header is not selected");
            driver.navigate().back();
        }

    }
}
