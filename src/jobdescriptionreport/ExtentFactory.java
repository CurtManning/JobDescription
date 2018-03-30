package jobdescriptionreport;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	public static ExtentReports getInstance() {
		ExtentReports extent;
		String Path = "/Users//CurtA//Desktop//job-description-report.html";
		extent = new ExtentReports(Path, false);
		extent
	     .addSystemInfo("Selenium Version", "3.53")
	     .addSystemInfo("Platform", "Windows");
 
		return extent;
	}
}