package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.pages.FooterPage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class FooterTest extends BaseTest {
    FooterPage fg;;
    @BeforeMethod
    public void objectDeclaration() {
        fg = new FooterPage(driver);
        notNowClick();

    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
   public void footerSectionLinks() throws InterruptedException {
        String parentUrl = driver.getCurrentUrl();
        fg.scrollIntoFooter();

        List<String> Url = fg.footerLinkClick();
        for( String currentUrl : Url){
            Assert.assertNotEquals(parentUrl,currentUrl );
        }

    }


}
