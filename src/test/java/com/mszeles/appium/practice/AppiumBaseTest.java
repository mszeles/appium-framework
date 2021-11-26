package com.mszeles.appium.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public abstract class AppiumBaseTest {
	protected AndroidDriver<AndroidElement> driver;
	protected Properties properties;
	protected AppiumUtils utils;
	// private ThreadLocal<AndroidDriver<AndroidElement>> d;
	private AppiumDriverLocalService appiumServer;
	private Process emulatorProcess;

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	@BeforeClass
	public void globalSetup() throws FileNotFoundException, IOException, InterruptedException {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(Paths
					.get(System.getProperty("user.dir"), "src", "test", "resources", "appium.properties").toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isEmulatorEnabled()) {
			startEmulator();
		}
		System.out.println("Starting Appium");
		appiumServer = AppiumDriverLocalService.buildDefaultService();
		appiumServer.start();
	}

	private boolean isEmulatorEnabled() {
		return "true".equalsIgnoreCase(properties.getProperty("emulator-enabled"));
	}

	private void startEmulator() throws IOException, InterruptedException {
		String[] params = new String[3];
		params[0] = Paths.get(System.getenv("ANDROID_HOME"), "emulator", "emulator").toString();
		params[1] = "-avd";
		params[2] = properties.getProperty("device-name");
		System.out.println("Starting emulator");
		emulatorProcess = Runtime.getRuntime().exec(params);
		if (!emulatorProcess.isAlive()) {
			System.out.println("Error during starting emulator:");
			printProcessLog(emulatorProcess.getErrorStream());
		} else {
			// Wait for start
			while (true) {
				Thread.sleep(1000);
				Process process = Runtime.getRuntime().exec("adb shell getprop init.svc.bootanim");
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String s = null;
				if ((s = reader.readLine()) != null) {
					if ("stopped".equals(s)) {
						break;
					}
				}
				reader.close();
			}
		}
	}

	private void printProcessLog(InputStream stream) throws IOException {
		BufferedReader stdError = new BufferedReader(new InputStreamReader(stream));
		// Read the output from the command
		String s = null;
		// Read any errors from the attempted command
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}
	}

	@AfterClass
	public void globalTeardown() throws IOException {
		appiumServer.stop();
		if (isEmulatorEnabled()) {
			System.out.println("Stopping emulator");
			//Did not work for me
			//TODO find a more gentle way to shutdown the emulator
//			Process process = Runtime.getRuntime()
//					.exec("adb -s " + properties.getProperty("device-name") + " emu kill");
			emulatorProcess.descendants().forEach(p -> p.destroyForcibly());
			emulatorProcess.destroyForcibly();
		}
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
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("device-name"));
		// This is needed in case the emulator is slow
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
		// https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk
		if (appPath != null && !appPath.isEmpty()) {
			File app = new File((String) appPath);
			cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		}
		// For Android you need UI Automator 2
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		// https://stackoverflow.com/questions/52023111/no-chromedriver-found-that-can-automate-chrome-53-0-2785
		String chromeDriver = properties.getProperty("chrome-driver");
		if (chromeDriver != null && !chromeDriver.isEmpty()) {
			cap.setCapability("chromedriverExecutable",
					Paths.get(System.getProperty("user.dir"), chromeDriver).toString());
		}
		String browserName = properties.getProperty("browser-name");
		if (browserName != null && !browserName.isEmpty()) {
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
		}
		return new AndroidDriver<>(new URL("http://127.0.01:4723/wd/hub"), cap);
	}

	/*
	 * Use this code whenever you learn how to restart application after each test
	 * private ThreadLocal<AndroidDriver<AndroidElement>> driver;
	 * 
	 * @BeforeClass public void setup() throws MalformedURLException { d = new
	 * ThreadLocal<AndroidDriver<AndroidElement>>() {
	 * 
	 * @Override protected AndroidDriver<AndroidElement> initialValue() { try {
	 * return configure(); } catch (MalformedURLException e) { e.printStackTrace();
	 * return null; } } }; driver = d.get()
	 * driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS); }
	 */

}
