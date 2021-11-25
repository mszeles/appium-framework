package com.mszeles.appium.practice;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mszeles.appium.practice.page_objects.GeneralStoreCartPage;
import com.mszeles.appium.practice.page_objects.GeneralStoreHomepage;
import com.mszeles.appium.practice.page_objects.GeneralStoreProductsPage;
import com.mszeles.appium.practice.page_objects.GoogleHomepage;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

//GeneralStore app is a Hybrid app
public class GeneralStoreTest extends AppiumBaseTest {

	private GeneralStoreHomepage homepage;
	private GeneralStoreProductsPage productsPage;
	private GeneralStoreCartPage cartPage;
	private GoogleHomepage google;
	
	@BeforeMethod
	public void createPOF() {
		homepage = new GeneralStoreHomepage(driver);
		productsPage = new GeneralStoreProductsPage(driver);
		cartPage = new GeneralStoreCartPage(driver);
		google = new GoogleHomepage(driver);
	}
	
	@Override
	protected String getAppPath() {
		return "lib/General-Store.apk";
	}
	
	@Test
	public void homePageSuccesfullStart() {
		homepage.countrySpinner.click();
		utils.scrollIntoView(homepage.countryCanadaUIAutomator).click();
		homepage.nameField.sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		driver.hideKeyboard();
		homepage.radioFemale.click();
		homepage.letsShopButton.click();
		//assertTrue(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).isDisplayed());
	}

	@Test
	public void homePageToastMessageOnError() {
		homepage.letsShopButton.click();
		WebElement toast = homepage.toast;
		assertEquals(toast.getText(), "Please enter your name");
		assertEquals(toast.getAttribute("name"), "Please enter your name");
	}

	@Test
	public void addToCartAndCheckoutAndWebViewHandling() {
		homepage.nameField.sendKeys("Miki");
		//The keyboard might appear when entering text, so we  hide it
		driver.hideKeyboard();
		homepage.letsShopButton.click();
		//MobileElement itemName = scrollIntoView("text('Air Jordan 9 Retro')");
		//itemName.findElement(By.xpath("//parent::android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView[1]")).click();
		//Normal scrolling will only make sure that the element we specifyed will be visible, but this time we would like
		//to access something which is below that element. So we have to use a different method
		//In case we specify first what is the scrollable component, and doing the scroll afterwards, then appium
		//will make sure that the whole item becomes visible in the list.
		productsPage.addToCart("Air Jordan 9 Retro");
		productsPage.addToCart("Air Jordan 4 Retro");
		productsPage.cartButton.click();
		List<WebElement> elements = cartPage.getProductsInCart();
		assertEquals(elements.get(0).getText(),	"Air Jordan 9 Retro");
		assertEquals(elements.get(1).getText(),	"Air Jordan 4 Retro");
		assertEquals(cartPage.sumProductPrices(), cartPage.getTotalPrice());
		utils.tap(cartPage.sendEmailsCheckBox);
		utils.longPress(cartPage.termsButton, Duration.ofSeconds(1));
		cartPage.closeTermsButton.click();
		//Navigates to the webview
		cartPage.proceedButton.click();
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

		google.acceptTerms();
		google.search("Appium");
		
		//pushing Android back button
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		//Switching back context
		driver.context("NATIVE_APP");
	}

}
