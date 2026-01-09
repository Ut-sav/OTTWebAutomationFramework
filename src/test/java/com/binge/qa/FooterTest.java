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
import java.util.Set;

public class FooterTest {
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
   public void footerSectionLinks() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try{driver.findElement(By.xpath("//p[text()='Not now']")).click();}
        catch (Exception e){
            e.printStackTrace();
        }

        String parentUrl = driver.getCurrentUrl();

        By footerLocator = By.xpath("//article[@class='global-footer-container']");


        WebElement footerSection = wait.until(
                ExpectedConditions.visibilityOfElementLocated(footerLocator));

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);", footerSection);

        List<WebElement> footerLinks = footerSection.findElements(By.tagName("a"));


        for(int i=0; i<footerLinks.size(); i++){

            // Re-locate to avoid stale element
            footerSection = driver.findElement(footerLocator);
            footerLinks = footerSection.findElements(By.tagName("a"));

            WebElement link = footerLinks.get(i);
            String href = link.getAttribute("href");

            if (href == null || href.isEmpty()) {
                continue;
            }

            String parentWindow = driver.getWindowHandle();

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", link);
            Thread.sleep(500);

            wait.until(ExpectedConditions.visibilityOf(link));
            js.executeScript("arguments[0].click();", link);

            String currentUrl = driver.getCurrentUrl();

            Assert.assertNotEquals(parentUrl,currentUrl );



            // Handle new tab
            Set<String> windows = driver.getWindowHandles();
            if (windows.size() > 1) {
                for (String win : windows) {
                    if (!win.equals(parentWindow)) {
                        driver.switchTo().window(win);
                        driver.close();
                    }
                }   driver.switchTo().window(parentWindow);
            } else {
                driver.navigate().back();
            }
            js.executeScript("arguments[0].scrollIntoView(true);", footerSection);
            wait.until(ExpectedConditions.visibilityOfElementLocated(footerLocator));
        }

    }

}
