package com.mszeles.appium.practice.page_objects;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ApiDemo {
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Preference']")
	public WebElement preferences;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"3. Preference dependencies\"]")
	public WebElement preferenceDependencies;
	
	@AndroidFindBy(id = "android:id/checkbox")
	public WebElement wifiCheckBox;
	
	@AndroidFindBy(xpath = "(//android.widget.RelativeLayout)[2]")
	public WebElement secondOption;
	
	@AndroidFindBy(className = "android.widget.EditText")
	public WebElement editBox;

	@AndroidFindBy(className = "android.widget.Button")
	public List<WebElement> buttons;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().clickable(true)")
	public List<WebElement> options;
	
	public String inlineUiAutomator = "text(\"2. Inline\")";
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Expandable Lists']")
	public WebElement expandableLists;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='1. Custom Adapter']")
	public WebElement customAdapter;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='People Names']")
	public WebElement peopleNames;
	
	@AndroidFindBy(id = "android:id/title")
	public WebElement title;
	
	public String viewsUiAutomator = "text(\"Views\")";
	
	public String radioGroupUiAutomator = "text(\"Radio Group\")";
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Date Widgets']")
	public WebElement dateWidgets;
	
	@AndroidFindBy(uiAutomator = "text(\"2. Inline\")")
	public WebElement inline;
	
	@AndroidFindBy(xpath = "//*[@content-desc='9']")
	public WebElement nineOClock;
	
	@AndroidFindBy(xpath = "//*[@content-desc='15']")
	public WebElement min15;
	
	@AndroidFindBy(xpath = "//*[@content-desc='45']")
	public WebElement min45;
	
	@AndroidFindBy(uiAutomator = "text(\"Drag and Drop\")")
	public WebElement dragAndDrop;
	
	@AndroidFindBy(className = "android.view.View")
	public List<WebElement> viewList;
	
	public ApiDemo(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

}
