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

   public void searchResult(){
        waitForVisibilityOfAllElements(searchResultLocator);
       List<WebElement> results = driver.findElements(searchResultLocator);
       jsClick(results.get(0));
   }



}
