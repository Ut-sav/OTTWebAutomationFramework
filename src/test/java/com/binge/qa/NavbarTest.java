package com.binge.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class NavbarTest {
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

    @Test
    public void navbarTabSwitch(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        By headerLocator = By.xpath("//div[contains(@class,'header-left')]//a[contains(@class,'header-menu-item')]");

        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(headerLocator));

        for(WebElement h: headers){
            js.executeScript("arguments[0].click();",h);
            WebElement selectedLink = driver.findElement(By.cssSelector(".selectedLink"));
            wait.until(ExpectedConditions.visibilityOf(selectedLink));
            String valueOfHeader = selectedLink.getText().trim();
            if(valueOfHeader.equalsIgnoreCase("Subscribe")){
                WebElement popUpLogin = driver.findElement(By.cssSelector(".login-details-container"));
                wait.until(ExpectedConditions.visibilityOf(popUpLogin));
                WebElement notNowCta = popUpLogin.findElement(By.cssSelector(".rmn-not-now"));
                wait.until(ExpectedConditions.visibilityOf(notNowCta));
                Assert.assertTrue(notNowCta.isDisplayed());
                js.executeScript("arguments[0].click();",notNowCta);
                wait.until(ExpectedConditions.elementToBeClickable(notNowCta));
            }
            Assert.assertTrue(selectedLink.getAttribute("class").contains("selectedLink"));
            driver.navigate().back();
        }

    }
}
