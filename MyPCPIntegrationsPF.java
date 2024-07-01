package PageFactoryElements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.CommonFunctions;
import Utilities.MyTestResults;

public class MyPCPIntegrationsPF extends CommonFunctions{

	static MyTestResults results = new MyTestResults();

	public static WebDriver driver;

	// Store the current window handle
	String winHandleBefore;


	public MyPCPIntegrationsPF(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}	

	public String NavigateToCAT(String title) throws Exception {
		getDriver().switchTo().newWindow(WindowType.TAB);
		getDriver().navigate().to(ppty.getProperty("CAT_URL")); 
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Fixed_Step_Management")));
		String titleFixedStep = getDriver().findElement(By.xpath(loc.getProperty("Fixed_Step_Management"))).getText();
		System.out.println("Title: " + title); 
		Thread.sleep(8000);
		return titleFixedStep;
	}
	
	public void ClickOnNewPopulation () throws Exception {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("New_Population_Button")));
		getDriver().findElement(By.xpath(loc.getProperty("New_Population_Button"))).click();
		switchBackToParentWindow(getDriver());
		//getDriver().findElement(By.xpath(loc.getProperty("MyOdyssey_Role"))).click();
		Thread.sleep(28000);
	}

}

