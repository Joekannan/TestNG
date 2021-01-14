package testNGMethods;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParameterTestLevel {
	
	public WebDriver driver;
		
		@Test(description="This test is to set up the browser", priority = -10)
		public void setUp() {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://automationpractice.com/index.php?");
		}
		@Parameters({"username","password"})
		@Test(description = "This test is to login", priority = -1)
		public void verifyLogin(String uname, String pwd) {
			driver.findElement(By.linkText("Sign in")).click();
			driver.findElement(By.id("email")).sendKeys(uname);
			driver.findElement(By.id("passwd")).sendKeys(pwd);
			driver.findElement(By.id("SubmitLogin")).click();
			if(driver.findElement(By.className("account")).isDisplayed()) {
				String name = driver.findElement(By.xpath(".//a[@class='account']/span")).getText();
				Assert.assertEquals(name, "sample test");
			}
		}
		
		@Test(description = "This test is to logout", priority = 3)
		public void verifyLogout() {
			if(driver.findElement(By.linkText("Sign out")).isDisplayed()) {
				driver.findElement(By.linkText("Sign out")).click();
				String text = driver.findElement(By.className("page-heading")).getText();
				Assert.assertEquals(text,"AUTHENTICATION");
			}
			
		}
		
		@Test(description = "This test is to tidy up the driver", dependsOnMethods = {"setUp"}, priority = 40)
		public void tearDown() {
			driver.close();
			driver.quit();
		}	

}
