package com.mszeles.appium.examples;

import org.testng.annotations.DataProvider;

public class TestData {
	@DataProvider(name = "ApiDemoInputData")
	public Object[][] getDataForEditField() {
		return new Object[][] {
			{"hello"}, {"@&$%"}
		};
	}
}
