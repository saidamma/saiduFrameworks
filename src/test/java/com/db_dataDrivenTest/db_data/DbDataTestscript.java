package com.db_dataDrivenTest.db_data;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * 
 * This class will get the data from DB and store the data into excel sheet..
 * after that again read the data from excel sheet and put results back into
 * excel sheet
 * 
 * @author saidamma
 * 
 */
public class DbDataTestscript extends BaseSetup {

	ExtentTest test;

	String path = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources"
			+ File.separator + "Screenshots" + File.separator
			+ ExtentReportFormat.getDate() + File.separator + "testlogin"
			+ File.separator + "screenthot" + System.currentTimeMillis()
			+ ".jpeg";

	/*
	 * @Parameters({ "browserType", "browserVersion", "site", "platform",
	 * "appURL" })
	 * 
	 * @BeforeMethod(alwaysRun = true) public void
	 * initializeTestBaseSetup(String browserType, String browserVersion, String
	 * site, String platform, String appURL) { try {
	 * ElementsMethods.clearCookies(driver); setDriver(browserType,
	 * browserVersion, site, platform, appURL);
	 * 
	 * } catch (Exception e) { System.out.println("Error....." +
	 * e.getStackTrace()); }
	 * 
	 * }
	 */

	@Parameters({ "site", "stack" })
	@BeforeMethod(alwaysRun = true)
	public void initializeTestBaseSetup(String site, String stack, Method m) {
		try {
			loadEnvironmentProperties(stack);
			ElementsMethods.clearCookies(driver);
			setDriver1(site, stack, m);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {

		if (!envSite.equalsIgnoreCase("local")) {
			RemoteWebDriver driver1 = (RemoteWebDriver) driver;
			String sauceSessionId = driver1.getSessionId().toString();

			// if test passed, add passed information to job, else add failed
			if (result.isSuccess())
				sauceClient.jobPassed(sauceSessionId);
			else
				sauceClient.jobFailed(sauceSessionId);
		}

		if (result.isSuccess()) {
			test.log(Status.PASS, result.getName());

		} else {
			test.log(Status.FAIL, result.getThrowable());
		}

		ExtentReportFormat.reporter.flush();
		driver.quit();

	}

	@BeforeSuite
	public void report() {
		// Create Extent Report Object
		ExtentReportFormat.generateReport();
	}

	@Test
	public void Asserttest2() {
		ElementsMethods.navigateUrl(driver, "login");
		test = ExtentReportFormat.reporter.createTest("Asserttest2");
		assertEquals("sai", "saidu");
	}

	@Test
	public void Asserttest3() {
		ElementsMethods.navigateUrl(driver, "login");
		test = ExtentReportFormat.reporter.createTest("Asserttest3");
		assertEquals("varshi", "varshi");
	}

	@Test
	public void testlogin() throws Exception {
		System.out.println("*********=" + path);
		ElementsMethods.navigateUrl(driver, "login");
		// Create extent test
		test = ExtentReportFormat.reporter.createTest("testlogin");

		List<String> l = new ArrayList<String>();
		Map<String, List<Object>> resultMap1 = new HashMap<String, List<Object>>();

		String sql = "SELECT A.* FROM MEMBER M,AUTHENTICATION A WHERE member_type = 'SEEKER' AND STATUS='A' AND  M.authentication_id = A.id ORDER BY 1 DESC LIMIT 3";
		String[] a = { "username", "id" };
		resultMap1 = Dbconnection.getDataFromDb(sql, a);
		System.out.println("username:::::" + resultMap1.get("username"));
		List<Object> result = resultMap1.get("username");

		for (int i = 0; i < result.size(); i++) {
			System.out.println("***************" + result.get(i));

		}
		// System.out.println("Id:::::"+resultMap1.get("id"));
		// Create Extent Report Object

		ReadWriteDataFromExcel.setExcelFile("a.xlsx", "USERNAME");
		ReadWriteDataFromExcel.setCellData(result, "a.xlsx");
		l = ReadWriteDataFromExcel.getCellData("USERNAME");
		for (int i = 0; i < result.size(); i++) {
			System.out.println("***************" + l.get(i));

			ElementsMethods.enterText(driver, ElementLocators.emailTextBox,
					l.get(i));
			ElementsMethods.enterText(driver, ElementLocators.passwordTextBox,
					"********");
			ElementsMethods.click(driver, ElementLocators.loginButton);

			ExtentReportFormat.takeScreenshot(driver, path);
			test.addScreenCaptureFromPath(path);

			int RowNum = i + 1;

			if (driver.getPageSource().contains("Hi")) {
				ReadWriteDataFromExcel.setExcelFile("a.xlsx", "USERNAME");
				ReadWriteDataFromExcel.setDataRowCell(RowNum, "a.xlsx", "PASS",
						1);
				System.out.println("login is success");
				ElementsMethods.navigateUrl(driver,"/visitor/logout.do");
			} else {
				ReadWriteDataFromExcel.setDataRowCell(RowNum, "a.xlsx", "FAIL",
						1);
				System.out.println("login is fail");
			}
			driver.navigate().refresh();
		}

	}
}