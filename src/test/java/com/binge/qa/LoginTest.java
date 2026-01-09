package com.binge.qa;

import com.binge.qa.pages.LoginPage;
import com.binge.qa.utils.Utility.UtilityQA;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    public WebDriver driver;

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
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    @Test(dataProvider = "supplyData")
    public void notNowClick(String RMN, String OTP) {

        LoginPage loginPage = new LoginPage(driver);
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
