package com.binge.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait;
    Actions action;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.action= new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitforVisibilityBy(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", element);
    }

    protected void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }


    protected List<WebElement> waitForVisibilityOfAllElements(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
        );
    }

    protected void waitForVisibilityOfAll(List<WebElement> element) {
        wait.until(
                ExpectedConditions.visibilityOfAllElements(element)
        );
    }

    protected void waitForPresence(By locator) {
        wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForClickabilityBy(By locator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForTheAttribute(WebElement element, String attribute, String value){
        return wait.until(ExpectedConditions.attributeContains(element,attribute,value));
    }

    protected <T> T fluentWait(Function<WebDriver, T> condition,
                               long timeoutInSeconds,
                               long pollingInMillis) {

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return wait.until(condition);
    }
}

