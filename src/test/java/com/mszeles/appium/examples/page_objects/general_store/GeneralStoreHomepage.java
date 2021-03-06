package com.mszeles.appium.examples.page_objects.general_store;

import org.openqa.selenium.WebElement;

import com.mszeles.appium.framework.page_objects.AbstractPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class GeneralStoreHomepage extends AbstractPage {
	
	@AndroidFindBy(className = "android.widget.Spinner")
	public WebElement countrySpinner;
	
	public String countryCanadaUIAutomator = "text(\"Canada\")";
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	public WebElement nameField;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	public WebElement radioFemale;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	public WebElement letsShopButton;
	
	@AndroidFindBy(xpath = "//android.widget.Toast")
	public WebElement toast;
	
	public GeneralStoreHomepage(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
}
