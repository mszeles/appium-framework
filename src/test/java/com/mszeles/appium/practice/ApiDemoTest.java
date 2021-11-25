package com.mszeles.appium.practice;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mszeles.appium.practice.page_objects.ApiDemo;

public class ApiDemoTest extends AppiumBaseTest {
	
	private static ApiDemo apiDemo;

	@BeforeMethod
	public void createPOF() {
		apiDemo = new ApiDemo(driver);
	}


	@Override
	protected String getAppPath() {
		return "lib/ApiDemos-debug.apk";
	}

	@Test
	public void preferencesTest() {
		apiDemo.preferences.click();
		apiDemo.preferenceDependencies.click();
		apiDemo.wifiCheckBox.click();
		apiDemo.secondOption.click();
		apiDemo.editBox.sendKeys("Hihi");
		apiDemo.buttons.get(1).click();
	}


	@Test
	public void validatingAllOptionsAreClickable() {
		System.out.println(apiDemo.options.size());
	}

	@Test
	public void tapAndLongpress() {
		scrollIntoView(apiDemo.viewsUiAutomator).click();
		tap(apiDemo.expandableLists);
		apiDemo.customAdapter.click();
		longPress(apiDemo.peopleNames, Duration.ofSeconds(2));
		//System.out.println(getDriver().getPageSource());
		assertTrue(apiDemo.title.isDisplayed());
	}

	@Test
	public void scrolling() {
		//Finding element by scrolling it into view first
		scrollIntoView(apiDemo.viewsUiAutomator).click();
		scrollIntoView(apiDemo.radioGroupUiAutomator).click();
	}

	@Test
	public void swipe() {
		scrollIntoView(apiDemo.viewsUiAutomator).click();
		apiDemo.dateWidgets.click();
		apiDemo.inline.click();
		apiDemo.nineOClock.click();

		swipe(apiDemo.min15, apiDemo.min45);
		assertTrue(apiDemo.min45.isSelected());
	}

	@Test
	public void dragAndDrop() {
		scrollIntoView(apiDemo.viewsUiAutomator).click();
		apiDemo.dragAndDrop.click();

		WebElement source = apiDemo.viewList.get(0);
		WebElement destination = apiDemo.viewList.get(2);
		dragAndDrop(source, destination);

	}

}
