package com.mszeles.appium.practice;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mszeles.appium.practice.page_objects.angular_web_app.AngularAppHomepage;

public class AngularAppDemoTest extends AppiumBaseTest {
	private AngularAppHomepage homepage;
	
	@Override
	protected String getAppPath() {
		return null;
	}
	
	@BeforeMethod
	public void setupAngularAppDemoTest() {
		homepage = new AngularAppHomepage(driver);
		String url = properties.getProperty("url");
		driver.get(url);
	}
	
	@Test
	public void selectDevops() {
		homepage.menu.click();
		homepage.productsMenuItem.click();
		utils.scrollInBrowser(0, 1000);
		homepage.devopsItem.click();
	}


}
