package OrangeHRMs;

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

public class OrangeHRMSetup {

	public String baseURL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;
	String empid = "123";

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
		driver.findElement(By.xpath("//input[@name = 'username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type = 'submit']")).click();
		String pageTitle = driver.getTitle();
//		if (pageTitle.equals("OrangeHRM")) {
//			System.out.println("Login Successfull");
//		} else {
//			System.out.println("Login Fail");
//		}
		Assert.assertEquals("OrangeHRM", pageTitle);
	}

	public void logout() {
		driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
		List<WebElement> elementlist = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		elementlist.get(3).click();
	}

	@Test(priority = 1)
	public void invalidLoginTest() {
		driver.findElement(By.xpath("//input[@name = 'username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin1234");
		driver.findElement(By.xpath("//button[@type = 'submit']")).click();
		String ExpectedError = "Invalid credentials";
		String Actualopt = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']"))
				.getText();
		Assert.assertEquals(ExpectedError, Actualopt);
		// Assert.assertTrue(Actualopt.contains(ExpectedError));
	}

	@Test(priority = 2)
	public void addNewMember() throws InterruptedException, IOException {
		login();
		driver.findElement(By.xpath("(//span[normalize-space()='PIM'])[1]")).click();
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("Lss");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Sharma");
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-plus']")).click();
		Thread.sleep(5000);
		// Profile Photo Update
		Runtime.getRuntime().exec("C://Users//This PC//eclipse-workspace//SeleniumProject//profileUpload.exe");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		String addemp = driver.findElement(By.xpath("//h6[text()='Personal Details']")).getText();
		logout();
		Assert.assertEquals("Personal Details", addemp);
	}

	@Test
	public void searchEmp() {
		login();
		driver.findElement(
				By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']"))
				.click();
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		driver.findElements(By.xpath("//input[@placeholder='Type for hints...']")).get(0).sendKeys("Rahul");
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		List<WebElement> emlSearch = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));
		String Searched_Actual = emlSearch.get(0).getText();
		String Searched_Expectec = "Records Found";
		Assert.assertTrue(Searched_Actual.contains(Searched_Expectec));
		logout();
		// for(int i=0;i<emlSearch.size();i++)
//		{
//			System.out.println("All element"+ i + "index are "+emlSearch.get(i).getText());
//		}
	}

	@Test
	public void searchEmpByID() {
		login();
		driver.findElement(
				By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']"))
				.click();
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		driver.findElements(By.tagName("input")).get(2).sendKeys(empid);
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		logout();

	}

	@Test
	public void fileUpload() throws IOException, InterruptedException {
		login();
		driver.findElement(
				By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']"))
				.click();
		// Click on configuration tab
		driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();
		// Click on Data Import
		driver.findElement(By.partialLinkText("Data Impo")).click();
		driver.findElement(By.xpath("//div[@class='oxd-file-button']")).click();
		Runtime.getRuntime().exec("C://Users//This PC//eclipse-workspace//SeleniumProject//FileUploadAutoIT.exe");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@type='submit']"));
		logout();
	}

	@AfterTest
	public void teardown() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();

	}
}