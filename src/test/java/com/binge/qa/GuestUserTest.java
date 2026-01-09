package com.binge.qa;

import com.binge.qa.pages.GuestUserPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Set;

public class GuestUserTest {
    ;
    public WebDriver driver;
    GuestUserPage guestUserPage;
    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        guestUserPage = new GuestUserPage(driver);
        try{driver.findElement(By.xpath("//p[text()='Not now']")).click();}
        catch (Exception e){
            e.printStackTrace();
        }
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

        String parentWindow = driver.getWindowHandle();

        Set<String> windows = driver.getWindowHandles();
        if(windows.size()>1){
            for(String  w: windows){
                driver.switchTo().window(w);
                driver.close();
            }
            driver.switchTo().window(parentWindow);
        }
        else {
            driver.navigate().back();
        }


    }

    @Test (groups = {"smoke"})
    public void bingeTopTenRail(){

        guestUserPage.scrollToBingeTopTenRail();
        guestUserPage.openFirstBingeTopTenContent();
        Assert.assertTrue(guestUserPage.contentPageView());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(guestUserPage.contentTitle());
        softAssert.assertAll();

        String parentWindow = driver.getWindowHandle();

        Set<String> windows = driver.getWindowHandles();
        if(windows.size()>1){
            for(String w: windows){
                driver.switchTo().window(w);
                driver.close();
            }
            driver.switchTo().window(parentWindow);
        }
        else {
            driver.navigate().back();
        }


    }


}
