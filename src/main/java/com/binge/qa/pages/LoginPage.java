package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy (css = ".logged_out_button")
    private WebElement loginVisible;


    private  By loginDrawer = By.cssSelector(".login-details-container");
    private  By rmnInput = By.cssSelector("input[name=\"rmn\"]");

    private By getOtpCta = By.xpath("//span[text()=\"Get OTP\"]");

    private By otpInputs = By.cssSelector("ul.otp-input-wrapper input");

    private  By multiSID = By.cssSelector(".radio-button") ;

    @FindBy (xpath = "//span[text()='Proceed To Login']")
    private  WebElement proceedCtaClick;

    private By loginToastLocator = By.xpath("//div[@class='login-success-text']");


    public void openLogin(){
        waitForVisibility(loginVisible);
        jsClick(loginVisible);
    }

    public void enterRMN(String RMN){
        WebElement drawer = waitforVisibilityBy(loginDrawer);
        WebElement rmnField = drawer.findElement(rmnInput);
        rmnField.sendKeys(RMN);
    }

    public void requestOTP(){

        WebElement drawer = waitforVisibilityBy(loginDrawer);
        WebElement getOtpClick = drawer.findElement(getOtpCta);
        jsClick(getOtpClick);
    }

    public void enterOTP(String OTP){
        List<WebElement> inputOtp = waitForVisibilityOfAllElements(otpInputs);
        for (int i = 0; i < inputOtp.size(); i++) {
            inputOtp.get(i).sendKeys(String.valueOf(OTP.charAt(i)));
        }
    }

    public void handleMultiSidIfPresent() {

        WebElement sid =
                waitForOptionalElement(multiSID, 3);

        if (sid == null) {
            return; // SID did not appear → normal login
        }

        waitForClickability(sid);
        jsClick(sid);

        waitForClickability(proceedCtaClick);
        proceedCtaClick.click();
    }


    public boolean isLoginSuccessful() {

        // ****soft polling window (not a hard wait)
        long end = System.currentTimeMillis() + 5000; // 5 seconds max

        while (System.currentTimeMillis() < end) {
            List<WebElement> loginToast =
                    driver.findElements(loginToastLocator);

            if (!loginToast.isEmpty() && loginToast.getFirst().isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    public void loginProcess(String rmn, String otp){
        openLogin();
        enterRMN(rmn);
        requestOTP();
        enterOTP(otp);
        handleMultiSidIfPresent();

    }


}
