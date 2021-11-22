package com.mszeles.appium.practice;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public abstract class AppiumBaseTest {
	private AndroidDriver<AndroidElement> driver;

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	@BeforeMethod
	public void setup() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2_XL_API_30");
		//https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
		File app = new File("lib", "ApiDemos-debug.apk");
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		//For Android you need UI Automator 2
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		driver = new AndroidDriver<>(new URL("http://127.0.01:4723/wd/hub"), cap);
	}

	/* Use this code whenever you learn how to restart application after each test
	private ThreadLocal<AndroidDriver<AndroidElement>> driver;

	@BeforeClass
	public void setup() throws MalformedURLException {
		driver = new ThreadLocal<AndroidDriver<AndroidElement>>() {
			@Override
			protected AndroidDriver<AndroidElement> initialValue() {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2_XL_API_30");
				//https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
				File app = new File("lib", "ApiDemos-debug.apk");
				cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
				//For Android you need UI Automator 2
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
				try {
					return new AndroidDriver<>(new URL("http://127.0.01:4723/wd/hub"), cap);
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		//getDriver().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}

	public AndroidDriver<AndroidElement> getDriver() {
		return driver.get();
	}
	 */
}
