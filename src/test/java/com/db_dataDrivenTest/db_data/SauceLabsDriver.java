package com.db_dataDrivenTest.db_data;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceLabsDriver {

	// eb022039-6299-4edf-93f4-62362c0ccd93

	public static final String USERNAME = "saidamma";
	public static final String ACCESS_KEY = "eb022039-6299-4edf-93f4-62362c0ccd93";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY
			+ "@ondemand.saucelabs.com:443/wd/hub";

	public static void main(String[] args) throws MalformedURLException {
		
		DesiredCapabilities desiredCapabilities	=  DesiredCapabilities.chrome();
	    desiredCapabilities.setCapability("platform", "Windows 8");
	    desiredCapabilities.setCapability("version", "50");


		WebDriver driver = new RemoteWebDriver(new java.net.URL(URL), desiredCapabilities);
		
		driver.get("http://www.google.com");
		
		driver.findElement(By.name("q")).sendKeys("Sauce Labs Test");
		
		driver.quit();

	}

}
