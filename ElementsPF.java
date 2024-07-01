package PageFactoryElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.taskdefs.Length;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import Utilities.CommonFunctions;

public class ElementsPF extends CommonFunctions{
	
	public ElementsPF(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public String  verifyElementsAssociatedToThatGIN() {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Number_Of_Elements")));
		String numberOfElements = getDriver().findElement(By.xpath(loc.getProperty("Number_Of_Elements"))).getText();
		return numberOfElements;
	}
	
	public List<WebElement> verifyNumberOfElementsWithProficiencyLevels() {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Basic_Level_Elements")));
		List<WebElement> checks =  getDriver().findElements(By.xpath(loc.getProperty("Elements_Count")));
		for (Iterator<WebElement> iterator = checks.iterator(); iterator.hasNext();) {
			WebElement webElement = (WebElement) iterator.next();
			System.out.println(webElement.getText());
		}
		return checks;
	}
	
	public void enterNameElementOnSearchBox(String element) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Search_Element")));
		getDriver().findElement(By.xpath(loc.getProperty("Search_Element"))).sendKeys(element);
	}
	
	public void clickOnMagnifyingGlass() throws InterruptedException {
		Thread.sleep(1000);
		getDriver().findElement(By.xpath(loc.getProperty("Search_Element"))).sendKeys(Keys.ENTER);
	}
	
	public int verifyNameElementsSearchedFor() {
		int count = 0;
		List<WebElement> checks =  getDriver().findElements(By.xpath(loc.getProperty("Name_Elements_Searched_For")));
		for (Iterator<WebElement> iterator = checks.iterator(); iterator.hasNext();) {
			WebElement webElement = (WebElement) iterator.next();
			//System.out.println(webElement.getText());
			count+=1;
		}
		return count;
	}
	
	public void clickOnOrderBy() throws InterruptedException {
		//expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("User_Sentiment_Survery")));
		//getDriver().findElement(By.xpath(loc.getProperty("User_Sentiment_Survery"))).sendKeys(Keys.ENTER);
		expWaitElementToBeClickable(getDriver(), By.cssSelector(loc.getProperty("Order_By")));
		getDriver().findElement(By.cssSelector(loc.getProperty("Order_By"))).click();
	}
	
