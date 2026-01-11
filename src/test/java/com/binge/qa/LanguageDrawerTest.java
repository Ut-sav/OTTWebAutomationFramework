package com.binge.qa;

import com.binge.qa.pages.LanguagePage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class LanguageDrawerTest {
    public WebDriver driver;
    LanguagePage lp;

    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        lp = new LanguagePage(driver);
    }

      @AfterMethod
        public void tearDown(){
            driver.quit();
        }
    @Test
    public void SelectVideoLanguage() throws InterruptedException {

        lp.waitForVideoLanguageSelection();

        int[] indexesClick = {1, 2, 3, 4};
        lp.clickLanguage(indexesClick);

        //get the list of selected languages and apply assertion on the basis of length of selected language count
        Assert.assertEquals(lp.selectedLang(), indexesClick.length);
        lp.clickOnProceed();
        Assert.assertTrue(lp.toastMessageLang());
    }
    @Test
    public void selectMoreThanFourLanguages(){
        lp.waitForVideoLanguageSelection();
        int [] indexesOfLang = {2,3,4,5,6};

        lp.clickLanguage(indexesOfLang);
        WebElement toast = driver.findElement(By.cssSelector(".toast-message-text"));
        Assert.assertTrue(toast.getAttribute("class").contains("toast-message-text"));

    }


    @Test
    public void notNowClick(){
        lp.notNowClick();
        Assert.assertEquals(lp.navBarTextSports(),"Sports");
    }

}

