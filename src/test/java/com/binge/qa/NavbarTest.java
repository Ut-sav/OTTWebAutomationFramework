package com.binge.qa;

import com.binge.qa.pages.NavbarPage;
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
    NavbarPage np;

    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        try {
            driver.findElement(By.xpath("//p[text()='Not now']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        np = new NavbarPage(driver);
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
