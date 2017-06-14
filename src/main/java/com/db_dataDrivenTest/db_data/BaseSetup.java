package com.db_dataDrivenTest.db_data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;

/**
 * 
 * This class contains web browser creation methods
 * 
 * @author Saidamma
 * 
 */
public class BaseSetup {

	public static WebDriver driver;
	static String driverPath = System.getProperty("user.dir");
	String envStack = null;
	String sauceBrowser = null;
	String sauceBrowserVersion = null;
	String saucePlatform = null;
	String envSite = null;
	/** Saucelabs Authenticator. Uses Saucelabs Username and API Key. */
	public static SauceOnDemandAuthentication sauceAuth;

	/** Saucelabs REST Client. Uses Saucelabs Username and API Key. */
	public static SauceREST sauceClient;

	/** Saucelabs Session ID of the job. Unique for each job. */
	public static String sessionId;

	public WebDriver getDriver() {
		return driver;
	}

	/*
	 * public void setDriver(String browserType, String browserVersion, String
	 * site, String platform, String appURL) throws MalformedURLException {
	 * 
	 * if (site.equalsIgnoreCase("local")) {
	 * 
	 * switch (browserType) { case "chrome": driver = initChromeDriver(); break;
	 * case "firefox":
	 * 
	 * driver = initFirefoxDriver(); break; default: System.out
	 * .println("browser : " + browserType +
	 * " is invalid, Launching Firefox as browser of choice.."); driver =
	 * initFirefoxDriver(); } } else { driver = saucelabDriver(browserType,
	 * browserVersion, platform, appURL); }
	 * 
	 * }
	 */

	public void setDriver1(String site, String stack, Method m)
			throws MalformedURLException {

		// Set the Environment from Jenkins, if null, get it from Variables file
		if (System.getenv("Stack") == null) {
			this.envStack = site;
		}

		else {
			this.envStack = System.getenv("Stack");
		}
		envSite = site;
		if (envSite.equalsIgnoreCase("local")) {
			String browser = EnvVaribles.environmentProperties
					.getProperty("browserName");
			switch (browser) {
			case "chrome":
				driver = initChromeDriver();
				break;
			case "firefox":

				driver = initFirefoxDriver();
				break;
			default:
				System.out
						.println("browser : "
								+ browser
								+ " is invalid, Launching Firefox as browser of choice..");
				driver = initFirefoxDriver();
			}
		} else {
			if (System.getenv("SELENIUM_BROWSER") == null) {
				this.sauceBrowser = EnvVaribles.environmentProperties
						.getProperty("sauceBrowser");
			}

			else {
				this.sauceBrowser = System.getenv("SELENIUM_BROWSER");
			}
			if (System.getenv("SELENIUM_VERSION") == null) {
				this.sauceBrowserVersion = EnvVaribles.environmentProperties
						.getProperty("sauceBrowserVersion");
			}

			else {
				this.sauceBrowserVersion = System.getenv("SELENIUM_VERSION");
			}
			if (System.getenv("SELENIUM_PLATFORM") == null) {
				this.saucePlatform = EnvVaribles.environmentProperties
						.getProperty("saucePlatform");
			}

			else {
				this.saucePlatform = System.getenv("SELENIUM_PLATFORM");
			}

		saucelabDriver(sauceBrowser, sauceBrowserVersion,
					saucePlatform,m);
		}

	}

	public static WebDriver initChromeDriver() {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", driverPath
				+ "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver initFirefoxDriver() {

		FirefoxProfile profile = new FirefoxProfile();
		profile.setAssumeUntrustedCertificateIssuer(false);
		System.out.println("Launching Firefox browser..");

		WebDriver driver = new FirefoxDriver(profile);
		driver.manage().window().maximize();
		return driver;
	}

	public static void saucelabDriver(String browserType, String Version,
			String platform, Method m) throws MalformedURLException {
		String USERNAME = null;
		String ACCESS_KEY = null;
		if (System.getenv("SAUCE_USER_NAME") == null) {
			USERNAME = EnvVaribles.environmentProperties
					.getProperty("sauceUsername");
		}

		else {
			USERNAME = System.getenv("SAUCE_USER_NAME");
		}
		if (System.getenv("SAUCE_API_KEY") == null) {
			ACCESS_KEY = EnvVaribles.environmentProperties
					.getProperty("sauceAccessKey");
		}

		else {
			ACCESS_KEY = System.getenv("SAUCE_API_KEY");
		}
		// Create Saucelabs authenticator and REST client
		sauceAuth = new SauceOnDemandAuthentication(USERNAME, ACCESS_KEY);
		sauceClient = new SauceREST(USERNAME, ACCESS_KEY);

		String URL = "https://" + USERNAME + ":" + ACCESS_KEY
				+ "@ondemand.saucelabs.com:443/wd/hub";

		DesiredCapabilities desiredCapabilities;
		if (browserType.equalsIgnoreCase("firefox")) {
			desiredCapabilities = DesiredCapabilities.firefox();
		} else {
			desiredCapabilities = DesiredCapabilities.chrome();
		}
		desiredCapabilities.setCapability("platform", platform);
		desiredCapabilities.setCapability("version", Version);

		driver = new RemoteWebDriver(new java.net.URL(URL),
				desiredCapabilities);

		// Get the session ID from Saucelabs, and set the local value

		sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

		// Print the Session ID
		String message = String.format(
				"SauceOnDemandSessionID=%1$s job-name=%2$s",
				((RemoteWebDriver) driver).getSessionId().toString(),
				m.getName());
		System.out.println(message);

	}

	/*
	 * @BeforeSuite(alwaysRun = true) public void loadProperties() throws
	 * IOException {
	 * 
	 * // Load Environment Properties loadEnvironmentProperties();
	 * 
	 * }
	 */

	public Properties loadEnvironmentProperties(String stack)
			throws IOException {

		// Create properties file object
		Properties properties = new Properties();

		// Initialize input stream
		InputStream inputStream;

		// Generate File name
		if (System.getenv("Stack") == null) {
			this.envStack = stack;
		}

		else {
			this.envStack = System.getenv("Stack");
		}
		String fileName = envStack.toLowerCase() + ".properties";

		// Get file path
		inputStream = new FileInputStream(System.getProperty("user.dir")
				+ "/src/test/resources/EnvProperties/" + fileName);

		// Load property file
		properties.load(inputStream);
		EnvVaribles.environmentProperties = properties;
		return properties;

	}

//	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, Method m) throws IOException {
		if (!envSite.equalsIgnoreCase("local")) {
			RemoteWebDriver driver1 = (RemoteWebDriver) driver;
			String sauceSessionId = driver1.getSessionId().toString();

			// if test passed, add passed information to job, else add failed
			if (result.isSuccess())
				sauceClient.jobPassed(sauceSessionId);
			else
				sauceClient.jobFailed(sauceSessionId);
		}
	}
}