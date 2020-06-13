package config;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Base {
	
	public WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait = null;
	
	public Base() {
		FileInputStream ip;
		String fileName = "./resources/app.properties";
		try {
			prop = new Properties();
			ip = new FileInputStream(fileName);
			prop.load(ip);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();		
		}
	}

	public WebDriver startBrowser() {
		if(driver == null) {
			String browserName = prop.getProperty("browser");
			if(browserName.equals("chrome")) {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(Platform.LINUX);
				System.setProperty("webdriver.chrome.driver", "./resources/browserdrivers/chromedriver");
				ChromeOptions option = new ChromeOptions();
				option.setHeadless(false);
				option.addArguments("disable-infobars");
				driver = new ChromeDriver(option);
			}
			
			driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT,TimeUnit.SECONDS) ;
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 120);
		}
		return driver;
	}

	@BeforeClass(alwaysRun = true)
	public void preTest()  {
		Reporter.log("========Browser Session Started========", true);
		startBrowser();
		driver.get(prop.getProperty("baseurl"));
	}
	
	@AfterClass(alwaysRun = true)
	public void postTest() {
		driver.quit();
		Reporter.log("========Browser Session End========", true);
	}
}
