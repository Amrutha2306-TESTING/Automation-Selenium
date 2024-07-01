package PageFactoryElements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.CommonFunctions;
import Utilities.MyTestResults;

public class MyPCPLoginPF extends CommonFunctions{

	static MyTestResults results = new MyTestResults();

	public static WebDriver driver;

	// Store the current window handle
	String winHandleBefore;


	public MyPCPLoginPF(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}	
	public void clickonLogin() {
		//getDriver().findElement(By.xpath(loc.getProperty("Login_Btn"))).sendKeys(Keys.ENTER);
		getDriver().findElement(By.xpath(loc.getProperty("Login_Btn"))).click();
		implicitlyWait(100);
	}

	public void EnterEmail(String email) throws Exception{
		winHandleBefore = getDriver().getWindowHandle();
		// Switch to new window opened
		for(String winHandle : getDriver().getWindowHandles()){
			getDriver().switchTo().window(winHandle);
		}
		getDriver().findElement(By.xpath(loc.getProperty("Email"))).sendKeys(email);
		implicitlyWait(30);
	}

	public void clickonSignin() {
		getDriver().findElement(By.xpath(loc.getProperty("SignIn_Btn"))).sendKeys(Keys.ENTER);	
		implicitlyWait(100);
	}

	public void EnterPassword() throws Exception {
		expWaitElementToBeClickableByXpath(getDriver(), By.xpath(loc.getProperty("Password")));
		getDriver().findElement(By.xpath(loc.getProperty("Password"))).sendKeys(CommonFunctions.fetch_Password_Value());
		//getDriver().findElement(By.xpath(loc.getProperty("pass_SignIn"))).sendKeys(Keys.ENTER);
		getDriver().findElement(By.cssSelector(loc.getProperty("pass_SignIn"))).click();
		expWaitElementToBeClickableByXpath(getDriver(), By.xpath(loc.getProperty("Yes_Btn")));
		getDriver().findElement(By.xpath(loc.getProperty("Yes_Btn"))).sendKeys(Keys.ENTER);
		// Switch back to original browser (first window)
		getDriver().switchTo().window(winHandleBefore);
	}

	public void NavigateMyPCP(String GIN, String elementDetails) throws Exception {
		Thread.sleep(5000);
		boolean alert= alertWriteAndAccept(getDriver(), GIN);
		System.out.println("Alert result: " + alert);
		alertAccept();
		Thread.sleep(300);
		//WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Spinner")));
		//boolean spinner = waitForSpinner(getDriver(), element);
		//if (spinner) {
			expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Element_Details")));
			String title = getDriver().findElement(By.xpath(loc.getProperty("Element_Details"))).getText();
			System.out.println("Title: " + title); 
			if(title.equalsIgnoreCase(elementDetails)) {
				CommonFunctions.WriteLog("App was open correctly: "+ elementDetails);
				System.out.println("App was open correctly: "+ elementDetails);
			}
			else {
				CommonFunctions.WriteLog("App was NOT open correctly: "+ elementDetails);
				System.out.println("App was NOT open correctly: "+ elementDetails);
				Assert.assertEquals("Pass","Fail");				
			}
		//}
	}

	public void VerifyUserNameIsDisplayed (String userName) throws Exception {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("User_Name_Top_Rigth_Corner")));
		String userNameTopRight = getDriver().findElement(By.xpath(loc.getProperty("User_Name_Top_Rigth_Corner"))).getText();
		System.out.println("User: " + userNameTopRight);
		if(userName.contains(userNameTopRight) && !userNameTopRight.isEmpty()) {
			CommonFunctions.WriteLog("App was open correctly with user name: "+ userNameTopRight);
			System.out.println("App was open correctly with user name: "+ userNameTopRight);
		}
		else {
			CommonFunctions.WriteLog("App was NOT open correctly with user name: "+ userNameTopRight);
			System.out.println("App was NOT open correctly with user name: "+ userNameTopRight);
			Assert.assertEquals("Pass","Fail");				
		}
	}
	
	public void closeSurvery() {
		getDriver().findElement(By.cssSelector(loc.getProperty("Close_Survery"))).sendKeys(Keys.ENTER);	
		implicitlyWait(100);
	}

	public void closeMyPCP() throws InterruptedException {
		Thread.sleep(1000);
		getDriver().quit();
	}
}