	public void selectExpiringSoon (String option) {
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Expiring_Soon") + option + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Expiring_Soon") + option + "')]")).click();
	}
	
	public boolean orderByClick(String option) throws InterruptedException {
		getDriver().navigate().back();
		getDriver().navigate().forward();
		Thread.sleep(1000);
		expWaitElementToBeClickable(getDriver(), By.cssSelector(loc.getProperty("Order_By_After")));
		getDriver().findElement(By.cssSelector(loc.getProperty("Order_By_After"))).click();
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Expiring_Soon") + option + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Expiring_Soon") + option + "')]")).click();
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Option_Order_By_Selected")));
		boolean isDisplayed = getDriver().findElement(By.xpath(loc.getProperty("Option_Order_By_Selected"))).isDisplayed();
		expWaitElementToBeClickable(getDriver(), By.cssSelector(loc.getProperty("Order_By_After")));
		getDriver().findElement(By.cssSelector(loc.getProperty("Order_By_After"))).click();
		System.out.println(isDisplayed);
		return isDisplayed;
	}
	
	public void clickOnFilterBy() throws InterruptedException {
		//expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("User_Sentiment_Survery")));
		//getDriver().findElement(By.xpath(loc.getProperty("User_Sentiment_Survery"))).sendKeys(Keys.ENTER);
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Filter_By")));
		getDriver().findElement(By.xpath(loc.getProperty("Filter_By"))).click();
	}
	
	public void selectCompetencyUnitAndValue (String value) {
		getDriver().findElement(By.xpath(loc.getProperty("Competency_Unit"))).click();
		getDriver().findElement(By.xpath(loc.getProperty("Select_Value"))).click();
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Competency_Unit_Select_Value") + value + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Competency_Unit_Select_Value") + value + "')]")).click();
	}
	
	public boolean filterByCompetencyUnit(String value) {
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Result_List_Of_Elements_Filter_By") + value + "')]"));
		boolean isDisplayed = getDriver().findElement(By.xpath(loc.getProperty("Result_List_Of_Elements_Filter_By") + value + "')]")).isDisplayed();
		System.out.println(isDisplayed);
		return isDisplayed;
	}
	
	public void selectProficiencyLevelAndValue (String value) {
		getDriver().findElement(By.xpath(loc.getProperty("Proficiency_Level"))).click();
		getDriver().findElement(By.xpath(loc.getProperty("Select_Value"))).click();
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Competency_Unit_Select_Value") + value + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Competency_Unit_Select_Value") + value + "')]")).click();
	}
	
	public String filterByProficiencyLevel(String value) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Result_List_Of_Elements_Filter_By")));
		String proficiencyLevel = getDriver().findElement(By.xpath(loc.getProperty("Result_List_Of_Elements_Filter_By"))).getText();
		System.out.println(proficiencyLevel);
		return proficiencyLevel;
	}
	
	public void clickOnElementName (String name) throws InterruptedException {
		//expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("User_Sentiment_Survery")));
		//getDriver().findElement(By.xpath(loc.getProperty("User_Sentiment_Survery"))).sendKeys(Keys.ENTER);
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Element_Name") + name + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Element_Name") + name + "')]")).click();
	}
	
	public String verifyElementName (String name) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Element_Name_Right_Side")));
		String elementNameRightSide = getDriver().findElement(By.xpath(loc.getProperty("Element_Name_Right_Side"))).getText();
		System.out.println(elementNameRightSide);
		return elementNameRightSide;
	}
	
	public void clickOnPCPHistoryTimeline () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Pcp_History_Timeline_button")));
		getDriver().findElement(By.xpath(loc.getProperty("Pcp_History_Timeline_button"))).click();
	}
	
	public String verifyPCPHistoryTimelineIsShow () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Pcp_History_Timeline_Modal_Title")));
		String pcpHistoryTimelineTilte = getDriver().findElement(By.xpath(loc.getProperty("Pcp_History_Timeline_Modal_Title"))).getText();
		System.out.println(pcpHistoryTimelineTilte);
		return pcpHistoryTimelineTilte;
	}
	
	public void clickOnShowHistory () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Show_History")));
		getDriver().findElement(By.xpath(loc.getProperty("Show_History"))).click();
	}
	
	public String verifyShowHistoryBecomesInHideHistory () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Show_History")));
		String hideHistoryName = getDriver().findElement(By.xpath(loc.getProperty("Show_History"))).getText();
		System.out.println(hideHistoryName);
		return hideHistoryName;
	}
	
	public boolean verifyThePCPHistoryTimelineIsShowingAGreenAndOrangeArrows () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Green_Arrow")));
		boolean greenArrow = getDriver().findElement(By.xpath(loc.getProperty("Green_Arrow"))).isDisplayed();
		System.out.println(greenArrow);
		boolean orangeArrow = getDriver().findElement(By.xpath(loc.getProperty("Orange_Arrow"))).isDisplayed();
		System.out.println(orangeArrow);
		if (greenArrow && orangeArrow) {
			return true;
		}
		return false;
	}
	
	public void clickOnShowProficiencyLevel () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Show_Proficiency_Level")));
		getDriver().findElement(By.xpath(loc.getProperty("Show_Proficiency_Level"))).click();
	}	
	
	public String verifyShowProficiencyLevelBecomesInHideProficiencyLevel () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Show_Proficiency_Level")));
		String hideProficiencyLevel = getDriver().findElement(By.xpath(loc.getProperty("Show_Proficiency_Level"))).getText();
		System.out.println(hideProficiencyLevel);
		return hideProficiencyLevel;
	}
	
	public ArrayList<String> verifyThatLevelTargetAwardedExpiresOnfieldsArePopulated () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Level")));
		String level = getDriver().findElement(By.xpath(loc.getProperty("Level"))).getText();
		System.out.println(level);
		String target = getDriver().findElement(By.xpath(loc.getProperty("Target"))).getText();
		System.out.println(target);
		String awarded = getDriver().findElement(By.xpath(loc.getProperty("Awarded"))).getText();
		System.out.println(awarded);
		ArrayList<String> proficiencyLevel = new ArrayList<String> ();
		proficiencyLevel.add(level);
		proficiencyLevel.add(target);
		proficiencyLevel.add(awarded);
		return proficiencyLevel;
	}
	
	public ArrayList<String> verifyNeededForNextLevelAssessmentsExperienceAndTrainings () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Assessments")));
		String assessments = getDriver().findElement(By.xpath(loc.getProperty("Assessments"))).getText();
		System.out.println(assessments);
		String experience = getDriver().findElement(By.xpath(loc.getProperty("Experience"))).getText();
		System.out.println(experience);
		String trainings = getDriver().findElement(By.xpath(loc.getProperty("Trainings"))).getText();
		System.out.println(trainings);
		ArrayList<String> neededForNextLevel = new ArrayList<String> ();
		neededForNextLevel.add(assessments);
		neededForNextLevel.add(experience);
		neededForNextLevel.add(trainings);
		return neededForNextLevel;
	}
	
	public ArrayList<String> verifyThatTheButtonHelpHaveFeedbackAndFAQLinks() {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Help_Button")));
		mouseOver(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Feedback_Link")));
		String feedbackLink = getDriver().findElement(By.xpath(loc.getProperty("Feedback_Link"))).getText();
		System.out.println(feedbackLink);
		String fAQLink = getDriver().findElement(By.xpath(loc.getProperty("FAQ_Link"))).getText();
		System.out.println(fAQLink);
		ArrayList<String> links = new ArrayList<String> ();
		links.add(feedbackLink);
		links.add(fAQLink);
		return links;
	}
	
	public String verifyThatRemainingDaysFieldIsPopulatedWithCorrectValue () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Remaining_Days")));
		String remainingDays = getDriver().findElement(By.xpath(loc.getProperty("Remaining_Days"))).getText();
		System.out.println(remainingDays);
		return remainingDays;
	}
	
	public void clickOnFeedback () {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Help_Button")));
		mouseOver(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Feedback_Link")));
		getDriver().findElement(By.xpath(loc.getProperty("Feedback_Link"))).click();
	}
	
	public String verifyItNavigatesToTheESMPortal () {
		switchToWindow(getDriver());
		//expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Remaining_Days")));
		String esmPortal = getDriver().getCurrentUrl();
		System.out.println(esmPortal);
		return esmPortal;
	}
	
	public void clickOnJobExperience () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Job_Experience")));
		getDriver().findElement(By.xpath(loc.getProperty("Job_Experience"))).click();
	}
	
	public String verifyJobExperiencePendingShouldBePesent () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Job_Experience_Pending")));
		String pendingName = getDriver().findElement(By.xpath(loc.getProperty("Job_Experience_Pending"))).getText();
		System.out.println(pendingName);
		return pendingName;
	}
	
	public String verifyJobExperiencePendingShouldBePesent (String message) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Job_Experience_Error")));
		String pendingName = getDriver().findElement(By.xpath(loc.getProperty("Job_Experience_Error"))).getText();
		System.out.println(pendingName);
		return pendingName;
	}
	
	public void clickOnTraining () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Training")));
		getDriver().findElement(By.xpath(loc.getProperty("Training"))).click();
	}
	
	public String verifyTrainingPendingShouldBePesent () {
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Training_Pending")));
		String pendingName = getDriver().findElement(By.xpath(loc.getProperty("Training_Pending"))).getText();
		System.out.println(pendingName);
		return pendingName;
	}
	
	public void clickOnRecentJobExperience () {
		expWaitElementToBeClickable(getDriver(), By.xpath(loc.getProperty("Recent_Job_Experience")));
		getDriver().findElement(By.xpath(loc.getProperty("Recent_Job_Experience"))).click();
	}
	
	public void clickOnCPACompetencyPracticalAssessments () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("CPA_Competency_Practical_Assessments")));
		getDriver().findElement(By.xpath(loc.getProperty("CPA_Competency_Practical_Assessments"))).click();
	}
	
	public String verifyMessageShouldBePesent (String message) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Message") + message + "')]"));
		String messageName = getDriver().findElement(By.xpath(loc.getProperty("Message") + message + "')]")).getText();
		System.out.println(messageName);
		return messageName;
	}
	
	public void clickOnNeededForNextLevel (String value) {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Needed_For_Next_Level") + value + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Needed_For_Next_Level") + value + "')]")).click();
	}
	
	public void clickOnNeededToKeepCurrentLevel (String value) {
		WebElement webElement = getDriver().findElement(By.xpath(loc.getProperty("Element_Name_Right_Side")));
		scrollIntoView(webElement);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Needed_To_Keep_Current_Level") + value + "')]"));
		getDriver().findElement(By.xpath(loc.getProperty("Needed_To_Keep_Current_Level") + value + "')]")).click();
		String element = getDriver().findElement(By.xpath(loc.getProperty("Needed_To_Keep_Current_Level") + value + "')]")).getTagName();
		System.out.println("Element: " +element);
	}
	
	public String verifyPendingDays () {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Remaining_Days")));
		scrollIntoView(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Remaining_Days")));
		String pendingDays = getDriver().findElement(By.xpath(loc.getProperty("Remaining_Days"))).getText();
		System.out.println(pendingDays);
		return pendingDays;
	}
	
	public boolean verifyWarningRemainingDays () {
		expWaitElementToBeVisible(getDriver(), By.cssSelector(loc.getProperty("Warning_Remaining_Days")));
		boolean warningRemainingDays = getDriver().findElement(By.cssSelector(loc.getProperty("Warning_Remaining_Days"))).isDisplayed();
		System.out.println(warningRemainingDays);
		return warningRemainingDays;
	}
	
	public String verifyJobExperienceCompletedShouldBePesent () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Job_Experience_Completed")));
		String pendingName = getDriver().findElement(By.xpath(loc.getProperty("Job_Experience_Completed"))).getText();
		System.out.println(pendingName);
		return pendingName;
	}
	
	public String verifyThatAssessmentFieldsArePopulatedWithCorrectValue () {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("CPA_Details")));
		scrollIntoView(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("CPA_Details")));
		String correctDetails = getDriver().findElement(By.xpath(loc.getProperty("CPA_Details"))).getText();
		System.out.println(correctDetails);
		return correctDetails;
	}
	
	public String verifyThatExperiencesFieldsArePopulatedWithCorrectValue () {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Experience_Details")));
		scrollIntoView(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Experience_Details")));
		String correctDetails = getDriver().findElement(By.xpath(loc.getProperty("Experience_Details"))).getText();
		System.out.println(correctDetails);
		return correctDetails;
	}
	
	public String verifyThatTrainingFieldsArePopulatedWithCorrectValue () {
		WebElement element = getDriver().findElement(By.xpath(loc.getProperty("Assessment_Details")));
		scrollIntoView(element);
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Assessment_Details")));
		String correctDetails = getDriver().findElement(By.xpath(loc.getProperty("Assessment_Details"))).getText();
		System.out.println(correctDetails);
		return correctDetails;
	}
	
	public void clickOnMyOdysseyRole () {
		expWaitElementToBeVisible(getDriver(), By.cssSelector(loc.getProperty("MyOdyssey_Role")));
		getDriver().findElement(By.cssSelector(loc.getProperty("MyOdyssey_Role"))).click();
	}
	
	public boolean verifyTheCalculationShouldBeCorrect () {
		expWaitElementToBeVisible(getDriver(), By.xpath(loc.getProperty("Number_Of_Competencies")));
		String numberOfCompetencies = getDriver().findElement(By.xpath(loc.getProperty("Number_Of_Competencies"))).getText();
		String realPercentaje = getDriver().findElement(By.cssSelector(loc.getProperty("MyOdyssey_Role_Percentaje"))).getText();
		System.out.println("Real Percentaje: " + realPercentaje);
		System.out.println(numberOfCompetencies);
		double numCESPassed = Integer.parseInt(numberOfCompetencies.substring(numberOfCompetencies.length()-3, numberOfCompetencies.length()-2));
		double numCESRequired = Integer.parseInt(numberOfCompetencies.substring(numberOfCompetencies.length()-3, numberOfCompetencies.length()-2));
		System.out.println("Number CES Passed : " +numCESPassed);
		System.out.println("Number CES Required: " +numCESRequired);
		if (numCESPassed>numCESRequired) {
			numCESPassed=numCESRequired;
		}
		double realPercentajeNumber = Double.parseDouble(realPercentaje.replace("%", ""));
		System.out.println("Real Percentaje Number : " + realPercentajeNumber);
		double result = numCESPassed / numCESRequired;
		System.out.println("Result : " +result);
		double percentaje = result * 100;
		System.out.println("Percentaje : " +percentaje);
		if (realPercentajeNumber == percentaje) {
			return true;
		}
		return false;
	}
}
