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
		return "true".equalsIgnoreCase(getValue(EMULATOR_ENABLED));
	}
	
	public String getDeviceName() {
		return getValue(DEVICE_NAME);
	}
	
	public boolean isHeadless() {
		return "true".equalsIgnoreCase(getValue(HEADLESS));
	}
	
	public String getChromeDriver() {
		return getValue(CHROME_DRIVER);
	}
	
	public String getBrowserName() {
		return getValue(BROWSER_NAME);
	}
	
	public String getUrl() {
		return getValue(URL);
	}
	
	private String getValue(String property) {
		String value = System.getProperty(property);
		String v = (value == null || value.isEmpty()) ? properties.getProperty(property) : value;
		System.out.println(property + ": " + v);
		return v;
	}
}
