package pages;
import config.Base;
import java.util.List;
import utils.GlobalHelper;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author
 * This class holds all details related to contact us frame.
 */

public class ContactUsFrame extends Base{

    WebDriver driver;
	GlobalHelper helper = new GlobalHelper();
    private static org.slf4j.Logger log = LoggerFactory.getLogger(ContactUsFrame.class);

    @FindBy (xpath = "//h2[@class='intercom-zo02ra e1yjqpel2']")
    private WebElement your_conversatsion_txt;
    
    @FindBy (xpath = "//input[@placeholder='Search our articles']")
    private WebElement input_field;

    @FindBy (xpath = "//button[@class='intercom-1naovro e1r6wzsz0']")
    private WebElement submit_key;

    @FindBy (xpath = "//b[@class='intercom-35ezg3 e1rnr2bi3']")
    private WebElement search_result_title;

    @FindBy(xpath = "//div[@class='intercom-tg33l e1b3yklj1']/div[1]")
    private List<WebElement> search_result_set_title;

    public ContactUsFrame(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean validateFindYourself(String search_key) {
        boolean isSearchResultVisible = false;
        wait.until(ExpectedConditions.visibilityOf(your_conversatsion_txt));
        helper.scrollToView(driver, input_field);
        if(input_field.isDisplayed() && search_key.length() > 0 && submit_key.isDisplayed()){
            input_field.clear();
            input_field.sendKeys(search_key);
            wait.until(ExpectedConditions.elementToBeClickable(submit_key));
            submit_key.click();
            wait.until(ExpectedConditions.visibilityOf(search_result_title));
            helper.wait3Seconds();
            if(search_result_title.isDisplayed()){
                isSearchResultVisible = true;
            }
        }else{
            log.error("Input field not found!");
        }

        if(isSearchResultVisible){
            if(search_result_set_title.size() > 0){
                boolean result = false;
                helper.scrollToView(driver, search_result_set_title.get(search_result_set_title.size()-1));
                for(WebElement ele : search_result_set_title){
                    if(ele.getText().trim().length() > 0){
                        result = true;
                    }else{
                        log.error("result titile is not visible!");
                        result = false;
                        break;
                    }
                }
                if(result){
                    return true;
                }
            }
        }
        return false;
	}
}