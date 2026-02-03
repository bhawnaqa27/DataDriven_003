package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utils.AppUtil;
import utils.ExcelFileUtil;

public class AppTest extends AppUtil{//Apputil is for accessing pre and post condtions
String fileinputpath ="./DataTables/TestData.xlsx";
String fileoutputpath ="./DataTables/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;
@Test
public void startTest() throws Throwable
{
	//define html report
	report = new ExtentReports("./target/ExtentReports/adminLogin.html");
	//create refeence object for ExcelFileUtil class
	ExcelFileUtil xl = new ExcelFileUtil(fileinputpath);
	//count no of rows in LoginData sheet
	int rc = xl.rowCount("LoginData");
	Reporter.log("No of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger= report.startTest("Validate login");
		logger.assignAuthor("ranga");
		//read username and password cell
		String username = xl.getCelldata("LoginData", i, 0);
		String password = xl.getCelldata("LoginData", i, 1);
		logger.log(LogStatus.INFO,username+"-------"+ password);
		//call adminlogin method from functionlibaray
		boolean res = FunctionLibrary.admin_Login(username, password);
		if(res)
		{
			//if res is true write as pass into status cell
			xl.setCelldata("LoginData", i, 2, "Pass", fileoutputpath);
			logger.log(LogStatus.PASS, "Login Success");
		}
		else
		{
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./target/Screenshot/"+i+"  "+"Loginpage.png"));
			//if res is false write as Fail into status cell
			xl.setCelldata("LoginData", i, 2, "Fail", fileoutputpath);
			logger.log(LogStatus.FAIL, "Login Fail");
		}
		report.endTest(logger);
		report.flush();
		
	}
}
}















