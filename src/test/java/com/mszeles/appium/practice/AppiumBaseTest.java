package com.mszeles.appium.practice;

import static io.appium.java_client.touch.offset.ElementOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

public abstract class AppiumBaseTest {
	private AndroidDriver<AndroidElement> driver;
	//private ThreadLocal<AndroidDriver<AndroidElement>> driver;

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
		properties.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/appium.properties")));
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, (String)properties.get("device-name"));
		//This is needed in case the emulator is slow
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
		//https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
		File app = new File((String)properties.get("app-path"));
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		//For Android you need UI Automator 2
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		return new AndroidDriver<>(new URL("http://127.0.01:4723/wd/hub"), cap);
	}

	/* Use this code whenever you learn how to restart application after each test
	private ThreadLocal<AndroidDriver<AndroidElement>> driver;
	@BeforeClass
	public void setupp() throws MalformedURLException {
		driver = new ThreadLocal<AndroidDriver<AndroidElement>>() {
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
		getDriver().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}

	public AndroidDriver<AndroidElement> getDriver() {
		return driver.get();
	}
	 */

	public MobileElement scrollIntoView(String androidUISelector) {
		MobileElement element = getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
				+ androidUISelector + ")"));
		return element;
	}

	public void dragAndDrop(WebElement source, WebElement destination) {
		TouchAction<?> action = new TouchAction<>(getDriver());
		action.longPress(LongPressOptions.longPressOptions().withElement(element(source)))
		.moveTo(element(destination)).release().perform();
	}

}
