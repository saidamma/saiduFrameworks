package com.db_dataDrivenTest.db_data;

import org.openqa.selenium.By;
/**
 * 
 * This class contains all element locators
 * 
 * @author Saidamma
 * 
 */

public class ElementLocators extends BaseSetup {

	public static By emailTextBox = By.xpath("(//input[@id='emailId'])[2]");
	public static By passwordTextBox = By
			.xpath("(//input[@type='password'])[2]");
	public static By loginButton = By.xpath("(//button[@type='submit'])[2]");
	public static By textverify = By
			.xpath("html/body/div[2]/div[1]/div/div/div/div[1]/div/div[1]/b");
	
	public static By logoutLink =By.linkText("Log Out");

}
