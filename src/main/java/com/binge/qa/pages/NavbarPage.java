package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class NavbarPage extends BasePage{

    public NavbarPage(WebDriver driver){
        super(driver);
    }

    private By headerLocator = By.xpath("//div[contains(@class,'header-left')]//a[contains(@class,'header-menu-item')]");

    @FindBy (css=".selectedLink")
    private WebElement selectedLink;

    @FindBy (css = ".login-details-container")
    private WebElement popUpLogin;

    private By rmnNotNowLoactor  = By.cssSelector( ".rmn-not-now");



    public List<Boolean> headerLinks(){
        List<Boolean> contains = new ArrayList<>();
        List<WebElement> headers = waitForVisibilityOfAllElements(headerLocator);
        for(WebElement h: headers){
            jsClick(h);
            waitForVisibility(selectedLink);
            String valueOfHeader = selectedLink.getText().trim();
            if(valueOfHeader.equalsIgnoreCase("Subscribe")){
                   waitForVisibility(popUpLogin);
                WebElement notNowCta = popUpLogin.findElement(rmnNotNowLoactor);
                waitForVisibility(notNowCta);
                waitForClickability(notNowCta);
                jsClick(notNowCta);
            }
            contains.add(selectedLink.getAttribute("class").contains("selectedLink"));
        }
        return contains;
    }
}
