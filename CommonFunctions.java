
package Utilities;

import static io.restassured.RestAssured.given;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Constants.Constants;
import io.cucumber.java.Scenario;
import io.restassured.http.ContentType;

public class CommonFunctions extends DriverIntialization {
	
	public static WebDriverWait drvWait;
	public static Actions actions;
	public static JavascriptExecutor jscript;
	public static ClassLoader basedir;
	public static int TimeoutValue = Constants.EXPLICITWAIT;
	public static Properties ppty;
	public static Properties loc;
	public static Properties dataSourcePpty;
	public static int count = 0;
	public static String configPath = Constants.CONFIGFILEPATH;

	public static Properties getObjDetails() throws IOException {
		ppty = new Properties();
		ppty.load(loadFileAsStream("/config.properties"));
		return ppty;
	}
	
	public static Properties getlocDetails() throws IOException {
		loc = new Properties();
		loc.load(loadFileAsStream("/locators.properties"));
		return loc;
	}

	
   public static void deleteText(WebElement el) {
		
		el.sendKeys(Keys.CONTROL + "a");
		el.sendKeys(Keys.DELETE);
	}
	
	public static void pageup() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_HOME);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_HOME);
	}
	
	public static String decryptData(String decrptData)
	{
	byte[] decodeBytes = Base64.getDecoder().decode(decrptData.getBytes());
	return(new String(decodeBytes));
	}
	
	//wait 
	 public static void waitVisibilityofElement(By element) {
		 drvWait = new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		 drvWait.until(ExpectedConditions.presenceOfElementLocated(element));
		 
	 }
	 
	 public static void waitVisibilityofElement(WebElement element) {
		 drvWait = new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		 drvWait.until(ExpectedConditions.visibilityOf(element));
		 
	 }
	
	 public static void elementToBeSelected(WebElement element) {
		 drvWait = new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		 drvWait.until(ExpectedConditions.elementToBeSelected(element));
		 
	 }
	 
	 public static void visibilityOfAllElements(List<WebElement> selectionBox) {
		 drvWait = new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		 drvWait.until(ExpectedConditions.visibilityOfAllElements(selectionBox));
		 
	 }
	 public static void clickRadioBTN(WebElement btn) {
		 
		 btn.click();
	 }
	 
	public static void implicitlyWait(int wait)
	{
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
	}
	 
	public static void loadBaseURL() throws Exception {
		getDriver().manage().deleteAllCookies();
		//getDriver().get("chrome://settings/clearBrowserData");
		//getDriver().findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);	
		getDriver().navigate().to(ppty.getProperty("MyPCP_URL")); 
		//getDriver().manage().window().setSize(new Dimension(1920, 1080));
		Dimension dimension = getDriver().manage().window().getSize();
		System.out.println("dimension is " + dimension);
		implicitlyWait(120);
		waitForPageLoad();
		Thread.sleep(3000);
		embedScreenshot();
//		String env = ppty.getProperty("env");
//		switch (env.toUpperCase()) {
//		case "QA":
//			Login();
//			break;
//
//		case "DEV":
//			getDriver().get(ppty.getProperty("DEV_url"));
//			break;
//
//		case "STG":
//			getDriver().get(ppty.getProperty("STG_url"));
//			break;
//		
//		case "MyREQ":
//			getDriver().get(ppty.getProperty("QA_MyReq_URL"));
//			break;
//			
//		default:
//			throw new Exception("Environment Not Defined");
//		}
		
	}
	
	public static String fetch_Password_Value(){
		String encodedBytes = ppty.getProperty("Password");
		String decodedBytes = encodedBytes;
		
//		String decodedBytes = new String(Base64.getDecoder().decode(encodedBytes));
		return decodedBytes;
		
	}
	
	public static void javascriptInit() {
		jscript = ((JavascriptExecutor) getDriver());
	}

	public static void actionInit() {
		actions = new Actions(getDriver());
	}

	public static void webDriverWaitInit() {
	//	drvWait = new WebDriverWait(getDriver(), TimeoutValue * 3);
	}
	
	public static Properties testDataProperties() throws IOException {
        dataSourcePpty = new Properties();
        dataSourcePpty.load(loadFileAsStream("/dataSource.properties"));
        return dataSourcePpty;
    }
	

	public static void switchToWindow(WebDriver driver) {
		String parentHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println("size is "+windowHandles.size());
		if (windowHandles.size() > 1)
			//getDriver().close();
		for (String windowHandle : windowHandles) {
			if (!(windowHandle.equals(parentHandle))) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
		//closeNotWnd();
	}
	
	/**
	 * @author Vidya H
	 * 
	 */
	
	public static WebDriver switchToWindowwithParent() {
		String parentHandle = getDriver().getWindowHandle();
		Set<String> windowHandles = getDriver().getWindowHandles();
		WebDriver alertDialog = null;
		System.out.println("size is "+windowHandles.size());
		if (windowHandles.size() > 1)
			// getDriver().close();
			for (String windowHandle : windowHandles) {
				if (!(windowHandle.equals(parentHandle))) {
					getDriver().switchTo().window(windowHandle);
					alertDialog = getDriver().switchTo().window(windowHandle);
					break;
				}
			}
		
		return alertDialog;
	}

	public static void switchBtwWindow(boolean newWdw) {
		String parentHandle = "";
		Set<String> windowHandles = new HashSet<String>();
		if (newWdw) {
			parentHandle = getDriver().getWindowHandle();
			windowHandles = getDriver().getWindowHandles();
			for (String windowHandle : windowHandles) {
				if (!(windowHandle.equals(parentHandle))) {
					getDriver().switchTo().window(windowHandle);
					break;
				}
			}
			//closeNotWnd();
		} else {
			for (String windowHandle : windowHandles) {
				if (!(windowHandle.equals(parentHandle))) {
					getDriver().close();
					break;
				}
			}
			getDriver().switchTo().window(parentHandle);
		}
	}
	
	/**
	 * @author Swati
	 *
	 */
	public static void switchBackToParentWindow(WebDriver driver)
	{
		String parentHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println("size is "+windowHandles.size());
		for (String windowHandle : windowHandles) {
			if (!(windowHandle.equals(parentHandle))) {
				driver.close();
				driver.switchTo().window(windowHandle);
				break;
			}
		}		
	}

	public static WebElement getSelectedOptions(WebElement element) {
		WebElement option = new Select(element).getAllSelectedOptions().get(0);
		return option;
	}

	public static Boolean getValueFromDropDown(WebElement element, String compareText) {
		WebElement option = getSelectedOptions(element);
		if (option.getText().equals(compareText)) {
			return true;
		}
		return false;
	}

	public static List<WebElement> getMultiSelectedOptions(WebElement element) {
		List<WebElement> options = new Select(element).getAllSelectedOptions();
		return options;
	}

	public static Boolean getMultiValueFromDropDown(WebElement element, String compareText) {
		List<WebElement> options = getMultiSelectedOptions(element);
		for (WebElement option : options) {
			if (option.getText().equals(compareText)) {
				return true;
			}
		}
		return false;
	}

	public static void selectDropdown(int Type, WebElement selEle, String dropDwnEle) throws Exception {
		/*
		 * Switch Case Options 1- Select By Visible Text 2- Select By Index 3-
		 * Select By Value
		 */
		try {			
			Select selectLst = new Select(selEle);
			if (selEle.isEnabled() && (!(selectLst.getOptions().size() == 1))) {
				switch (Type) {
				case 1:
					selectLst.selectByVisibleText(dropDwnEle);
					messageHandler.log("Selected dropdown value form Visible text is : " + dropDwnEle);
					break;
				case 2:
					selectLst.selectByIndex(Integer.valueOf(dropDwnEle));
					messageHandler.log("Selected dropdown value form Index is : " + dropDwnEle);
					break;
				case 3:
					selectLst.selectByValue(dropDwnEle);
					messageHandler.log("Selected dropdown value form Value is : " + dropDwnEle);

					break;
				default:
					throw new Exception(
							"Invalid Select Choice - Choose 1 for Select By Visible Text, 2 for Select By Index, 3 for Select By Value");
				}
			} else {
				System.out.println("Dropddown disabled or default value set");
			}
		} catch (Exception e) {
		}
		Thread.sleep(500);
	}

	public static void selectRadioBtn(int type, List<WebElement> eleLst, String dropDwnEle) throws Exception {
		int count = eleLst.size();
		try {
			/*
			 * Switch Case Options 1- Select By Visible Text 2- Select By Value
			 */
			String sValue;
			for (int i = 0; i < count; i++) {
				if (type == 1){
					sValue = eleLst.get(i).getText().toLowerCase();				   
				}
				else
					sValue = eleLst.get(i).getAttribute("value").toLowerCase();

				if (sValue.contains(dropDwnEle.toLowerCase())) {
					drawHighlight(eleLst.get(i));
					mouseOver(eleLst.get(i));
					Thread.sleep(2000);	
					messageHandler.log("Selected Radio Button is : " + eleLst.get(i).getText().toLowerCase());
					moveToEle(eleLst.get(i));
					Thread.sleep(1000);	
					break;
				}
				if (i == count -1)
					throw new Exception("Not Selected as Provided");
			}
		} catch (Exception e) {
		}
	}

	public static void drawHighlight(WebElement element) {
		jscript.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				element);
	}

	public static int getLocation(WebElement element, boolean xCord) {
		int coord;
		Point classname = element.getLocation();
		if (xCord)
			coord = classname.getX();
		else
			coord = classname.getY();

		return coord;
	}

	public static void moveToEle(WebElement to) {
		actions.moveToElement(to).click();
		buildPerform();
	}
	
	public static void mouseOver(WebElement ele) {
		actions.moveToElement(ele).perform();
	}
	public static void draganddrop(WebElement from, WebElement to) {
		// action.clickAndHold(From).moveToElement(To).release().build().perform();
		actions.dragAndDrop(from, to);
		buildPerform();
	}

	public static void draganddropBy(WebElement from, int dropxaxis, int dropyaxis) {
		actions.dragAndDropBy(from, dropxaxis, dropyaxis);
		buildPerform();
	}

	public static void doubleClick(WebElement dblClkEle) {
		actions.doubleClick(dblClkEle);
		buildPerform();
	}

	public static void buildPerform() {
		actions.build().perform();
	}

	public static void loadClassLoader() {
		basedir = CommonFunctions.class.getClassLoader();
	}

	public static InputStream loadFileAsStream(String file) {
		return CommonFunctions.class.getResourceAsStream(file);
	}

	public static String getTargetFilePath(String filename) {
		System.out.println("Printing fileName " +filename);
		String path = basedir.getResource(filename).getPath();
		
		System.out.println("Printing fileName before " +path);
		path = path.indexOf("/") == 0 ? path.substring(1, path.length()) : path;
		System.out.println("Printing fileName after" +path);
		return path;
	}

	public static File getTargetFile(String filename) {
		File retFile = new File(getTargetFilePath(filename));
		return retFile;
	}
	
	/**
	 * @param entry
	 * @return
	 */
	

	public static String encoder(String filePath) {
		String base64File = "";
		System.out.println("File Path : "+filePath);
		
		File file = new File(filePath);
		
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a file from file system
			byte fileData[] = new byte[(int) file.length()];
			imageInFile.read(fileData);
			System.out.println("Data Read from file : "+imageInFile.read(fileData));
			
			base64File = Base64.getEncoder().encodeToString(fileData);
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the file " + ioe);
		}
		return base64File;
	}

	public static void draganddropJscript(WebElement src, WebElement tgt) throws IOException {
		String path = getTargetFilePath("dragAndDropNw.js");
		Path fpath = Paths.get(path);
		String script = new String(Files.readAllBytes(fpath), StandardCharsets.UTF_8);
		script += "simulateHTML5DragAndDrop(arguments[0], arguments[1])";
		jscript.executeScript(script, src, tgt);
	}

	public static void scrollToElement(WebElement element) throws InterruptedException {
		jscript.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
	}

	public static boolean waitForJStoLoad() {
		ExpectedCondition<Boolean> jQueryLoad = null, jsLoad = null;
		try {
			// wait for Jquery to load
			jQueryLoad = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					try {
						return ((long) jscript.executeScript("return jQuery.active") == 0);
					} catch (Exception e) {
						return true;
					}
				}
			};

			// wait for Javascript to load
			jsLoad = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return jscript.executeScript("return document.readyState").toString().equals("complete");
				}
			};
		} catch (Exception e) {
		}
		if (jQueryLoad != null && jsLoad != null)
			return drvWait.until(jQueryLoad) && drvWait.until(jsLoad);
		// if(jsLoad!=null)
		// return drvWait.until(jsLoad);
		else
			return false;
	}
	public static void waitForPageLoad(){
	       ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
	    }
	
	public static void waitForLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(20)).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
				.executeScript("return document.readyState").equals("complete"));
	}
	
	public static boolean waitForSpinner(WebDriver driver, WebElement ele) {
		try {
			while (ele.isDisplayed()) {
				System.out.println("Spinner .....");
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public static String readStream(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader r = new InputStreamReader(is, "UTF-8");
			int c = 0;
			while ((c = r.read()) != -1) {
				sb.append((char) c);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	public static byte[] embedScreenshot() throws Exception {
		byte[] srcBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
		return srcBytes;		
	}
	
	
	public static void TakeScreenshot(Scenario scenario) throws Exception {
	TakesScreenshot scrShot =((TakesScreenshot)getDriver());
	byte[] SrcFile=scrShot.getScreenshotAs(OutputType.BYTES);
	//File DestFile=new File("C:\\Screenshots"+Math.random()+"test1234.png");
	//FileUtils.copyFile(SrcFile, DestFile);
	scenario.attach(embedScreenshot(),"","");
	}
	
	
	
	public static void IncreaseWindowsize(){
		//Action copy = sendKeys(Keys.CONTROL).sendKeys(Keys.SHIFT).sendKeys("-").build(); 
		Keys.chord(Keys.CONTROL,"-");
		Keys.chord(Keys.CONTROL,"-");
		Keys.chord(Keys.CONTROL,"-");
		Keys.chord(Keys.CONTROL,"-");
	}

	public static void javascriptClick(WebElement ele) {
		jscript.executeScript("arguments[0].click();", ele);
	}

	public static void expWaitElementToBeClickable(WebDriver driver, WebElement ele) {
		int count = 0;
		try {
			waitForLoad(driver);
			drvWait.until(ExpectedConditions.elementToBeClickable(ele));
			/*drvWait.until(
            ExpectedConditions.and(
                 ExpectedConditions.elementToBeClickable(ele),
                 ExpectedConditions.invisibilityOfElementLocated(By.xpath("//[@class = 'message-overlay' and @id = 'commondialog']"))
            )
        );*/
		} catch (ElementClickInterceptedException e) {
			count++;
			if (count < 3) {
				expWaitElementToBeClickable(driver, ele);
			}
			try {
				javascriptClick(ele);
			} catch (Exception ex) {
				try {
					throw new Exception("Navigating/Clicking is Failed");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void expWaitElementToBeClickable(WebDriver driver, By path) {
							
		int count = 0;
		try {
			waitForLoad(driver);
			 drvWait = new WebDriverWait(driver,Duration.ofSeconds(30));
			drvWait.until(ExpectedConditions.elementToBeClickable(path));
		} catch (ElementClickInterceptedException e) {
			count++;
			if (count < 5) {
				expWaitElementToBeClickable(driver, path);
			}
			try {
				throw new Exception("Navigating/Clicking is Failed");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public static void expWaitElementToBeClickableByXpath(WebDriver driver, By path) {
		
		int count = 0;
		try {
			waitForLoad(driver);
			drvWait = new WebDriverWait(driver,Duration.ofSeconds(10000));
			drvWait.until(ExpectedConditions.elementToBeClickable(path));
		} catch (ElementClickInterceptedException e) {
			count++;
			if (count < 5) {
				expWaitElementToBeClickable(driver, path);
			}
			try {
				throw new Exception("Navigating/Clicking is Failed");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
        
	public static void expWaitElementToBePresent(WebDriver driver, By path) {
		waitForLoad(driver);
		drvWait = new WebDriverWait(driver,Duration.ofSeconds(10000));
		drvWait.until(ExpectedConditions.presenceOfElementLocated(path));
	}
	
	public static void expWaitElementToBeVisible(WebDriver driver, By path) {
		waitForLoad(driver);
		drvWait = new WebDriverWait(driver,Duration.ofSeconds(30));
		drvWait.until(ExpectedConditions.visibilityOfElementLocated(path));
	}

	public static void expWaitAllElementToBePresent(By path) {
		drvWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(path));
	}

	public static void expWaitTextToBePresent(By path, String text) {
		drvWait.until(ExpectedConditions.textToBePresentInElementLocated(path, text));
	}

	public static void scrollIntoView(WebElement ele) {
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", ele);
	}

	// Adding function to concatenate current date time at the end of a string
	// to make it distinct every time
	public static String ConcatCurrentDateTime(String NameProvided) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String DistinctName = NameProvided.concat(dtf.format(now));
		return DistinctName;
	}
	
	// Adding method to convert a pdf into text stream
	// And verify if a string is in there or not
	// Input- File path name [C:/xyz.pdf]
	// Output- Boolean [ True/False ]
	
	public static boolean VerifyTextInPdf(String pdfPath, String searchText) throws Exception {
		boolean txtPresent = false;
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		try{
		    PDFTextStripper pdfStripper = new PDFTextStripper();
		    String text = pdfStripper.getText(document);
		    txtPresent = text.contains(searchText);
		}
		catch (Exception er) {
			throw new Exception("Pdf could not be validated.");
		}
		finally {
	    document.close();
		}
	    return txtPresent;
	}
	
	// Adding method to convert a pdf into text stream
	// And verify if a string is in there or not
	// Input- File path name [C:/xyz.pdf]
	// Output- Boolean [ True/False ]

	public static boolean VerifyTextsInPdf(String pdfPath, String[] searchText) throws Exception {
		boolean txtPresent = false;
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		try {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
			for (int i = 0; i < searchText.length; i++) {
				txtPresent = text.contains(searchText[i]);
				if (!txtPresent)
					break;
			}
		} catch (Exception er) {
			throw new Exception("Pdf could not be validated.");
		} finally {
			document.close();
		}
		return txtPresent;
	}
	
	/**
	 * @author DGovindhan
	 * @param str
	 * @param timeOut
	 * 
	 */
	public static void elementToBeClickable(String str, int timeOut) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(str)));
	}

	/**
	 * @author DGovindhan
	 * @param str
	 * @param timeOut
	 * 
	 */
	public static void WriteLog(String message) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = new Date();
		String str = dateFormat.format(date);
		messageHandler.log(str +  "****" + message);
	}
	/**
	 * @author DGovindhan
	 * @param str
	 * @param timeOut
	 * 
	 */
	public static void elementToBePresent(By path, int timeOut) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.presenceOfElementLocated(path));
	}

	public static void clickBySelenium(WebElement ele) {
		try {
			drvWait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			System.out.println("Clicked on "+ele);

		} catch (ElementClickInterceptedException e) {
			if (count < 4) {
				expWaitElementToBeClickable(driver, ele);
				System.out.println("Element click intercepted exeption " + count);
				count++;
				clickBySelenium(ele);
			}
		} 
	}	
	
	public void waitPresenceOfElementIsLocated(String str, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(str)));
			Thread.sleep(2000);
			/*
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(
			 * By.xpath(str)));
			 */
		} catch (Exception ex) {
			messageHandler.log("Not Found Specific Visisble Element");
		}
	}
	
	public void waitPresenceOfElementIsinVisibility(String str, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(str)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(str)));
			Thread.sleep(200);			
		} catch (Exception ex) {
			messageHandler.log("Not Found Specific Visisble Element Invisibility Of Element Located");
		}
	}
	
	/**
	 * This method will open up the API passed in Browser and return the entire dataset in JSON Array format
	 * @author DBiswas
	 * @param apiAddress
	 * @return JSONArray
	 * @throws Exception
	 */
	public JSONArray getDataFromAPI(String apiAddress) throws Exception {
		WebDriver tempdriver = new ChromeDriver();
		tempdriver.get(apiAddress);
		WebElement elems = tempdriver.findElement(By.xpath("//pre"));
		JSONArray jsonarr = new JSONArray(elems.getText());
		tempdriver.close();
		return jsonarr;
	}

	
	/**
	 * 
	 * @author DBiswas
	 */
	public static String getCurrentProdLineNameFromURL() {
		String str = getDriver().getCurrentUrl();
		//String str = getDriver().getCurrentUrl().split("/")[10].trim();
		return str;
	}
	

	/**
	 * getDrpDownElementValue() would return the selected element in the drop down
	 *
	 * @author ANayyar
	 * @throws Exception
	 *
	 */
	public String getDrpDownElementValue(WebElement targetEle) throws Exception {
		return  getSelectedOptions(targetEle).getText();
	}

	
	/**
	   * An expectation for checking WebElement any non empty value for given attribute
	   *
	   * @param element   used to check its parameters
	   * @param attribute used to define css or html attribute
	   * @return Boolean true when element has css or html attribute with non empty value
	   */
	
	public static void expWaitAttributeToBeNotEmpty(WebElement ele, String attribute) {
		drvWait.until(ExpectedConditions.attributeToBeNotEmpty(ele, attribute));
	}
	/**
	 * For Password encryption
	 *
	 * @author Devjit
	 * @throws Exception
	 *
	 */
	
	public static String encryptdecryptPwd(String pwd) {
		byte[] encodedBytes = Base64.getEncoder().encode(pwd.getBytes());	
		System.out.println("password is "+encodedBytes.toString());
		
		String decodedBytes = new String(Base64.getDecoder().decode(encodedBytes));
		System.out.println("password is "+decodedBytes);
		return decodedBytes;
	}

	/**
	 * Alert Accept
	 *
	 * @author Moorthy
	 * @throws Exception
	 *
	 */
	public boolean alertAccept() {
		boolean flag = false;
		try {
			Alert alert = getDriver().switchTo().alert();
			// Capturing alert message.
			String alertMessage = getDriver().switchTo().alert().getText();
			// Displaying alert message
			//messageHandler.log(alertMessage);
			System.out.println("alert Text"+alertMessage);
			// Accepting alert
			alert.accept();
			Thread.sleep(5000);
			flag=true;
		} catch (Exception ex) {
			//messageHandler.log("No Alert is present");
			System.out.println("No Alert is present");
		}
		return flag;
	}
	
	/**
	 * Alert Write and Accept
	 *
	 * @author Miguel
	 * @throws Exception
	 *
	 */
	public boolean alertWriteAndAccept(WebDriver driver, String GIN) {
		boolean flag = false;
		try {
			Alert alert = driver.switchTo().alert();
			// Capturing alert message.
			String alertMessage = getDriver().switchTo().alert().getText();
			// Displaying alert message
			//messageHandler.log(alertMessage);
			System.out.println("alert Text: "+alertMessage);
			//Alert write
			Thread.sleep(1000);
			alert.sendKeys(GIN);
			// Accepting alert
			alert.accept();
			Thread.sleep(1000);
			flag=true;
		} catch (Exception ex) {
			//messageHandler.log("No Alert is present");
			System.out.println("No Alert is present");
		}
		return flag;
	}
	
	
	
	public void setYesNoByWebElement(String yesOrno, WebElement webEleYes, WebElement webEleNo) throws Exception {
		if (yesOrno.isEmpty() == false) {
			if (yesOrno.equals("true")) {
				mouseOver(webEleYes);
				webEleYes.click();
			} else {
				mouseOver(webEleNo);
				webEleNo.click();
			}
		}
	}
	
	
	public void switchToSpecificWindow(String Window) throws Exception {
		Set<String> allWindow = getDriver().getWindowHandles();
		 Iterator<String> iterator =allWindow.iterator();
		String firstWindow=iterator.next();	
		String secondWindow=iterator.next();
		String thirdWindow=iterator.next();		
		
		if(Window == "first") 
			getDriver().switchTo().window(firstWindow);
			System.out.println("1st "+getDriver().switchTo().window(firstWindow).getTitle());
		if(Window == "second")
			getDriver().switchTo().window(secondWindow);	
			System.out.println("2nd "+getDriver().switchTo().window(secondWindow).getTitle());
		if(Window == "third")
			getDriver().switchTo().window(thirdWindow);
			System.out.println("3rd "+getDriver().switchTo().window(thirdWindow).getTitle());
	}		
	
	
	
	public void switchToPopupWindow(String WindowID) throws Exception {
		Set<String> allWindow = getDriver().getWindowHandles();
		 Iterator<String> iterator = allWindow.iterator();
		 String popWindow=iterator.next();	
		
			getDriver().switchTo().window(popWindow);
			System.out.println("1st "+getDriver().switchTo().window(popWindow).getTitle());
	
	}		
	
	public static String getRequiredDate(String reqdays) {
		String Operation = null;
		String ReqDate = null;
		
		if (reqdays.contains("-")){
			Operation = "minus";
		}
		else
		{
			Operation = "plus";
		}
	
		reqdays = reqdays.substring(1);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDateTime now = LocalDateTime.now();
	    System.out.println("todays local date "+now);
	    switch (Operation.toLowerCase()){
	   
	    case "plus" :
	    	LocalDateTime AddedDays = now.plusDays(Long.parseLong(reqdays));
	    	System.out.println("Days added "+reqdays);
	    	ReqDate  = AddedDays.format(format);
	    	System.out.println(reqdays +" days after todays Date is :"+ReqDate);
	    	break;
		    
	    case "minus" :
	    	LocalDateTime MinusDays = now.minusDays(Long.parseLong(reqdays));
	    	System.out.println("Days subtracted "+reqdays);
	    	ReqDate  = MinusDays.format(format);
	    	System.out.println(reqdays +" days before todays Date is :"+ReqDate);
	    	break;
	    }
	 
		return ReqDate;
	}
	
	
	public boolean isUIElementPresent(String UIXpath) {
		boolean isPresent = false;
		if(getDriver().findElements(By.xpath(UIXpath)).size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}
	
	public static String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(9999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
		}
	
	//This Method is used to Verify Column Name and to check column is available on grid 
		public static boolean VerifyColumnName(String ColumnName,String Select) throws Exception {

			CommonFunctions.ZoomOut();

			String[] col= ColumnName.split(",");
			boolean columnIsAvailable = false;
			boolean column1 = false;
			switch(Select) {		
			case "APPROVAL":			
				for(int i=0;i<col.length;i++) {
					try {
						getDriver().findElement(By.xpath(loc.getProperty("ApprovalColumn")+col[i]+"']")).isDisplayed();
						System.out.println("'"+col[i]+"' column is Displayed");
					} catch (Exception e) {
						CommonFunctions.WriteLog("'"+col[i]+"' column is NOT Displayed");
						System.out.println("'"+col[i]+"' column is NOT Displayed");
						columnIsAvailable = true;
						e.printStackTrace();
						CommonFunctions.ScrollRight();
					}	
					if (i==9 || i==16) CommonFunctions.ScrollRight();
				}
				 break;
				//"BANNER","FAVORITE","SEARCH"
			case "BFS" :
				for(int i=0;i<col.length;i++) {
					try {
						getDriver().findElement(By.xpath(loc.getProperty("docgrid")+col[i]+"']")).isDisplayed();
						System.out.println("'"+col[i]+"' column is Displayed");
					} catch (Exception e) {
						CommonFunctions.WriteLog("'"+col[i]+"' column is NOT Displayed");
						System.out.println("'"+col[i]+"' column is NOT Displayed");
						columnIsAvailable = true;
						e.printStackTrace();
						CommonFunctions.ScrollRight();
					}	
					if (i==7 ) CommonFunctions.ScrollRight();
				}
				 break;


			case "NaA":
				for(int i=0;i<col.length;i++) {
					try {
						column1=getDriver().findElement(By.xpath(loc.getProperty("notificationgrid_header")+col[i]+"']")).isDisplayed();
					} catch (Exception e) {
						e.printStackTrace();
					}	
					if(column1==true) {
						System.out.println("'"+col[i]+"' is Available");
					}
					else if(column1==false) {		
					boolean column2 =getDriver().findElement(By.xpath(loc.getProperty("notificationgrid_header")+col[i]+"']")).isDisplayed();
					CommonFunctions.implicitlyWait(70);
					if(column2==true) {
						System.out.println("'"+col[i]+"' is Available");
					}
					}
					else{
						System.out.println("'"+col[i]+"' is NOT Available");
					}
				}	
				 break;	

			case "TSC":
				for(int i=0;i<col.length;i++) {
					try {
						column1=getDriver().findElement(By.xpath(loc.getProperty("contactsgrid_header")+col[i]+"']")).isDisplayed();
					} catch (Exception e) {
						e.printStackTrace();
						CommonFunctions.ScrollRight();
					}	
					if(column1==true) {
						System.out.println("'"+col[i]+"' is Available");
					}
					else if(column1==false) {		
					boolean column2 =getDriver().findElement(By.xpath(loc.getProperty("contactsgrid_header")+col[i]+"']")).isDisplayed();
					CommonFunctions.implicitlyWait(70);
					if(column2==true) {
						System.out.println("'"+col[i]+"' is Available");
					}
					}
					else{
						System.out.println("'"+col[i]+"' is NOT Available");
					}
				}
				 break;	 
			}
			return columnIsAvailable;
		}
		
		//This method is used to Scroll Right
		public static void ScrollRight() {
			WebElement element=getDriver().findElement(By.xpath(loc.getProperty("Scroll")));
			element.sendKeys(Keys.ENTER);
			for(int i=0;i<25;i++) {
			element.sendKeys(Keys.ARROW_RIGHT);
			}
			System.out.println("Right scroll is done");	
		}
	
		//This method is used to select column value or apply filter of that value
		public static void ColumnValue(String Value,String Select) throws InterruptedException{
			Thread.sleep(3000);
			switch(Select) {
			//"SEARCH","BANNER"
			case "SB":
				getDriver().findElement(By.xpath(loc.getProperty("Filter_Search"))).sendKeys(Value);
			    CommonFunctions.implicitlyWait(70);
				 break;
			case "TSC":
				getDriver().findElement(By.xpath(loc.getProperty("TSC_filter_search"))).sendKeys(Value);
				CommonFunctions.implicitlyWait(70);
				 break;
				 //"NaA","APPROVAL"
			case "NaAAPPROVAL":
				getDriver().findElement(By.xpath(loc.getProperty("Ftr_Search"))).sendKeys(Value);
			    CommonFunctions.implicitlyWait(70);
				break;
			}
		}
		//This Method is used to Click on Apply,Cancel and Clear button
		public static void ApplyCancelClear(String operation) throws InterruptedException{
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//button[contains(text(),'"+operation+"')]")).click();
			 CommonFunctions.implicitlyWait(30);
		}	
		
		//This method is used to CHECK the box in setting
		public static void SettingClickCheckBox(String Column) {
			getDriver().findElement(By.xpath(loc.getProperty("auto_select")+Column+"']")).click();
			CommonFunctions.implicitlyWait(50); 
		}
		
		//This method is used to CHECK the box of settings 
		public static void SettingAvailableCheckBox(String Column){
			try {
			boolean available =getDriver().findElement(By.xpath(loc.getProperty("auto_select")+Column+"']/label/input")).isDisplayed();
			CommonFunctions.implicitlyWait(100);
			if(available==true) {
				System.out.println(Column +" is available with Checkbox");
			}
			else {
				System.out.println(Column +" is NOT available with Checkbox");
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//This method is used to apply sort on Column 
		public static void SortColumn(String ColumnName,String Select) throws InterruptedException{
			switch(Select) {		
			case "APPROVAL":
				try {				
					boolean col= getDriver().findElement(By.xpath(loc.getProperty("ApprovalColumn")+ColumnName+"']")).isDisplayed();
					CommonFunctions.implicitlyWait(300);
					if(col==true) {
						getDriver().findElement(By.xpath(loc.getProperty("ApprovalColumn")+ColumnName+"']")).click();
						CommonFunctions.implicitlyWait(300);
					}
					CommonFunctions.implicitlyWait(100);
				}catch (Exception e) {
					e.printStackTrace();			
					CommonFunctions.ScrollRight();
				}
				break;
				//"BANNER","FAVORITE","SEARCH"
			case "BFS":
				try {
					boolean col= getDriver().findElement(By.xpath(loc.getProperty("docgrid")+ColumnName+"']")).isDisplayed();				
					CommonFunctions.implicitlyWait(100);
					if(col==true) {
						getDriver().findElement(By.xpath(loc.getProperty("docgrid")+ColumnName+"']")).click();
						CommonFunctions.implicitlyWait(300);
					}
					CommonFunctions.implicitlyWait(100);
				}catch (Exception e) {
					e.printStackTrace();
					CommonFunctions.ScrollRight();
				}
				break;	
			case "NaA":
				try {
					boolean col= getDriver().findElement(By.xpath(loc.getProperty("notificationgrid_header")+ColumnName+"']")).isDisplayed();
					CommonFunctions.implicitlyWait(100);
					if(col==true) {
						getDriver().findElement(By.xpath(loc.getProperty("notificationgrid_header")+ColumnName+"']")).click();
						CommonFunctions.implicitlyWait(300);
					}
					CommonFunctions.implicitlyWait(100);
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;	
			case "TSC":
				try {
					boolean col= getDriver().findElement(By.xpath(loc.getProperty("contactsgrid_header")+ColumnName+"']")).isDisplayed();
					CommonFunctions.implicitlyWait(100);
					if(col==true) {
						getDriver().findElement(By.xpath(loc.getProperty("contactsgrid_header")+ColumnName+"']")).click();
						CommonFunctions.implicitlyWait(300);
					}
					CommonFunctions.implicitlyWait(100);
				}catch (Exception e) {
					e.printStackTrace();
					CommonFunctions.ScrollRight();
				}
				break;
			}
		}
		
		
		//This Method is used to select an Action From 'Action' Dropdown 
		public static void SelectActionDropDown(String Value) throws Exception{
			Thread.sleep(2000);
			try {
			boolean ele= getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).isDisplayed();
			CommonFunctions.implicitlyWait(200);
			if(ele==true) {
				getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).click();
				CommonFunctions.implicitlyWait(100);
				System.out.println("Clicked");	
				}			
			else {
				System.out.println(Value+" is not displayed");
			}
			}
			catch(org.openqa.selenium.NoSuchElementException e) {

				getDriver().findElement(By.xpath(loc.getProperty("Act_Fav"))).click();
				CommonFunctions.implicitlyWait(300);

				boolean ele= getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).isDisplayed();
				CommonFunctions.implicitlyWait(200);
				if(ele==true) {
					getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).click();
					CommonFunctions.implicitlyWait(200);
				}
				else {
					System.out.println(Value+" is not displayed");
				}
			}
			catch(Exception e) {
			e.printStackTrace();
			boolean ele= getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).isDisplayed();
			CommonFunctions.implicitlyWait(200);
			if(ele==true) {
				getDriver().findElement(By.xpath(loc.getProperty("docgrid_menu")+Value+"']")).click();
				CommonFunctions.implicitlyWait(200);
			}
			else {
				System.out.println(Value+" is not displayed");
			}			
			}					
		}
		
		//This method is used to Scroll left
		public static void ScrollLeft() {
		WebElement element=getDriver().findElement(By.xpath(loc.getProperty("Scroll")));
		element.sendKeys(Keys.ENTER);
		for(int i=0;i<40;i++) {
		element.sendKeys(Keys.ARROW_LEFT);
		}
		System.out.println("Left scroll is done");
	    }
		
		//This method is used to Find File from Downloads folder
		public static boolean FindFile(String filename)
	    {
	        String home = System.getProperty("user.home");
	            String file_name = filename;
	        String file_with_location = home + "\\Downloads\\" + file_name;
	        System.out.println("Function Name ===========================" + home + "\\Downloads\\" + file_name);
	        File file = new File(file_with_location);
	        if (file.exists()) {
	            System.out.println(file_with_location + " is present");
	            return true;
	        } else {
	            System.out.println(file_with_location + " is not present");
	            return false;
	        }
	    }
		
		//This method is used to Delete File from Downloads folder
				public static boolean DeleteFile(String filename) {
				    String home = System.getProperty("user.home");
				    String file_name = filename;
				    String file_with_location = home + "\\Downloads\\" + file_name;
				    System.out.println("Function Name ===========================" + home + "\\Downloads\\" + file_name);
				    File file = new File(file_with_location);
				    if (file.exists()) {
				        System.out.println(file_with_location + " is present");
				        if (file.delete()) {
				            System.out.println("file deleted");
				            return true;
				        } else {
				        	if(file.delete()) {
				        		  System.out.println("file deleted");
						            return true;
				        	}
				        	else {
				            System.out.println("file not deleted");
				            return false;
				        	}
				        }
				    } else {
				        System.out.println(file_with_location + " is not present");
				        return true;
				    }
				}
		
		//This method is used to verify available checked box of settings 
		public static void VerifySettingCheckedBox(String Column) {
			try {
				boolean available =getDriver().findElement(By.xpath(loc.getProperty("auto_select")+Column+"' and @aria-selected='true']")).isDisplayed();
				CommonFunctions.implicitlyWait(30);
				if(available==true) {
					System.out.println(Column +" is available with Checked box");
				}
				else {
					System.out.println(Column +" is available with UnChecked box");
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		
		//This method is used to UNCHECK the box of settings 
		public static void VerifySettingUnCheckedBox(String Column) {
			try {
				boolean available =getDriver().findElement(By.xpath(loc.getProperty("auto_select")+Column+"' and @class='wj-listbox-item wj-state-selected']")).isDisplayed();
				CommonFunctions.implicitlyWait(30);
				if(available==true) {
					System.out.println(Column +" is available with UnChecked box");
				}
				else {
					System.out.println(Column +" is available with Checked box");
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		//This method is used to get Token
		 public static String getAccessToken() {
		        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		        String temp = (String) jsExecutor.executeScript(String.format("return window.localStorage.getItem('%s')", "token"));
		       System.out.println(temp);
		       CommonFunctions.WriteLog(temp);
		        return temp;
		    }	 
		
		public static void passFileAsPayload(String role)
		{
			// Creating a File instance 
			File jsonDataInFile = new File("src/test/java/"+role+".json");
			//GIVEN
			//RestAssured.baseURI="https://ecmcsp.evq.slb.com/cspsit/api/UserAccessRequest";
			    given()
					.baseUri("https://ecmcsp.evq.slb.com/cspsit/api/UserAccessRequest")
					.auth().oauth2(CommonFunctions.getAccessToken())
					.contentType(ContentType.JSON)
					.body(jsonDataInFile)
			// WHEN
				.when()
					.post()
					// THEN
				.then()
					.assertThat()
					.statusCode(200);
		
//			given().log().all().auth().oauth2(CommonFunctions.getAccessToken()).contentType(ContentType.JSON)
//			.body(jsonDataInFile)
//	// WHEN
//		.when()
//			.post()
//			// THEN
//		.then()
//			.assertThat()
//			.statusCode(200);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		}
		

		//This method is used to VERIFY DATA the filter by condition
		public static boolean VerifyDataOnStatus(String type) {
			boolean isOnList = false;
			String ListFilterSelect = getDriver().findElement(By.xpath(loc.getProperty("List_Filter_Select"))).getText();
			String [] listFilter = ListFilterSelect.split("\n");
			String [] listType = type.split(",");
			for (int i = 0; i < listFilter.length;i++) {
				String listFilterItem = listFilter[i];
				String listTypeItem = listType[i];
				if (listFilterItem.equals(listTypeItem)) {
				    isOnList = true;
				} else {
				    isOnList = false;
				}
			}
			CommonFunctions.implicitlyWait(50); 
			return isOnList;
		}

		public static void ZoomOut()
        {
			for(int i=0; i<2; i++){
			    Robot robot;
				try {
					robot = new Robot();
				    robot.keyPress(KeyEvent.VK_CONTROL);
				    robot.keyPress(KeyEvent.VK_MINUS);
				    robot.keyRelease(KeyEvent.VK_CONTROL);
				    robot.keyRelease(KeyEvent.VK_MINUS);
				} catch (AWTException e) {
					e.printStackTrace();
				}

			}
        }
		
		public static void ZoomIn()
        {
			for(int i=0; i<2; i++){
			    Robot robot;
				try {
					robot = new Robot();
				    robot.keyPress(KeyEvent.VK_CONTROL);
				    robot.keyPress(KeyEvent.VK_PLUS);
				    robot.keyRelease(KeyEvent.VK_CONTROL);
				    robot.keyRelease(KeyEvent.VK_PLUS);
				} catch (AWTException e) {
					e.printStackTrace();
				}

			}
        }
		
		//This method is used to VERIFY DATA the filter by condition
		public static boolean VerifyDataOnFilterByCondition(String type) {
			boolean isOnList = false;
			String ListFilterSelect = getDriver().findElement(By.xpath(loc.getProperty("List_Filter_Select"))).getText();
			String [] dataOnFilter = ListFilterSelect.split("\n");
			String [] types = type.split(",");
			for (int i = 0; i < types.length;i++) {
				for (int j = 0; j < dataOnFilter.length;) {
					String oneType = types[i];
					System.out.println(ListFilterSelect);
					System.out.println(oneType);
					if (ListFilterSelect.contains(oneType)) {
						System.out.println("Inside if ");
					    isOnList = true;
					    break;
					} else {
						System.out.println("Inside else ");
					    isOnList = false;
					    break;
					}
				}
				if (!isOnList) {
					System.out.println("Inside oustif ");
					return false;
				}
			}
			return isOnList;
		}
		
		//This method is used to go to Home page
		public static void goToHome() {
			WebElement element=getDriver().findElement(By.xpath(loc.getProperty("Scroll")));
			element.sendKeys(Keys.CONTROL, Keys.HOME);
			System.out.println("Go to Home is done");	
		}

	}