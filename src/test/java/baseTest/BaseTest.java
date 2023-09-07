package baseTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import io.restassured.RestAssured;
import utils.ExtentReportListner;
import utils.FileandEnv;

@Listeners(ExtentReportListner.class)
public class BaseTest extends ExtentReportListner{
	
	@BeforeClass
	public void baseTest() {
		
		RestAssured.baseURI = FileandEnv.envAndFile().get("ServerUrl");
	}

}
