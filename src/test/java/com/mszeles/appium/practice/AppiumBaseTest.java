package com.mszeles.appium.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public abstract class AppiumBaseTest {
	protected AndroidDriver<AndroidElement> driver;
	protected Properties properties;
	protected AppiumUtils utils;
	//private ThreadLocal<AndroidDriver<AndroidElement>> d;

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	@BeforeMethod
	public void setupAppiumBaseTest() throws FileNotFoundException, IOException {
		driver = configure(getAppPath());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		utils = new AppiumUtils(driver);
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	protected abstract String getAppPath();

	private AndroidDriver<AndroidElement> configure(String appPath) throws FileNotFoundException, IOException {
		properties = new Properties();
		String projectHome = System.getProperty("user.dir");
		properties.load(new FileInputStream(new File(projectHome + "/src/test/resources/appium.properties")));
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("device-name"));
		//This is needed in case the emulator is slow
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
		//https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
		if (appPath != null && !appPath.isEmpty()) {
			File app = new File((String)appPath);
			cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		}
		//For Android you need UI Automator 2
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		//https://stackoverflow.com/questions/52023111/no-chromedriver-found-that-can-automate-chrome-53-0-2785
		String chromeDriver = properties.getProperty("chrome-driver");
		if (chromeDriver != null && !chromeDriver.isEmpty()) {
			cap.setCapability("chromedriverExecutable", projectHome + "/" + chromeDriver);
		}
		String browserName = properties.getProperty("browser-name");
		if (browserName != null && !browserName.isEmpty()) {
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
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

}
