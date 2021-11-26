package com.mszeles.appium.examples.page_objects.general_store;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mszeles.appium.framework.page_objects.AbstractPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class GoogleHomepage extends AbstractPage {
	
	@FindBy(xpath = "//*[text()='Olvasson tovább']")
	private WebElement continueReading;
	
	@FindBy(xpath = "//*[text()='Egyetértek']")
	private WebElement agreeButton;

	private By agreeButtonBy = By.xpath("//*[text()='Egyetértek']");
	
	@FindBy(css = "[name='q']")
	private WebElement searchField;
	
	public GoogleHomepage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		
	}
	
	public void acceptTerms() {
		continueReading.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(agreeButtonBy));
		agreeButton.click();
	}
	
	public void search(String text) {
		searchField.sendKeys("Appium");
		searchField.sendKeys(Keys.ENTER);
	}
}
