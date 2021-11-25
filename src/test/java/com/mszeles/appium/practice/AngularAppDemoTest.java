package com.mszeles.appium.practice;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AngularAppDemoTest extends AppiumBaseTest {
	
	@BeforeMethod
	public void setupAngularAppDemoTest() {
		String url = properties.getProperty("url");
		driver.get(url);
	}
	
	@Test
	public void test() {
		driver.findElement(By.cssSelector(".navbar-toggler")).click();
		driver.findElement(By.cssSelector("a[href*='products'")).click();
		//Scrolling in browser
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 1000)", "");
		
		driver.findElement(By.xpath("//li[@class='list-group-item']//a[text()='Devops']")).click();
	}

}
