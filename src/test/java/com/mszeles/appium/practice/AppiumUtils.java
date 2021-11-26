package com.mszeles.appium.practice;

import static io.appium.java_client.touch.offset.ElementOption.element;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;

public class AppiumUtils {
	
	private AndroidDriver<AndroidElement> driver;

	public AppiumUtils(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
	}
	
	public MobileElement scrollIntoView(String androidUISelector) {
		MobileElement element = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
				+ androidUISelector + ")"));
		return element;
	}

	/**
	 * In case we specify first what is the scrollable component, and doing the scroll afterwards,
	 * then appium will make sure that the whole item becomes visible in the list.
	 * @param scrollableParentResourceId
	 * @param itemSelector
	 * @return at the moment it is not returning the element which we scrolled, so you should fix this before use
	 */
	public MobileElement scrollIntoView(String scrollableParentResourceId, String itemSelector) {
		return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"" + scrollableParentResourceId
				+ "\")).scrollIntoView(" + itemSelector + ".instance(0))"));
	}

	public void dragAndDrop(WebElement source, WebElement destination) {
		TouchAction<?> action = new TouchAction<>(driver);
		action.longPress(LongPressOptions.longPressOptions().withElement(element(source)))
		.moveTo(element(destination)).release().perform();
	}
	
	public void tap(WebElement element) {
		TouchAction<?> action = new TouchAction<>(driver);
		action.tap(TapOptions.tapOptions().withElement(element(element))).perform();
	}
	
	public void longPress(WebElement element, Duration duration) {
		TouchAction<?> action = new TouchAction<>(driver);
		action.longPress(LongPressOptions.longPressOptions().withElement(element(element)).withDuration(duration)).release().perform();
	}
	
	public void swipe(WebElement from, WebElement to) {
		//Swipe: swipe = longpress + move + release
		TouchAction<?> action = new TouchAction<>(driver);
		action.longPress(LongPressOptions.longPressOptions().withElement(element(from)).withDuration(Duration.ofSeconds(2)))
		.moveTo(element(to)).release().perform();
	}
	
	public void scrollInBrowser(int x, int y) {
		((JavascriptExecutor)driver).executeScript("window.scrollBy(" + x + ", " + y + ")", "");
	}
}
