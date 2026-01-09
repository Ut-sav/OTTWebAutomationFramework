package com.binge.qa;

import com.binge.qa.utils.Utility.UtilityQA;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js  =(JavascriptExecutor) driver;

        WebElement loginVisible= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logged_out_button")));
        js.executeScript("arguments[0].click();",loginVisible);

        WebElement loginDrawer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-details-container")));

        WebElement rmnEnter = loginDrawer.findElement(By.cssSelector("input[name=\"rmn\"]"));
        rmnEnter.sendKeys(RMN);
        WebElement getOtpCta = loginDrawer.findElement(By.xpath("//span[text()=\"Get OTP\"]"));
        js.executeScript("arguments[0].click();",getOtpCta);

        //sending otp
        List<WebElement> otpInputs = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("ul.otp-input-wrapper input")));
        for (int i = 0; i < otpInputs.size(); i++) {
            otpInputs.get(i).sendKeys(String.valueOf(OTP.charAt(i)));
        }

        List<WebElement> multiSID = driver.findElements(By.cssSelector(".radio-button"));

        if (!multiSID.isEmpty()) {
            WebElement sidClick = multiSID.get(0);

            wait.until(ExpectedConditions.elementToBeClickable(sidClick));
            js.executeScript("arguments[0].click();", sidClick);

            WebElement proceedCtaClick = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[text()='Proceed To Login']")));
            proceedCtaClick.click();
        }

        List<WebElement> loginToast = driver.findElements(
                By.xpath("//div[@class='login-success-text']")
        );

        if (!loginToast.isEmpty()) {
            // Login success message is displayed
            //prevent false passes loginToast.get(0).isDisplayed()
            // Confirms actual UI visibility
            wait.until(ExpectedConditions.visibilityOfAllElements(loginToast));
            Assert.assertTrue(loginToast.getFirst().isDisplayed(),
                    "Login success toast is not displayed");
        }

    }

    @DataProvider
    public Object[][] supplyData(){
        Object[][] data = UtilityQA.getTestDataFromExcel("Sheet1");
        return data;
    }

}
