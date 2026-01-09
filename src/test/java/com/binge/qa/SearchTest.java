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
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class SearchTest {
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
    public void searchResult(){

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        WebElement searchIcon = driver.findElement(By.cssSelector(".icon-icon-search-upd"));
        wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(searchIcon).click().build().perform();
        WebElement searchBar = driver.findElement(By.xpath("//input[contains(@placeholder,'Try Titles')]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBar)).click();

        actions.sendKeys("Salman Khan movies")
                .sendKeys(Keys.ENTER)
                .perform();
        List<WebElement> contentCardSearch = driver.findElements(By.cssSelector(".listing-block"));
        wait.until(ExpectedConditions.visibilityOfAllElements(contentCardSearch));
        actions.moveToElement(contentCardSearch.getFirst()).click().perform();

        WebElement contentPage = driver.findElement(By.xpath("//span[contains(text(),'Related')]"));
        Assert.assertTrue(contentPage.isDisplayed());

        WebElement contentPageTitle = driver.findElement(By.cssSelector(".heading-title"));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(contentPageTitle.isDisplayed());
        driver.navigate().back();
        driver.navigate().back();

    }

    @Test
    public void autoSuggestions(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));

        WebElement searchIcon = driver.findElement(By.cssSelector(".icon-icon-search-upd"));
        wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(searchIcon).click().build().perform();
        WebElement searchBar = driver.findElement(By.xpath("//input[contains(@placeholder,'Try Titles')]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBar)).click();

        actions.sendKeys("Jio").perform();

        By providerCardLocator = By.xpath("//img[contains(@class,'content-image') and contains(@src,'.png')]");

        WebElement providerCard = wait.until(ExpectedConditions.visibilityOfElementLocated(providerCardLocator));
        Assert.assertTrue(providerCard.isDisplayed());

        actions.moveToElement(providerCard).click().perform();

        By providerPageLocator = By.cssSelector(".partner-name");

        WebElement providerPage = wait.until(ExpectedConditions.visibilityOfElementLocated(providerPageLocator));

        Assert.assertTrue(providerPage.isDisplayed());

    }



}
