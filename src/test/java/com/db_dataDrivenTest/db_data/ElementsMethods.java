package com.db_dataDrivenTest.db_data;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * 
 * This class contains all the methods
 * 
 * @author Saidamma
 * 
 */
public class ElementsMethods extends BaseSetup {
	/**
	 * Method to CLICK on an Object
	 * 
	 * @param driver
	 * @param locator
	 */
	public static String click(WebDriver driver, By locatorValue) {

		String result;
		try {
			WebElement element = driver.findElement(locatorValue);

			element.click();
			result = "Successfully element find and clicked";

		} catch (NoSuchElementException e) {
			result = "Element not available - " + e;
		} catch (Exception e) {

			result = " Unable to click - " + e;
		}

		return result;
	}

	/**
	 * Method to launch AUT Site
	 * 
	 * @param driver
	 * @param URL
	 */
	public static String openUrl(WebDriver driver, String URL) {
		driver.get(URL);
		return "Successfully Launched URL " + URL;
	}
	
	public static void navigateUrl(WebDriver driver,String url)
	{
		driver.get(EnvVaribles.environmentProperties.getProperty("base.url")+"/"+url);
		
	}

	/**
	 * Method to enter some data inside textbox. text area
	 * 
	 * @param driver
	 * @param locator
	 * @param inputData
	 * @return
	 */
	public static String enterText(WebDriver driver, By locator,
			String inputData) {

		try {
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(inputData);
			return "Successfullly entered " + inputData + " text";
		} catch (NoSuchElementException e) {
			return "unable to enter text and failed" + e;
		} catch (Exception e) {

			return "unable to enter text and failed" + e;
		}
	}

	/**
	 * Halt the application for few seconds
	 * 
	 * @param millis
	 * @return
	 */
	public static String waitFor(String millis) {

		if (millis.contains(".")) {
			millis = millis.substring(0, millis.indexOf("."));
		}

		try {
			Thread.sleep(Integer.parseInt(millis));

			return "Successfullly waited for " + millis + " seconds";
		} catch (NumberFormatException e) {
			return "Failed with NumberFormatException and the given number is "
					+ millis + e;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return "Failed with InterruptedException and the given number is "
					+ millis + e;
		}
	}

	/**
	 * Clear browser cookies
	 * 
	 * @param driver
	 * @return
	 */
	public static String clearCookies(WebDriver driver) {

		try {
			// Clear cookies
			driver.manage().deleteAllCookies();

			// Success message
			return "Cleared browser cookies";

		} catch (Exception e) {

			return "Unable to clear browser cookies" + e;
		}
	}

	/**
	 * 
	 * Select require value from Select Dropdown {@link Select}
	 * 
	 * @param driver
	 * @param locator
	 * @param inputData
	 * @return
	 */
	public static String selectDropdown(WebDriver driver, By locator,
			String inputData) {

		// Create web element
		WebElement w = (WebElement) locateElement(driver, locator);

		// Create object for Select
		Select select = new Select(w);

		try {

			select.selectByVisibleText(inputData);

			return "Selected" + inputData + " from select dropdown";

		} catch (Exception e) {

			return " No matching option found " + e;
		}
	}

	/**
	 * 
	 * Locate element using {@link ByXPath}
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static Object locateElement(WebDriver driver, By locator) {

		try {

			WebElement w = driver.findElement(locator);

			return w;

		} catch (Exception e) {

			return "Failed with No such element found with the given locator "
					+ e;
		}

	}

	/**
	 * 
	 * Verify expected message with actual message
	 * 
	 * @param driver
	 * @param locator
	 * @param expectedText
	 * @return
	 */
	public static String verifyText(WebDriver driver, By locator,
			String expectedText) {

		// Create Webelement
		WebElement w = (WebElement) locateElement(driver, locator);

		// Expected text
		expectedText = expectedText.toLowerCase();

		// Actual text
		String actualText = w.getText().trim().toLowerCase();

		try {

			// Verify text
			Assert.assertTrue(actualText.contains(expectedText));

			return "Successully verified actual text with expected text";

		} catch (AssertionError e) {

			return "Failed with Exception caught while verifing the messages "
					+ e;
		}
	}

