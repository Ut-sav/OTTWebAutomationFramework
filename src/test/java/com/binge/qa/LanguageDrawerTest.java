package com.binge.qa;

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

    @BeforeMethod
    public void webAppLaunch() {

        driver = new ChromeDriver();
        driver.get("https://www.tataplaybinge.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

      @AfterMethod
        public void tearDown(){
            driver.quit();
        }
    @Test
    public void SelectVideoLanguage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
       //get the locator for multiple elements
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".select-content")));
        //provide index in array for languages selection
       int [] indexesClick = {1,2,3,4};

        List<WebElement> languages = driver.findElements(
                By.cssSelector("div.select-content"));
//loop through the array and get the languages from list for specific index
        for (int index : indexesClick) {
            WebElement lang= languages.get(index);
            lang.click();
//On the basis of attribute apply assertion
            wait.until(ExpectedConditions.attributeContains(
                    lang, "class", "active"));

            Assert.assertTrue(
                    lang.getAttribute("class").contains("active"),
                    "Language at index " + index + " is NOT selected"

            );

        }
        //get the list of selected languages and apply assertion on the basis of length of selected language count
        List <WebElement> selectedLang
                = driver.findElements(By.cssSelector(".select-content.active"));

        Assert.assertEquals(selectedLang.size(), indexesClick.length);

        WebElement proceedCTA = wait.until(ExpectedConditions.elementToBeClickable
                (By.cssSelector("button.selected-language-btn span.button-text")));

        proceedCTA.click();

        WebElement toast = driver.findElement(By.cssSelector(".toast-message-text"));
        Assert.assertTrue(toast.getAttribute("class").contains("toast-message-text"));
    }
    @Test
    public void selectMoreThanFourLanguages(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".select-content")));

        int [] indexesOfLang = {2,3,4,5,6};

        List<WebElement> languages = driver.findElements(
                By.cssSelector("div.select-content"));

        for(int index: indexesOfLang){
            WebElement lang = languages.get(index);

            lang.click();

        }
        WebElement toast = driver.findElement(By.cssSelector(".toast-message-text"));
        Assert.assertTrue(toast.getAttribute("class").contains("toast-message-text"));

    }


    @Test
    public void notNowClick(){
        driver.findElement(By.xpath("//p[text()='Not now']")).click();

        WebElement text = driver.findElement(By.xpath("(//span[text()=\"Sports\"])[1]"));
        String actualText = text.getText();

        Assert.assertEquals(actualText,"Sports");
    }

}

