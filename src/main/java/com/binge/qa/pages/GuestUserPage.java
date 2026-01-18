package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GuestUserPage extends BasePage{

    public GuestUserPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".container.hero-banner.false.new-hero-banner")
    private WebElement heroBanner;

    @FindBy(xpath = "//div[contains(@class,\"rail-title-block\")]")
    private WebElement contentDetailPage;

    @FindBy(css=".heading-title")
    private  WebElement contentTitle;

    @FindBy(xpath = "//span[normalize-space()='Binge Top 10']")
    private WebElement bingeTopTen;

    //Locator for Binge Top 10 content cards
    private By bingeTopTenContentCards = By.xpath(
            "//span[normalize-space()='Binge Top 10']" +
                    "/ancestor::div[contains(@class,'divider-title-section')]" +
                    "/following-sibling::ul[contains(@class,'listing-horizontal')]//li"
    );

    public void heroBannerClick(){
        waitForClickability(heroBanner);
        heroBanner.click();
    }
    public boolean contentPageView(){
        waitForVisibility(contentDetailPage);
        return contentDetailPage.isDisplayed();
    }

    public boolean contentTitle(){
        waitForVisibility(contentTitle);
        return contentTitle.isDisplayed();
    }

    public void scrollToBingeTopTenRail(){
        waitForVisibility(bingeTopTen);
        scrollIntoView(bingeTopTen);
    }


    public void openFirstBingeTopTenContent() {

        //wait & scroll rail
        waitForVisibility(bingeTopTen);
        scrollIntoView(bingeTopTen);

        //wait for cards
        List<WebElement> cards = waitForVisibilityOfAllElements(bingeTopTenContentCards);

        //scroll & JS click first card
        WebElement firstCard = cards.getFirst();
        scrollIntoView(firstCard);
        jsClick(firstCard);
    }
}
