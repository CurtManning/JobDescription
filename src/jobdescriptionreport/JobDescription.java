package jobdescriptionreport;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import jobdescriptionreport.ExtentFactory;
import jobdescriptionreport.HomePage;

public class JobDescription {

	private WebDriver driver;
	private String baseUrl;
	ExtentReports report;
	ExtentTest test;
	HomePage objHomePage;

	String jobDesc = "Skookum is seeking a candidate with experience in Quality Assurance (QA) Automation. If this is you, you're:\r\n"
			+ "\r\n" + "An experienced quality assurance tester (2+ years)\r\n"
			+ "Respectful of you coworkers and clients\r\n" + "A solid communicator\r\n"
			+ "Excited about the technology and process around automating tests.\r\n"
			+ "Interested in learning and growing in your career\r\n" + "\r\n" + "Things That Will Help Your Case\r\n"
			+ "\r\n" + "Experience with selenium or similar tooling for automating browser-based tests\r\n"
			+ "Work within a team developing software\r\n" + "\r\n"
			+ "This postion will be responsible for creating an automation test approach that adds value to an existing Quality Assurance process. You will design and document overarching test plans, create and execute actual test scripts, help build out test environments and validate requirements. We’re hoping that you will also help improve our QA automation efforts and collaborate with others that want to learn what you know. In short, you’ll bring some more quality to our QA, most assuredly (see what we did there??)\r\n"
			+ "\r\n"
			+ "In general, you have all the attributes of someone folks like to work with: You get things done, make (and learn from) mistakes, share knowledge, and ask for help.\r\n"
			+ "\r\n"
			+ "Skookum’s projects are team-based and client facing. We pride ourselves on treating our clients and employees like adults. And we mean the fun adults that get things done. If you think you might value these things too, come talk to us.\r\n"
			+ "\r\n"
			+ "If interested, please apply below and include your resume along with how many errors your found in this job description ;). If you have an automated test that reports these errors, well then, we’d be very impressed.";

	@BeforeClass
	public void beforeClass() {

		baseUrl = "https://www.scribens.com/";
		report = ExtentFactory.getInstance();
		test = report.startTest("Verify Job Description");
		System.setProperty("webdriver.gecko.driver",
				"/Users/CurtA/Projects/personal/libs/geckodriver-v0.19.1-win64/geckodriver.exe");
		driver = new FirefoxDriver();
		objHomePage = new HomePage(driver, test);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);

	}

	@Test
	public void ValidateSpellingTest() throws Exception {
		
		// Thread.sleep(3000);  
		Assert.assertTrue(objHomePage.clickOnCancel());
		Assert.assertTrue(objHomePage.setJobDescription(jobDesc));
		Assert.assertTrue(objHomePage.clickCheckButton());
		Assert.assertTrue(objHomePage.runGrammerChecker());
	}

	@AfterClass
	public void afterClass() {
		
		driver.quit();
		report.endTest(test);
		report.flush();
	}
}
