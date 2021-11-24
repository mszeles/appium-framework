package com.mszeles.appium.practice;

import static io.appium.java_client.touch.offset.ElementOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;

public abstract class AppiumBaseTest {
	protected AndroidDriver<AndroidElement> driver;
	//private ThreadLocal<AndroidDriver<AndroidElement>> d;

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	@BeforeMethod
	public void setup() throws FileNotFoundException, IOException {
		driver = configure();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	private AndroidDriver<AndroidElement> configure() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		String projectHome = System.getProperty("user.dir");
		properties.load(new FileInputStream(new File(projectHome + "/src/test/resources/appium.properties")));
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, (String)properties.get("device-name"));
		//This is needed in case the emulator is slow
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
		//https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
		File app = new File((String)properties.get("app-path"));
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		//For Android you need UI Automator 2
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		//https://stackoverflow.com/questions/52023111/no-chromedriver-found-that-can-automate-chrome-53-0-2785
		String chromeDriver = properties.getProperty("chrome-driver");
		if (chromeDriver != null && !chromeDriver.isEmpty()) {
			cap.setCapability("chromedriverExecutable", projectHome + "/" + chromeDriver);
		}
		return new AndroidDriver<>(new URL("http://127.0.01:4723/wd/hub"), cap);
	}

	/* Use this code whenever you learn how to restart application after each test
	private ThreadLocal<AndroidDriver<AndroidElement>> driver;
	@BeforeClass
	public void setup() throws MalformedURLException {
		d = new ThreadLocal<AndroidDriver<AndroidElement>>() {
			@Override
			protected AndroidDriver<AndroidElement> initialValue() {
				try {
					return configure();
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		driver = d.get()
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	 */

	public MobileElement scrollIntoView(String androidUISelector) {
		MobileElement element = getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
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
		return getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"" + scrollableParentResourceId
				+ "\")).scrollIntoView(" + itemSelector + ".instance(0))"));
	}

	public void dragAndDrop(WebElement source, WebElement destination) {
		TouchAction<?> action = new TouchAction<>(getDriver());
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

}
