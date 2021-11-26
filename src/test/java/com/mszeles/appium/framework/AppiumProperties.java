package com.mszeles.appium.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class AppiumProperties {
	private static final String URL = "url";
	private static final String BROWSER_NAME = "browser-name";
	private static final String CHROME_DRIVER = "chrome-driver";
	private static final String HEADLESS = "headless";
	private static final String DEVICE_NAME = "device-name";
	private static final String EMULATOR_ENABLED = "emulator-enabled";
	private Properties properties;
	
	
	public AppiumProperties() {
		load();
	}
	
	private void load() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(Paths
					.get(System.getProperty("user.dir"), "src", "test", "resources", "appium.properties").toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEmulatorEnabled() {
		return "true".equalsIgnoreCase(properties.getProperty(EMULATOR_ENABLED));
	}
	
	public String getDeviceName() {
		return properties.getProperty(DEVICE_NAME);
	}
	
	public boolean isHeadless() {
		return "true".equalsIgnoreCase(properties.getProperty(HEADLESS));
	}
	
	public String getChromeDriver() {
		return properties.getProperty(CHROME_DRIVER);
	}
	
	public String getBrowserName() {
		return properties.getProperty(BROWSER_NAME);
	}
	
	public String getUrl() {
		return properties.getProperty(URL);
	}
}