	/**
	 * Select values from list of values based on Text
	 * 
	 * @param driver
	 * @param locator
	 * @param inputData
	 * @return
	 */
	public static String selectFromListUsingText(WebDriver driver, By locator,
			String inputData) {

		// Get list of elements
		List<WebElement> l1 = (List<WebElement>) listOfVisibleElements(driver,
				locator);

		// Flag
		boolean isClicked = false;

		if (l1.isEmpty()) {

			return "size of the list with the given element is 0"
					+ new NoSuchElementException("Element not found");
		} else if (inputData == null) {

			try {
				l1.get(0).click();

				return "Selected first element from list";
			} catch (StaleElementReferenceException e) {

				return "If the element no longer exists as initially defined"
						+ e;
			}

		} else {

			for (int i = 0; i < l1.size(); i++) {

				if (l1.get(i).getText().trim().equalsIgnoreCase(inputData)) {

					try {
						l1.get(i).click();
						isClicked = true;
					} catch (StaleElementReferenceException e) {

						return "If the element no longer exists as initially defined"
								+ e;
					}
					break;
				}
			}

			if (isClicked) {
				return "Successfuly clicked on given element";
			} else {
				return "Unable to identify the drop down value given value is "
						+ inputData;
			}
		}
	}

	/**
	 * Get the list of visiable elements
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static List<WebElement> listOfVisibleElements(WebDriver driver,
			By locator) {

		// Get list of elements
		List<WebElement> allElements = driver.findElements(locator);

		// Store visible elements list
		List<WebElement> visiableElements = new ArrayList<>();

		for (int i = 0; i < allElements.size(); i++) {

			if (allElements.get(i).isDisplayed()) {
				visiableElements.add(allElements.get(i));
			}
		}

		// Return visible elements list
		return visiableElements;
	}

	/**
	 * Select value from list of values based on index
	 * 
	 * @param driver
	 * @param locator
	 * @param index
	 * @return
	 */
	public static String selectFromListUsingIndex(WebDriver driver, By locator,
			String index) {

		// Get list of elements
		List<WebElement> l1 = (List<WebElement>) listOfVisibleElements(driver,
				locator);

		if (l1.isEmpty()) {

			return "size of the list with the given element is 0"
					+ new NoSuchElementException("Element not found");
		} else {

			try {

				int i = Integer.parseInt(index.trim());

				if (index != null) {
					i = Integer.parseInt(index.trim());
				}

				l1.get(i).click();

				return "Successfuly clicked on given element";

			} catch (NumberFormatException n) {

				return "Given wrong type of the index value " + index + " " + n;
			} catch (IndexOutOfBoundsException i) {

				return "Index is out of range, Size of the array is "
						+ l1.size() + " " + i;
			} catch (StaleElementReferenceException s) {

				return "If the element no longer exists as initially defined"
						+ s;
			}
		}
	}

	/**
	 * Select radio button
	 * 
	 * @param driver
	 * @param locator
	 * @param index
	 * @return
	 */
	public static String selectRadioButton(WebDriver driver, By locator,
			String index) {

		// Select radio button and return status
		String status = selectRadioButton(driver, locator, index);

		// Return status
		return status;
	}

	/**
	 * Select check box
	 * 
	 * @param driver
	 * @param locator
	 * @param index
	 * @return
	 */
	public static String selecCheckbox(WebDriver driver, By locator,
			String index) {

		// Select radio button and return status
		String status = selectRadioButton(driver, locator, index);

		// Return status
		return status;
	}

	/**
	 * Wait for visibility of element {@link WebDriverWait}
	 * 
	 * @param driver
	 * @param locator
	 */
	public static String explicitVisibleWait(WebDriver driver, String locator) {

		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(locator)));

			return "Successfuly waited for " + locator;

		} catch (Exception e) {

			return "Failed with Unable to Wait for element " + e;
		}
	}

	public static String getText(WebDriver driver, By locator) {
		try {

			WebElement w = driver.findElement(locator);
			String s = w.getText();
			return s;

		} catch (Exception e) {

			return "Failed with No such element found with the given locator "
					+ e;
		}
	}
}
