package com.db_dataDrivenTest.db_data;


	import java.io.File;
	import java.io.IOException;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.testng.annotations.AfterSuite;

	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


	/**
	 * 
	 * This class will generate the Extent Reports
	 * 
	 * @author saidamma
	 *
	 */
	public class ExtentReportFormat {
		
		/** Extent Reports variable to store report {@link ExtentReports}*/
		public static ExtentReports reporter;
		
		/** Extent HTML reporter to store report {@link ExtentHtmlReporter}*/
		public static ExtentHtmlReporter htmlReporter;

		public static void generateReport() {
			
			// Create Object for Extent
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "test" + File.separator + "resources" + File.separator + "Reports" + File.separator
					+ "AutomationReport"  + ".html");
			
			// Condition to append the tests to existing html reported
			htmlReporter.setAppendExisting(false);
			
			// Report configurations
			htmlReporter.config()
					.setJS("$(document).ready(function() { var link = document.createElement('link');link.type = 'image/x-icon';link.rel = 'shortcut icon';link.href = 'https://s.cdn-care.com/img/favicon.ico?v=160921';document.getElementsByTagName('head')[0].appendChild(link);  });"
							+ "$(document).ready(function() { var a = document.getElementsByClassName('brand-logo')[0]; a.innerHTML='saidamma'; a.href = 'http://www.gmail.com';});");
			htmlReporter.config().setDocumentTitle("Saidamma data-drivenDb Automation Report");
			htmlReporter.config().setReportName("Saidu data-drivenDb Automation Report");
			
			// Object creation for extent reports class
			reporter	= new ExtentReports();
			
			// Attaching extent report to HTML reporter
			reporter.attachReporter(htmlReporter);
		}
		
		/**
		 * Get today's date
		 * @return	Return today date
		 */
		public static String getDate(){
			
			// Get current system date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate localDate = LocalDate.now();
			
			// Store today date
			String today = dtf.format(localDate);
			
			return today;
		}
		
		@AfterSuite
		public void closeReport(){
			reporter.flush();
		}
		
		/**
		 * 
		 * Take screenshot of current screen
		 * 
		 * @param driver
		 * @param path
		 */
		public static void takeScreenshot(WebDriver driver, String path){
			
			File srcFile	= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			try {
				FileUtils.copyFile(srcFile, new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}


