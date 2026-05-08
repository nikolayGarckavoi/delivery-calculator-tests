package com.example.delivery_calculator_tests.pages;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeliveryCalculatorPage extends BasePage {

    @FindBy(id = "weight")
    private WebElement weightInput;

    @FindBy(id = "dimensions")
    private WebElement dimensionsSelect;

    @FindBy(id = "distance")
    private WebElement distanceInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(className = "result")
    private WebElement resultBlock;

    public DeliveryCalculatorPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8080/");
    }

    public void enterWeight(String weight) {
        weightInput.clear();
        weightInput.sendKeys(weight);
    }
    public boolean isAnyErrorDisplayed() {
        try {
            return driver.findElement(By.xpath("//span[@class='error']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void selectDimensions(String dimensionValue) {
        Select select = new Select(dimensionsSelect);
        select.selectByValue(dimensionValue);
    }

    public void enterDistance(String distance) {
        distanceInput.clear();
        distanceInput.sendKeys(distance);
    }

    public void submit() {
        wait.until(d -> submitButton.isDisplayed() && submitButton.isEnabled());
        submitButton.click();
    }

    public String getResultText() {
        wait.until(d -> resultBlock.isDisplayed());
        return resultBlock.getText();
    }


}