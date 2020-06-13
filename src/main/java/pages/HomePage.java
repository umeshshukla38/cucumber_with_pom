package pages;
import config.Base;
import java.util.List;
import utils.GlobalHelper;
import org.slf4j.LoggerFactory;
import definitions.AssemblyFeature;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends Base {

	WebDriver driver;
	GlobalHelper helper = new GlobalHelper();
	private static org.slf4j.Logger log = LoggerFactory.getLogger(AssemblyFeature.class);

	@FindBy(xpath = "//body[@id='home']/nav/div/div[2]/div/a[1]")
	private WebElement signin;

	@FindBy(xpath = "//*[@name='intercom-launcher-frame']")
	private WebElement chat_box_notfifier_frame;

	@FindBy(xpath = "//*[@name='intercom-note-frame']")
	private WebElement chat_box_frame;

	@FindBy(xpath = "//*[@id='intercom-container']/div/span/div/div/div/span")
	private WebElement close_chat_frame;

	@FindBy(xpath = "//input[@name='EMAIL']")
	private List<WebElement> emailList;

	@FindBy(xpath = "//h1[contains(text(),'Empower your team')]")
	private WebElement empower_your_team_title_text;

	@FindBy(xpath = "//*[@id='empoweryourteam']/div[1]/div[2]/form/div/input")
	private WebElement email_input;

	@FindBy(xpath = "//button[@class='btn btn-sm btn-tryAssembly btn-round w-150 fs-15 text-capitalize']")
	private WebElement try_for_free_btn;

	@FindBy(xpath = "//h5")
	private WebElement verify_email_page_title;

	@FindBy (xpath = "//a[contains(text(),'resend code')]")
	private WebElement resend_code_btn;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String validateSiteTitle() {
		wait.until(ExpectedConditions.elementToBeClickable(signin));
		helper.wait3Seconds();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(chat_box_frame));
		if (close_chat_frame.isDisplayed()) {
			helper.wait3Seconds();
			close_chat_frame.click();
			log.info("Iframe closed further process.");
		}

		String url = driver.getCurrentUrl();
		if (url.equals(prop.getProperty("baseurl"))) {
			return driver.getTitle();
		}
		return null;
	}

	public int getEmailList() {
		int count = 0;
		if (emailList.size() > 0) {
			for (WebElement ele : emailList) {
				if (ele.isDisplayed())
					count++;
			}
		}
		return count;
	}

	public boolean firstEmailEnter(String email, String validation_msg) {
		boolean is_validated = false;
		if (email.length() > 0 || email != null) {
			helper.scrollToView(driver, try_for_free_btn);
			helper.wait3Seconds();
			boolean is_required = Boolean.parseBoolean(email_input.getAttribute("required"));
			if (is_required) {
				helper.scrollToView(driver, empower_your_team_title_text);
				email_input.clear();
				email_input.sendKeys(email);
				try_for_free_btn.click();
				wait.until(ExpectedConditions.elementToBeClickable(try_for_free_btn));
				helper.wait3Seconds();
				String error_msg = email_input.getAttribute("placeholder").trim();
				// log.error("Debug =>"+error_msg+" : "+validation_msg);
				boolean is_error_validated = error_msg.equals(validation_msg);
				if (is_error_validated) {
					log.info("Wrong email validation validated successfully : " + is_error_validated + "\n Error message is : " + error_msg);
				}
				is_validated = is_error_validated;
			}
		}
		return is_validated;
	}

	public boolean secondEmailEnter(String email) {
		String ex_landed_url = createExUrl(prop.getProperty("baseurl") + "verify");
		if(email.length() > 0 || email != null) {
			driver.navigate().refresh();
			email_input.clear();
			email_input.sendKeys(email);
			try_for_free_btn.click();
			helper.wait3Seconds();
			String landed_url = driver.getCurrentUrl();
			if(landed_url.contains(ex_landed_url)) {
				wait.until(ExpectedConditions.elementToBeClickable(resend_code_btn));
				helper.wait3Seconds();
				if(verify_email_page_title.getText().trim().length() > 0 && resend_code_btn.isDisplayed()){
					// log.error("trying to navigate back-----> ");
					driver.navigate().back();
					wait.until(ExpectedConditions.elementToBeClickable(signin));
					helper.wait3Seconds();
					return true;
				}else{
					log.error("Resend code button not available on page which url is : "+ex_landed_url);
				}
			}
		}
		return false;
	}

	private String createExUrl(String url){
		StringBuffer str = new StringBuffer(url);
		str.insert(8, "my.");
		return str.toString();
	}

	@FindBy (xpath = "//div[@class='topbar-left']/ul/li/a")
	private List<WebElement> nav_links_list;

	@FindBy (xpath = "//ul[@class='nav nav-vertical']/li/a/h6")
	private List<WebElement> vertical_navigation_list;

	public boolean navigateFeaturePage() {
		if(nav_links_list.size() > 0){
			for(WebElement ele : nav_links_list){
				if(ele.getText().trim().equals("Features")){
					ele.click();
					helper.wait3Seconds();
					break;
				}
			}
			if(vertical_navigation_list.size() > 0){
				return true;
			}
		}else{
			log.error("Navigation links list on topbar navigation menu not found!");
			return false;
		}
		return false;
	}

	public FeaturePage createFeaturePageInstance(){
		return new FeaturePage(driver);
	}
}
