package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LanguagePage extends BasePage{
     public LanguagePage(WebDriver driver){
         super(driver);
     }

    @FindBy (css= ".select-content")
    private WebElement languageBoxSelect;

     @FindBy (css="button.selected-language-btn span.button-text")
     private WebElement proceedCTALocator;

     @FindBy(css = ".toast-message-text")
     private WebElement toastMessage;

     @FindBy (xpath = "//p[text()='Not now']")
     private WebElement notNowCTA;

     @FindBy (xpath = "(//span[text()=\"Sports\"])[1]")
     private WebElement navBarText;


    private By languageRadioButtonLocator = By.cssSelector("div.select-content");

    private By selectedLangLocator = By.cssSelector(".select-content.active");

    public void waitForVideoLanguageSelection(){
        waitForVisibility(languageBoxSelect);}

    // Get all language elements
    public List<WebElement> getAllLanguages() {
        return driver.findElements(languageRadioButtonLocator);
    }

    //loop is action oriented -- Select the language
    public void clickLanguage(int[] index) {

        for(int indexes: index){
            List<WebElement> languages = getAllLanguages();
        WebElement lang = languages.get(indexes);
        waitForClickability(lang);
        lang.click();
        }
    }

    public int selectedLang(){
        List<WebElement> selectedLanguages = driver.findElements(selectedLangLocator);
        return selectedLanguages.size();
    }

    public void clickOnProceed(){
        waitForClickability(proceedCTALocator);
        proceedCTALocator.click();
    }

    public boolean toastMessageLang() {
        waitForVisibility(toastMessage);
        boolean toast = toastMessage.isDisplayed();
        return toast;
    }

    public void notNowClick(){
        try{notNowCTA.click();}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String navBarTextSports(){
        waitForVisibility(navBarText);
        String actualText = navBarText.getText().trim();
        return actualText;
    }


    }



