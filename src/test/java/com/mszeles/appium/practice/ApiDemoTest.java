package com.mszeles.appium.practice;

import static io.appium.java_client.touch.TapOptions.*;
import static io.appium.java_client.touch.offset.ElementOption.*;
import static org.testng.Assert.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;

public class ApiDemoTest extends AppiumBaseTest{

	//@Test
	public void preferencesTest() {
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();
		getDriver().findElement(By.xpath("//android.widget.TextView[@content-desc=\"3. Preference dependencies\"]")).click();
		getDriver().findElement(By.id("android:id/checkbox")).click();
		getDriver().findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
		getDriver().findElement(By.className("android.widget.EditText")).sendKeys("Hihi");
		getDriver().findElements(By.className("android.widget.Button")).get(1).click();
		//getDriver().findElementByAndroidUIAutomator("attribute(\"value\")");

	}


	//@Test
	public void validatingAllOptionsAreClickable() {
		//getDriver().findElementsByAndroidUIAutomator("new UISelector().property(value)");
		System.out.println(getDriver().findElementsByAndroidUIAutomator("new UiSelector().clickable(true)").size());
	}

	//@Test
	public void tapAndLongpress() {
		MobileElement element = scrollIntoView("text(\"2. Inline\")");
		element.click();
		//Tap
		TouchAction<?> action = new TouchAction<>(getDriver());
		WebElement expandableLists = getDriver().findElement(By.xpath("//android.widget.TextView[@content-desc='Expandable Lists']"));
		action.tap(tapOptions().withElement(element(expandableLists))).perform();

		getDriver().findElement(By.xpath("//android.widget.TextView[@text='1. Custom Adapter']")).click();
		//Longpress
		WebElement peopleNames = getDriver().findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
		action.longPress(LongPressOptions.longPressOptions().withElement(element(peopleNames)).withDuration(Duration.ofSeconds(2))).release().perform();
		//System.out.println(getDriver().getPageSource());
		assertTrue(getDriver().findElement(By.id("android:id/title")).isDisplayed());
	}

	//@Test
	public void scrolling() {
		//Finding element by scrolling it into view first
		scrollIntoView("text(\"Views\")").click();
		scrollIntoView("text(\"Radio Group\")").click();
	}

	//@Test
	public void swipe() {
		scrollIntoView("text(\"Views\")").click();
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Date Widgets']")).click();
		getDriver().findElementByAndroidUIAutomator("text(\"2. Inline\")").click();
		getDriver().findElement(By.xpath("//*[@content-desc='9']")).click();

		//Swipe: swipe = longpress + move + release
		TouchAction<?> action = new TouchAction<>(getDriver());
		WebElement min15 = getDriver().findElement(By.xpath("//*[@content-desc='15']"));
		WebElement min45 = getDriver().findElement(By.xpath("//*[@content-desc='45']"));
		action.longPress(LongPressOptions.longPressOptions().withElement(element(min15)).withDuration(Duration.ofSeconds(2)))
		.moveTo(element(min45)).release().perform();
		assertTrue(min45.isSelected());
	}

	@Test
	public void dragAndDrop() {
		scrollIntoView("text(\"Views\")").click();
		getDriver().findElementByAndroidUIAutomator("text(\"Drag and Drop\")").click();

		//Swipe: swipe = longpress + move + release
		WebElement source = getDriver().findElements(By.className("android.view.View")).get(0);
		WebElement destination = getDriver().findElements(By.className("android.view.View")).get(2);
		dragAndDrop(source, destination);

	}

}
