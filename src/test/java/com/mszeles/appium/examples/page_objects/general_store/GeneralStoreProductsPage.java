package com.mszeles.appium.examples.page_objects.general_store;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mszeles.appium.framework.page_objects.AbstractPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class GeneralStoreProductsPage extends AbstractPage {
	
	private String scrollableParentResourceId = "com.androidsample.generalstore:id/rvProductList";

	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	public WebElement cartButton;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	private List<WebElement> productsInCart;

	public GeneralStoreProductsPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		
	}
	
	public void addToCart(String productName) {
		String itemSelector = "text(\"" + productName + "\")";
		utils.scrollIntoView(scrollableParentResourceId, itemSelector);
		driver.findElement(By.xpath("//*[@text='" + productName + "']/..//*[@text='ADD TO CART']")).click();
	}
	
}
