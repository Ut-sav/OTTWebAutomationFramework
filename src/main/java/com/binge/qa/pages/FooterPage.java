package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FooterPage extends BasePage{

    public FooterPage(WebDriver driver){
        super(driver);
    }

    @FindBy (xpath = "//article[@class='global-footer-container']")
    private WebElement footerElement;


    private By footerLinkLocator = By.tagName("a");


    public void scrollIntoFooter(){
        waitForVisibility(footerElement);
        scrollIntoView(footerElement);
    }

    public int getFooterLinksCount() {
        scrollIntoFooter();
        return footerElement.findElements(footerLinkLocator).size();
    }

    public List<String> footerLinkClick() throws InterruptedException {

        List<String>  navigatedUrl = new ArrayList<>();

        int linksCount = getFooterLinksCount();

        for(int i=0; i<linksCount; i++){
            scrollIntoFooter();
            WebElement links = footerElement.findElements(footerLinkLocator).get(i);
            String href = links.getAttribute("href");

            if(href==null || href.isEmpty()){
                continue;
            }
            scrollIntoView(links);
            Thread.sleep(500);

            waitForVisibility(links);
            jsClick(links);

            navigatedUrl.add(driver.getCurrentUrl());
            handleTab();

        }

     return  navigatedUrl;
    }

    public void handleTab(){

        String parentWindow = driver.getWindowHandle();
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
        scrollIntoView(footerElement);
        waitForVisibility(footerElement);
    }



}
