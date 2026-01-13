package com.binge.qa;

import com.binge.qa.pages.AfterLoginPage;
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

import java.time.Duration;
import java.util.List;

import static com.binge.qa.utils.Utility.UtilityQA.getRandomString;

public class AfterLoginTest {
    public WebDriver driver;
    AfterLoginPage ap;
    SearchPage sp;

    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();

        try {
            driver.findElement(By.xpath("//p[text()='Not now']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement loginVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logged_out_button")));
        js.executeScript("arguments[0].click();", loginVisible);

        WebElement loginDrawer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-details-container")));

        WebElement rmnEnter = loginDrawer.findElement(By.cssSelector("input[name=\"rmn\"]"));
        rmnEnter.sendKeys("8178151424");
        WebElement getOtpCta = loginDrawer.findElement(By.xpath("//span[text()=\"Get OTP\"]"));
        js.executeScript("arguments[0].click();", getOtpCta);

        String otp = "9286";
        List<WebElement> otpInputs = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("ul.otp-input-wrapper input")));
        for (int i = 0; i < otpInputs.size(); i++) {
            otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".account-dropdown")));
        ap = new AfterLoginPage(driver);
        sp = new SearchPage(driver);

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions(driver);

        List<WebElement> settingPage =
                driver.findElements(By.xpath("//h3[text()='Settings']"));

        if (settingPage.isEmpty()) {

            WebElement profileMenu = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".account-dropdown")));
            action.moveToElement(profileMenu).click().perform();

            WebElement settingButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[text()='Settings']")));
            js.executeScript("arguments[0].click();", settingButton);
        }
        WebElement logoutCta = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//p[text()='Logout']")));
        js.executeScript("arguments[0].click();", logoutCta);

        WebElement logoutDrawer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".modal-body")));

        WebElement logoutClick =
                logoutDrawer.findElement(By.xpath("//button[text()='Log Out']"));

        js.executeScript("arguments[0].click();", logoutClick);
        driver.quit();

    }


    @Test
    public void profileSetting() {
        ap.clickProfile();
        ap.settingClick();
        Assert.assertEquals(ap.setting(), "Settings");
    }

    @Test
    public void profileName() throws InterruptedException {
        ap.updateProfileName();
        Assert.assertTrue(ap.isSuccessToastVisible());
    }

    @Test
    public void addAndRemoveToBingeList() throws InterruptedException {
        Thread.sleep(7000);
        sp.searchIconClick();
        sp.searchBarKeys();
        sp.searchResult();
        ap.removeBingeList();
        Assert.assertTrue(ap.bingeLisToast());
    }

    @Test
    public void playback() throws InterruptedException {
        Thread.sleep(7000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));

        sp.searchIconClick();
        sp.searchBarDiffKeys();


        By searchResult = By.cssSelector(".listing-block");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchResult, 0));

        WebElement firstResult = wait.until(
                ExpectedConditions.elementToBeClickable(searchResult));
        firstResult.click();

        //wait for navigation to PI page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".play-btn")));

       //Play CTA screen
        WebElement playCTA = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".play-btn")));
        playCTA.click();

        WebElement playTime = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".endTime")));
        String timeBefore = playTime.getText();
        System.out.println(timeBefore);

        Thread.sleep(1000);
        //Wait for the player control before click
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".icon-forward")));

        WebElement playIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".icon-forward")));
        playIcon.click();

        Thread.sleep(1000);
        String timeAfter = playTime.getText();
        System.out.println(timeAfter);

        Assert.assertNotEquals(timeBefore, timeAfter);
        driver.navigate().back();
    }
}
