package com.mszeles.appium.framework.page_objects;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.mszeles.appium.framework.AppiumUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class AbstractPage {
	
	private static final int IMPLICIT_TIMEOUT = 10;
	protected final AndroidDriver<AndroidElement> driver;
	protected final AppiumUtils utils;

	public AbstractPage(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		this.utils = new AppiumUtils(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(IMPLICIT_TIMEOUT)), this);
	}

}
