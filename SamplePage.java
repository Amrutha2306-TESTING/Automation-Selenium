package PageFactoryElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import Utilities.MyTestResults;

public class SamplePage {

		MyTestResults testresult = new MyTestResults();

	/// Constructor
	public SamplePage(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
	}

}