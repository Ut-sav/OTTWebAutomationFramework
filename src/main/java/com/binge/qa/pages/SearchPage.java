package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage{

    public SearchPage(WebDriver driver){
        super(driver);
    }
    @FindBy (css=".icon-icon-search-upd")
    private WebElement searchIconElement;

    @FindBy (xpath= "//input[contains(@placeholder,'Try Titles')]")
    private WebElement searchBarElement;

    @FindBy (xpath = "//img[contains(@class,'content-image') and contains(@src,'.png')]")
    private WebElement providerCardElement;

    @FindBy (css=".partner-name")
    private WebElement providerPageElement;

    private By searchResultLocator = By.cssSelector(".listing-block");

    public void searchIconClick(){
        waitForClickability(searchIconElement);
        searchIconElement.click();
    }

   public void searchBarKeys(){
        searchBarElement.sendKeys("Salman Khan Movies", Keys.ENTER);
   }
    public void searchBarDiffKeys(){
        searchBarElement.sendKeys("Icon Star Presents aha 2.0", Keys.ENTER);
    }

    public void searchBarAutoSuggestionKeys(){
        searchBarElement.sendKeys("Jio");
    }

   public void searchResult(){
        waitForVisibilityOfAllElements(searchResultLocator);
       List<WebElement> results = driver.findElements(searchResultLocator);
       jsClick(results.get(0));
   }

   public void searchResultSingle(){
        waitForNumberOfElement(searchResultLocator);
        WebElement firstResult = waitForClickabilityBy(searchResultLocator);
        firstResult.click();

   }

   public boolean providerCard(){
        waitForVisibility(providerCardElement);
        return providerCardElement.isDisplayed();
   }
   public void providerCardClick(){
        providerCardElement.click();
   }

   public boolean providePage(){
        waitForVisibility(providerPageElement);
        return providerPageElement.isDisplayed();
   }

}
