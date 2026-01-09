package com.binge.qa;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions(driver);

        By profileLocator = By.cssSelector(".account-dropdown");
        WebElement profileMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(profileLocator));
        action.moveToElement(profileMenu).click().perform();

        By settingLocator = By.xpath("//div[text()='Settings']");
        WebElement settingButton = wait.until(ExpectedConditions.visibilityOfElementLocated(settingLocator));
        js.executeScript("arguments[0].click();", settingButton);
        WebElement settingPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()=\"Settings\"]")));
        Assert.assertEquals(settingPage.getText().trim(), "Settings");
    }

    @Test
    public void profileName() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        By profileLocator = By.cssSelector(".account-dropdown");
        WebElement profileMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(profileLocator));
        actions.moveToElement(profileMenu).click().perform();

        By settingLocator = By.xpath("//div[text()='Settings']");
        WebElement settingButton = wait.until(ExpectedConditions.visibilityOfElementLocated(settingLocator));
        js.executeScript("arguments[0].click();", settingButton);

        WebElement nameInput = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@placeholder='Enter Name Here']")));
        String randomName = "Test_" + getRandomString(5);

        actions.click(nameInput)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(randomName)
                .perform();

        WebElement saveClick = driver.findElement(By.xpath("//span[text()='Save Changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(saveClick));
        js.executeScript("arguments[0].click();", saveClick);
        List<WebElement> successToast = driver.findElements(By.xpath("//div[contains(@class,'Toastify__toast-body')]/div[contains(text(),'Subscriber Details Updated')]"));
        if (!successToast.isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfAllElements(successToast));
            Assert.assertTrue(successToast.getFirst().isDisplayed());
        }


    }

    @Test
    public void addAndRemoveToBingeList() throws InterruptedException {
        Thread.sleep(7000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".icon-icon-search-upd")));
        searchIcon.click();

        WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder,'Try Titles')]")));
        searchBar.sendKeys("Salman Khan Movies", Keys.ENTER);

        By searchResult = By.cssSelector(".listing-block");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResult));

        List<WebElement> results = driver.findElements(searchResult);
        js.executeScript("arguments[0].click();", results.get(0));

        By removeIconBy = By.xpath("//i[contains(@class,'icon-check')]");
        List<WebElement> removeBingeList =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(removeIconBy));

        if (!removeBingeList.isEmpty()) {
            js.executeScript("arguments[0].click();", removeBingeList.get(0));
        }

        By addBingeBy = By.xpath("//span[text()='My Binge List']/preceding-sibling::img");
        WebElement addToBingeListIcon =
                wait.until(ExpectedConditions.elementToBeClickable(addBingeBy));

        js.executeScript("arguments[0].click();", addToBingeListIcon);

        WebElement toastBingeList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Binge List')]")));

        Assert.assertTrue(toastBingeList.isDisplayed());


    }

    @Test
    public void playback() throws InterruptedException {
        Thread.sleep(7000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        WebElement searchIcon =
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".icon-icon-search-upd")));

        searchIcon.click();

        WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@placeholder,'Try Titles')]")));
        searchBar.click();
        searchBar.sendKeys("Icon Star Presents aha 2.0", Keys.ENTER);


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
