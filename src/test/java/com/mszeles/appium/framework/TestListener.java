package com.mszeles.appium.framework;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{

	@Override
	public void onTestFailure(ITestResult result) {
		AppiumBaseTest currentClass = (AppiumBaseTest) result.getInstance();
		try {
			currentClass.takeScrenshot(result.getName() + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
