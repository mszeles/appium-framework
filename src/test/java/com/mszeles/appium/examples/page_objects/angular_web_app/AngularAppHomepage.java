package com.mszeles.appium.examples.page_objects.angular_web_app;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.mszeles.appium.framework.page_objects.AbstractPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AngularAppHomepage extends AbstractPage {
	
	@FindBy(css = ".navbar-toggler")
	public WebElement menu;
	
	@FindBy(css = "a[href*='products'")
	public WebElement productsMenuItem;
	
	@FindBy(xpath = "//li[@class='list-group-item']//a[text()='Devops']")
	public WebElement devopsItem;
		
	public AngularAppHomepage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		
	}
	
}
