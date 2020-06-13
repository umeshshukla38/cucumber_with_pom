package definitions;
import config.Base;
import config.Constants;
import config.DataProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.ContactUsFrame;
import pages.FeaturePage;
import pages.HomePage;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class AssemblyFeature extends Base{

	HomePage homepage;
	FeaturePage featurePage;
	ContactUsFrame contactUsFrame;
	Constants cons = new Constants();
	DataProvider dp = new DataProvider();
	private static org.slf4j.Logger log = LoggerFactory.getLogger(AssemblyFeature.class);
	
	@Given("^Launch browser and navigate to join assembly website.$")
	public void launchBrowser() {
		boolean isValidate = true;
		preTest();
		homepage = new HomePage(driver);
		String title = homepage.validateSiteTitle();
		if(!title.equals(cons.SITE_TITLE)) {
			isValidate = false;
			log.error("Site title validation got failed!");
		}
		Assert.assertTrue(isValidate);
	}

	@Then("^Search email keyword and return list of count.$")
	public void searchEmail() {
		boolean isEmailCountValidated = false;
		int email_count = homepage.getEmailList();
		isEmailCountValidated = email_count == cons.EMAIL_COUNT;
		Assert.assertEquals(isEmailCountValidated, true, "Error => Email keyword count is not valid!");
	}

	@Then("^Enter abc@gmail.com, click on Try for Free and test.$")
	public void EnterEmailActionOne() {
		String email = dp.emailList().get(0);
		String email_validation = "Please try a work email";
		boolean validated = homepage.firstEmailEnter(email, email_validation);
		Assert.assertEquals(validated, true, "Error => "+email+" email is not validated!");
	}

	@Then("^Enter abc@carrothr.com, click on Try for Free and test.$")
	public void EnterEmailActionTwo() {
	    String email = dp.emailList().get(1);
		boolean validated = homepage.secondEmailEnter(email);
		Assert.assertEquals(validated, true, "Error => "+email+" email case not validated!");
	}

	@Then("^Navigate to feature page.$")
	public void navigateFeaturePage() {
		boolean value = homepage.navigateFeaturePage();
		if(value)
			featurePage = homepage.createFeaturePageInstance();
		Assert.assertEquals(value, true, "Error => not able to navigate on feature page!");   
	}

	@Then("^Validate Recognition tab should be highlighted.$")
	public void validateHighlightedText() {
		String tab_name = cons.DEFAULT_ACTIVE_TAB;
		boolean validated = featurePage.getActiveTab(tab_name);
		Assert.assertEquals(validated, true, "Error => Active tab validation got failed!");   
	}

	@Then("^Click on Anniversaries and Birthdays should enable this tab and disable all other tabs.$")
	public void switchTabAandB() {
		String tab_name = cons.CHANGE_TAB_NAME;
		boolean validated = featurePage.switchTabAnniversariesAndBirthdays(tab_name);
		Assert.assertEquals(validated, true, "Error => During switch tab error occured!");
	}

	@Then("^Scroll to footer and click on Contact Us under Support.$")
	public void ScrollFooterAtContactUs() {
		boolean value = featurePage.openConatctUsdialogue(cons.CONTACT_US_NAV_TITLE);
		if(value)
			contactUsFrame = featurePage.redirectToContactFrame();
		Assert.assertEquals(value, true, "Error => Not able to switch on frame!");
	}

	@Then("^Enter Slack in Find an answer yourself field and verify the results displayed.$")
	public void enterSlackAndVerify() { 
		boolean validated = contactUsFrame.validateFindYourself(cons.FIND_YOURSELF_VALUE);
		Assert.assertEquals(validated, true, "Error => Searched result set validation failed!");
		postTest();
	}
}
