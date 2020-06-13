package runner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
 
/***
  * @author macbook
  * {@summary runner file for testng cucumber}
  */

@CucumberOptions(
	features = {"src/main/java/features/"}, /** path of feature files*/
	glue = {"definitions"}, /** path of step definition files*/
	plugin = {"pretty", "html:target/cucumber-reports/cucumber_pretty", "json:target/cucumber-reports/joinAssemblyReport.json"},
	dryRun = false, /** to check proper mapping definition between feature and step definition */
	monochrome = true /** display the console output */
)
public class Runner {

	private TestNGCucumberRunner runner;
	
	@BeforeClass(alwaysRun = true)
	public void invoke() {       
		runner = new TestNGCucumberRunner(this.getClass());
	}
	
	@DataProvider(name = "scenario")
	public Object[][] scenario(){
		if (runner == null) {
            return new Object[0][0];
        }
		return runner.provideScenarios();
	}
	
	@Test(groups = "join-assembly", description = "Join-Assembly-Tests", dataProvider = "scenario")
	public void scenario(PickleWrapper wrapper, FeatureWrapper featureWrapper) throws Throwable {  
        runner.runScenario(wrapper.getPickle());
	}

	@AfterClass
	public void finish() {
		if (runner == null) {
            return;
        }
		runner.finish();
	}
}