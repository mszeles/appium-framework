package com.mszeles.appium.practice;

import static org.testng.Assert.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

//GeneralStore app is a Hybrid app
public class GeneralStoreTest extends AppiumBaseTest {

	@Override
	protected String getAppPath() {
		return "lib/General-Store.apk";
	}
	
	//@Test
	public void homePageSuccesfullStart() {
		driver.findElement(By.className("android.widget.Spinner")).click();
		scrollIntoView("text(\"Canada\")").click();
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		driver.hideKeyboard();
		driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//assertTrue(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).isDisplayed());
	}

	//@Test
	public void homePageToastMessageOnError() {
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		AndroidElement toast = driver.findElement(By.xpath("//android.widget.Toast"));
		assertEquals(toast.getText(), "Please enter your name");
		assertEquals(toast.getAttribute("name"), "Please enter your name");
	}

	@Test
	public void addToCartAndCheckoutAndWebViewHandling() {
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		driver.hideKeyboard();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
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
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("com.androidsample.generalstore:id/productName")));
		List<AndroidElement> elements = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
		assertEquals(elements.get(0).getText(),
				productName);
		assertEquals(elements.get(1).getText(),
				productName2);
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < elements.size(); i++) {
			BigDecimal price = getPriceOfItem(i);
			sum = sum.add(price);
		}
		String totalPriceStr = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		BigDecimal totalPrice = new BigDecimal(totalPriceStr.split(" ")[1]);
		assertEquals(sum, totalPrice);
		AndroidElement checkBox = driver.findElement(By.className("android.widget.CheckBox"));
		tap(checkBox);
		AndroidElement termsButton = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
		longPress(termsButton, Duration.ofSeconds(1));
		driver.findElement(By.id("android:id/button1")).click();
		//Navigates to the webview
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Switching context
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context((String)contextNames.toArray()[1]); // set context to WEBVIEW_1
		driver.findElement(By.xpath("//*[text()='Olvasson tovább']")).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Egyetértek']")));
		driver.findElement(By.xpath("//*[text()='Egyetértek']")).click();
		driver.findElement(By.cssSelector("[name='q']")).sendKeys("Appium");
		driver.findElement(By.cssSelector("[name='q']")).sendKeys(Keys.ENTER);
		
		//pushing Android back button
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		//Switching back context
		driver.context("NATIVE_APP");
		


	}

	private void addToCart(String productName) {
		String scrollableParentResourceId = "com.androidsample.generalstore:id/rvProductList";
		String itemSelector = "text(\"" + productName + "\")";
		scrollIntoView(scrollableParentResourceId, itemSelector);
		driver.findElement(By.xpath("//*[@text='" + productName + "']/..//*[@text='ADD TO CART']")).click();
	}
	
	private BigDecimal getPriceOfItem(int index) {
		String priceStr = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(index).getText();
		BigDecimal price = new BigDecimal(priceStr.substring(1));
		return price;
	}

}
