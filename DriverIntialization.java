
package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import Constants.Constants;
import io.cucumber.java.Scenario;
//import cucumber.api.Scenario;
//import cucumber.api.Scenario;

public class DriverIntialization {
	public static WebDriver driver;
	public static Properties ppty;
	public static Properties loc;
	public static Logger logger;
	//logger = LogManager.getLogger();
	public static Scenario messageHandler;
	public static ThreadLocal<WebDriver> TLdriver = new ThreadLocal<WebDriver>();
	
	public static void intializeDriver(String drivernm) throws Exception {
		setWebDriver(driver, drivernm);
		System.out.println("initialise function");
	}

	public static WebDriver setWebDriver(WebDriver driver, String drivernm) throws Exception {
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		CommonFunctions.loadClassLoader();
	
		logger = LogManager.getLogger(DriverIntialization.class.getName());
		//logger = Logger.getLogger(DriverIntialization.class.getName());
		ppty = CommonFunctions.getObjDetails();
		loc=CommonFunctions.getlocDetails();
		drivernm = drivernm.toUpperCase();
		System.out.println("Browser: " + drivernm + " selected for execution");

		switch (drivernm) {

		case "CHROME":
//			System.out.println("chrome case...");
//			System.out.println("chrome path "+WebDriverManager.chromedriver().getDownloadedDriverPath());
//			WebDriverManager.chromedriver().setup();

			ChromeOptions handlingSSL = new ChromeOptions();
			//Using the accept insecure cert method with true as parameter to accept the untrusted certificate
			handlingSSL.setAcceptInsecureCerts(true);
			handlingSSL.addArguments("--remote-allow-origins=*");
			//handlingSSL.addArguments("--window-size=1920,1080");
			//handlingSSL.addArguments("--headless");
			TLdriver.set(new ChromeDriver(handlingSSL)); 
			break;

		case "IE":
			WebDriverManager.iedriver().setup();
			TLdriver.set(new InternetExplorerDriver());
			// driver.get(ppty.getProperty("QA_url"));
			break;

		case "FIREFOX":
			WebDriverManager.firefoxdriver().setup();			
		//System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Mozilla Firefox\\firefox.exe" );
//			//System.setProperty("webdriver.gecko.driver","C:\\Users\\NPatil16\\Downloads\\geckodriver-v0.33.0-win64 (1)\\geckodriver.exe" );
		//FirefoxDriver driver1 = new FirefoxDriver();
		//FirefoxOptions handlingSSL1 = new FirefoxOptions();
		//Using the accept insecure cert method with true as parameter to accept the untrusted certificate
		//System.setProperty("webdriver.gecko.driver","C:\\Users\\NPatil16\\Downloads\\geckodriver-v0.33.0-win64(1)\\geckodriver.exe");
		//handlingSSL1.setAcceptInsecureCerts(true);
		TLdriver.set(new FirefoxDriver());			
			break;

		default:
			throw new Exception("Driver " + drivernm + " Not Defined");
		}
		//TLdriver.set(driver);
		maximiseWindow();
		setUpImplicitWait();
		return getDriver();
	}

	public static WebDriver getDriver() {
		return TLdriver.get();
	}

	@SuppressWarnings("deprecation")
	public static void setUpImplicitWait() {

		getDriver().manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT, TimeUnit.SECONDS);
	}

	public static void maximiseWindow() {

		getDriver().manage().window().maximize();
	}
}

/*
 ** ==========~~~~~~~~~~==========~~~~~~~~~~==========~~~~~~~~~~==========
 ** Copyright (C) 2008-2019 Schlumberger,- All Rights Reserved Unauthorized
 * copying of this file, via any medium is strictly prohibited Proprietary and
 * confidential
 ** ==========~~~~~~~~~~==========~~~~~~~~~~==========~~~~~~~~~~==========
 */
