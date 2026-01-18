package com.binge.qa.base;

import com.binge.qa.configReader.ConfigReader;
import com.binge.qa.pages.LanguagePage;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Set;


public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;
    protected LanguagePage lp;

    @BeforeMethod
    public void setUp(){
        config = new ConfigReader();

        String browser = config.getProperty("browserName");
        String url = config.getProperty("URL");
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("fireFox")){
            driver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("edge")){
            driver= new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get(url);

    }

    public void navigateBack(){
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

    public void notNowClick(){
        lp = new LanguagePage(driver);
        lp.notNowClick();
    }

    public void navigateBackWithoutCheckingWindow(){
        driver.navigate().back();
    }

}
