package com.binge.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;


import static com.binge.qa.utils.Utility.UtilityQA.getRandomString;

public class AfterLoginPage extends BasePage {
    public AfterLoginPage(WebDriver driver){
        super(driver);
    }


    @FindBy (css=".account-dropdown")
    private WebElement accountSetting;

    @FindBy (xpath = "//div[text()='Settings']")
    private WebElement settingElement;

    @FindBy (xpath = "//h3[text()=\"Settings\"]")
    private WebElement settingPage;

    @FindBy (xpath = "//input[@placeholder='Enter Name Here']")
    private WebElement nameInput;

    @FindBy (xpath = "//span[text()='Save Changes']")
    private WebElement saveCTA;

    @FindBy(xpath = "//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Binge List')]")
    private WebElement toastBingeListElement;

    @FindBy(css=".play-btn")
    private WebElement playCTAElement;

    @FindBy(css=".endTime")
    private WebElement endTimeElement;

    private By forwardCTALocator = By.cssSelector(".icon-forward");

    private By profileUpdateToast = By.xpath("//div[contains(@class,'Toastify__toast-body')]/div[contains(text(),'Subscriber Details Updated')]");

    private By removeIconLocator = By.xpath("//i[contains(@class,'icon-check')]");
    private By addBingeListLocator = By.xpath("//span[text()='My Binge List']/preceding-sibling::img");

    private By bingeListToastLocator = By.xpath("//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Binge List')]");

    public void clickProfile(){
        waitForVisibility(accountSetting);
        action.moveToElement(accountSetting).click().perform();
    }

    public void settingClick(){
        waitForVisibility(settingElement);
        jsClick(settingElement);
    }

    public String setting(){
        waitForVisibility(settingPage);
        String settingText = settingPage.getText().trim();
        return settingText;
    }

    public void nameInputClick(){

        String randomName = "Test_" + getRandomString(5);
        waitForClickability(nameInput);
        jsClick(nameInput);
        nameInput.clear();
        nameInput.sendKeys(randomName);
    }

    public void saveCTAClick(){
        waitForClickability(saveCTA);
        jsClick(saveCTA);
    }

    public boolean isSuccessToastVisible() {
        try {
            return fluentWait(
                    d -> !d.findElements(bingeListToastLocator).isEmpty(),
                    5,
                    300
            );
        } catch (TimeoutException e) {
            return false;
        }
    }



    public void updateProfileName(){
        clickProfile();
        settingClick();
        nameInputClick();
        saveCTAClick();
    }

    public void removeBingeList() {
        List<WebElement> removeIcons = waitForVisibilityOfAllElements(removeIconLocator);
        if (!removeIcons.isEmpty()) {
            jsClick(removeIcons.get(0));

        }
        WebElement addToBingeListIcon = waitforVisibilityBy(addBingeListLocator);
            jsClick(addToBingeListIcon);

    }

    public boolean bingeLisToast(){
        try {
            return fluentWait(d -> !d.findElements(bingeListToastLocator).isEmpty(),
                    5,
                    300 );
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    public void playCTAClick(){
        waitForVisibility(playCTAElement);
        waitForClickability(playCTAElement);
        playCTAElement.click();
    }

    public String beforeWatchTime(){
        waitForVisibility(endTimeElement);
        String timeBefore = endTimeElement.getText();
        return timeBefore;
    }

    public void forwardClick() throws InterruptedException {
        Thread.sleep(1000);
        //Wait for the player control before click
         waitForPresence(forwardCTALocator);
         WebElement forward = waitForClickabilityBy(forwardCTALocator);
         forward.click();
    }

    public String afterWatchTime() throws InterruptedException {
        Thread.sleep(1000);
        waitForVisibility(endTimeElement);
        String timeAfter = endTimeElement.getText();
        return timeAfter;
    }


}
