package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.LanguagePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LanguageDrawerTest extends BaseTest {
    LanguagePage lp;

    @BeforeMethod
    public void objectCreation() {
        lp = new LanguagePage(driver);
    }

       @AfterMethod
        public void tearDown(){
            driver.quit();
        }
    @Test
    public void SelectVideoLanguage() {
        lp.waitForVideoLanguageSelection();

        int[] indexesClick = {1, 2, 3, 4};
        lp.clickLanguage(indexesClick);

        //get the list of selected languages and apply assertion on the basis of length of selected language count
        Assert.assertEquals(lp.selectedLang(), indexesClick.length);
        lp.clickOnProceed();
        Assert.assertTrue(lp.toastMessageLang());
    }
    @Test
    public void selectMoreThanFourLanguages()  {
        lp.waitForVideoLanguageSelection();
        int [] indexesOfLang = {2,3,4,5,6};

        lp.clickLanguage(indexesOfLang);
        Assert.assertTrue(lp.toastMessageLang());

    }

    @Test
    public void notNowClick(){
        lp.notNowClick();
        Assert.assertEquals(lp.navBarTextSports(),"Sports");
    }

}

