package allFBSetup;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class fbSetup {

	public String baseURL = "https://www.facebook.com/";
	String validEmail = "rahulsharma20306@gmail.com";
	String validPass = "lsspl#123";
	String invalidEmail = "xyz";
	String invalidPass = "123";
	public WebDriver driver;

	@BeforeTest
	public void setup() {
		System.out.println("Test Start");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	public void login() {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(validEmail);
		driver.findElement(By.xpath("//input[@name='pass']")).sendKeys(validPass);
		driver.findElement(By.xpath("//button[@name='login']")).click();
	}

	public void logout() {
		driver.findElement(By.xpath("(//div[@role='none'])[12]")).click();
		driver.findElement(By.xpath("//span[@id=':r18:']")).click();
	}

	@Test
	public void messengeSearchBox() {
		login();
		
		//Need to handle notification popup
		
		//User clicked on Profile Picture from composer 
		driver.findElement(By.xpath("(//div[@style='inset: 0px;'])[6]")).click();
		System.out.println("Timline Opened");
		driver.findElement(By.xpath("(//div[@role='none'])[9]")).click();
		driver.findElement(By.xpath("//input[@placeholder='Search Messenger']")).sendKeys("Rahul");
		driver.findElement(By.xpath("(//span[normalize-space()='Rahul Sharma'])[5]"));
		logout();
	}
}
