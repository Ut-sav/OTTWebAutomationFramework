package com.binge.qa;

import com.binge.qa.pages.GuestUserPage;
import com.binge.qa.pages.SearchPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class SearchTest {
    public WebDriver driver;
    SearchPage sp;
    GuestUserPage gp;
    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        try{driver.findElement(By.xpath("//p[text()='Not now']")).click();}
        catch (Exception e){
            e.printStackTrace();
        }
        sp = new SearchPage(driver);
        gp = new GuestUserPage(driver);
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
        driver.navigate().back();
        driver.navigate().back();

    }

    @Test
    public void autoSuggestions(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));

        sp.searchIconClick();
        sp.searchBarAutoSuggestionKeys();

        Assert.assertTrue(sp.providerCard());
        sp.providerCardClick();

        Assert.assertTrue(sp.providePage());

    }


}
