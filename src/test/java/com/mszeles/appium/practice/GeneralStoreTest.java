package com.mszeles.appium.practice;

import static org.testng.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidElement;

//GeneralStore app is a Hybrid app
public class GeneralStoreTest extends AppiumBaseTest {

	//@Test
	public void homePageSuccesfullStart() {
		getDriver().findElement(By.className("android.widget.Spinner")).click();
		scrollIntoView("text(\"Canada\")").click();
		getDriver().findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		getDriver().hideKeyboard();
		getDriver().findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
		getDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//assertTrue(getDriver().findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).isDisplayed());
	}

	//@Test
	public void homePageToastMessageOnError() {
		getDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		AndroidElement toast = getDriver().findElement(By.xpath("//android.widget.Toast"));
		assertEquals(toast.getText(), "Please enter your name");
		assertEquals(toast.getAttribute("name"), "Please enter your name");
	}

	@Test
	public void addToCartAndCheckout() {
		getDriver().findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		getDriver().hideKeyboard();
		getDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//MobileElement itemName = scrollIntoView("text('Air Jordan 9 Retro')");
		//itemName.findElement(By.xpath("//parent::android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView[1]")).click();
		//Normal scrolling will only make sure that the element we specifyed will be visible, but this time we would like
		//to access something which is below that element. So we have to use a different method
		//In case we specify first what is the scrollable component, and doing the scroll afterwards, then appium
		//will make sure that the whole item becomes visible in the list.
		String productName = "Air Jordan 9 Retro";
		String productName2 = "Air Jordan 4 Retro";
		addToCart(productName);
		addToCart(productName2);
		getDriver().findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("com.androidsample.generalstore:id/productName")));
		List<AndroidElement> elements = getDriver().findElements(By.id("com.androidsample.generalstore:id/productName"));
		assertEquals(elements.get(0).getText(),
				productName);
		assertEquals(elements.get(1).getText(),
				productName2);
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < elements.size(); i++) {
			String priceStr = getDriver().findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(i).getText();
			BigDecimal price = new BigDecimal(priceStr.substring(1));
			sum = sum.add(price);
		}
		String totalPriceStr = getDriver().findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		BigDecimal totalPrice = new BigDecimal(totalPriceStr.split(" ")[1]);
		assertEquals(sum, totalPrice);



	}

	private void addToCart(String productName) {
		String scrollableParentResourceId = "com.androidsample.generalstore:id/rvProductList";
		String itemSelector = "text(\"" + productName + "\")";
		scrollIntoView(scrollableParentResourceId, itemSelector);
		getDriver().findElement(By.xpath("//*[@text='" + productName + "']/..//*[@text='ADD TO CART']")).click();
	}

}
