package com.mszeles.appium.practice.page_objects;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class GeneralStoreCartPage extends AbstractPage {
	
	private final String PRODUCTS_IN_CART_ID = "com.androidsample.generalstore:id/productName";

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	private List<WebElement> productsInCart;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productPrices;
	
	
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmountLbl;
	
	@AndroidFindBy(className = "android.widget.CheckBox")
	public WebElement sendEmailsCheckBox;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	public WebElement termsButton;
	
	@AndroidFindBy(id = "android:id/button1")
	public WebElement closeTermsButton;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	public WebElement proceedButton;

	public GeneralStoreCartPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		
	}
	
	public List<WebElement> getProductsInCart() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(PRODUCTS_IN_CART_ID)));
		return productsInCart;
	}
	
	public BigDecimal getPriceOfItem(int index) {
		String priceStr = productPrices.get(index).getText();
		BigDecimal price = new BigDecimal(priceStr.substring(1));
		return price;
	}
	
	public BigDecimal sumProductPrices() {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < productPrices.size(); i++) {
			BigDecimal price = getPriceOfItem(i);
			sum = sum.add(price);
		}
		return sum;
	}
	
	public BigDecimal getTotalPrice() {
		String totalPriceStr = totalAmountLbl.getText();
		return new BigDecimal(totalPriceStr.split(" ")[1]);
	}
}
