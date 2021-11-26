package com.mszeles.appium.examples;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mszeles.appium.examples.page_objects.api_demo.ApiDemo;
import com.mszeles.appium.framework.AppiumBaseTest;

public class ApiDemoTest extends AppiumBaseTest {
	
	private ApiDemo apiDemo;

	@BeforeMethod
	public void createPOF() {
		apiDemo = new ApiDemo(driver);
	}


	@Override
	protected String getAppPath() {
		return "lib/ApiDemos-debug.apk";
	}

	@Test(dataProvider = "ApiDemoInputData", dataProviderClass = TestData.class)
	public void preferencesTest(String input) {
		apiDemo.preferences.click();
		apiDemo.preferenceDependencies.click();
		apiDemo.wifiCheckBox.click();
		apiDemo.secondOption.click();
		apiDemo.editBox.sendKeys(input);
		apiDemo.buttons.get(1).click();
	}


	@Test
	public void validatingAllOptionsAreClickable() {
		System.out.println(apiDemo.options.size());
	}

	@Test
	public void tapAndLongpress() {
		utils.scrollIntoView(apiDemo.viewsUiAutomator).click();
		utils.tap(apiDemo.expandableLists);
		apiDemo.customAdapter.click();
		utils.longPress(apiDemo.peopleNames, Duration.ofSeconds(2));
		//System.out.println(getDriver().getPageSource());
		assertTrue(apiDemo.title.isDisplayed());
	}

	@Test
	public void scrolling() {
		//Finding element by scrolling it into view first
		utils.scrollIntoView(apiDemo.viewsUiAutomator).click();
		utils.scrollIntoView(apiDemo.radioGroupUiAutomator).click();
	}

	@Test
	public void swipe() {
		utils.scrollIntoView(apiDemo.viewsUiAutomator).click();
		apiDemo.dateWidgets.click();
		apiDemo.inline.click();
		apiDemo.nineOClock.click();

		utils.swipe(apiDemo.min15, apiDemo.min45);
		assertTrue(apiDemo.min45.isSelected());
	}

	@Test
	public void dragAndDrop() {
		utils.scrollIntoView(apiDemo.viewsUiAutomator).click();
		apiDemo.dragAndDrop.click();

		WebElement source = apiDemo.viewList.get(0);
		WebElement destination = apiDemo.viewList.get(2);
		utils.dragAndDrop(source, destination);
	}
	
}
