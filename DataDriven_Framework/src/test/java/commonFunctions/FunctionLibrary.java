package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import utils.AppUtil;

public class FunctionLibrary extends AppUtil{
//method for login
	public static boolean admin_Login(String user,String pass)throws Throwable
	{
		driver.get(conpro.getProperty("Url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement username = driver.findElement(By.xpath(conpro.getProperty("ObjUser")));
		username.clear();
		username.sendKeys(user);
		WebElement password = driver.findElement(By.xpath(conpro.getProperty("Objpass")));
		password.clear();
		password.sendKeys(pass);
		driver.findElement(By.xpath(conpro.getProperty("ObjLogin"))).click();
		Thread.sleep(2000);
		String Expected ="dashboard";
		String Actual = driver.getCurrentUrl();
		if(Actual.contains(Expected))
		{
			//click logout link
			driver.findElement(By.xpath(conpro.getProperty("ObjLogout"))).click();
			Thread.sleep(2000);
			Reporter.log("Login Success",true);
			return true;
			
		}
		else
		{
			Thread.sleep(1000);
			String error_mess = driver.findElement(By.xpath(conpro.getProperty("ObjAlertMessage"))).getText();
			driver.findElement(By.xpath(conpro.getProperty("ObjOkbtn"))).click();
			Thread.sleep(1000);
			Reporter.log(error_mess,true);
			return false;
			
		}
		
		
	}
}
