package pages;
import config.Base;
import utils.GlobalHelper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.LoggerFactory;

public class FeaturePage extends Base{

    WebDriver driver;
	GlobalHelper helper = new GlobalHelper();
    private static org.slf4j.Logger log = LoggerFactory.getLogger(FeaturePage.class);
    
    @FindBy (xpath = "//ul[@class='nav nav-vertical']/li/a")
    private List<WebElement> vertical_navigation_list;

    @FindBy (xpath = "//ul[@class='nav nav-vertical']/li/a/h6")
    private List<WebElement> vertical_navigation_list_title;

    @FindBy (xpath = "//div[@class='nav flex-column']/a")
    private List<WebElement> bottom_nav_links;

    @FindBy (xpath = "//*[@name='intercom-messenger-frame']")
    private WebElement contact_us_iframe;

    @FindBy (xpath = "//h2[@class='intercom-zo02ra e1yjqpel2']")
    private WebElement your_conversatsion_txt;

    public FeaturePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean getActiveTab(String tab_name) {
        if(vertical_navigation_list.size() == vertical_navigation_list_title.size()){
            int count = 0;
            for(WebElement ele : vertical_navigation_list){ 
                // log.error("tab active =>"+ele.getAttribute("class").contains("active"));   
                if(ele.getAttribute("class").contains("active")){
                    String title = vertical_navigation_list_title.get(count).getText().trim();
                    if(title.equals(tab_name)){
                        return true;
                    }else{
                        log.error("Active tab name title mismatched : "+title);
                        return false;
                    }
                }
                count++;
            }
        }
        return false;
	}

	public boolean switchTabAnniversariesAndBirthdays(String tab_name) {
        if(vertical_navigation_list.size() == vertical_navigation_list_title.size()){
            int count = 0;
            for(WebElement ele : vertical_navigation_list_title){ 
                String title = ele.getText().trim();
                if(title.equals(tab_name)){
                    vertical_navigation_list.get(count).click();
                    helper.wait3Seconds();
                    return getActiveTab(tab_name);
                }
                count++;
            }
        }
		return false;
    }
    
    public boolean openConatctUsdialogue(String nav_title){
        if(nav_title.length() > 0 && nav_title != null){
            helper.scrollToView(driver, bottom_nav_links.get(0));
            for(WebElement ele : bottom_nav_links){
                if(ele.getText().trim().equals(nav_title)){
                    ele.click();
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(contact_us_iframe));
                    helper.wait3Seconds();
                    if(your_conversatsion_txt.isDisplayed()){
                        return true;
                    }
                }
            }
        }else{
            log.error("Navigation title can't be null!");
            return false;
        }
        return false;
    }

    public ContactUsFrame redirectToContactFrame(){
        return new ContactUsFrame(driver);
    }
}