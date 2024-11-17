package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import page.DashboardPage;
import page.LoginPage;
import util.BrowserFactory;
import util.ExcelReader;

public class LoginTest {
	
	WebDriver driver;
	
	ExcelReader exlRead = new ExcelReader("testData\\TF_TestData.xlsx");
	
	
	String userName = exlRead.getCellData("LoginInfo", "UserName", 2);
	String password = exlRead.getCellData("LoginInfo", "Password", 2);
	String dashboardHeaderPage = exlRead.getCellData("DashboardInfo", "DashboardHeader", 2);
	String expectedUserAlertMsg = exlRead.getCellData("LoginInfo", "alertUserValidationText", 2);
	String expectedPasswordAlertMsg = exlRead.getCellData("LoginInfo", "alertPasswordValidationText", 2);
	
	@Test
	public void validUserShouldBeAbleToLogin() {
		
		driver = BrowserFactory.init();		
		
//		LoginPage loginPage1 = new LoginPage(driver);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.insertUserName(userName);
		loginPage.insertPassword(password);
		loginPage.clickSigninButton();
		
		DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
		String actualDashboardHeader = dashboardPage.validateDashboarPage();
		Assert.assertEquals(actualDashboardHeader, dashboardHeaderPage, "Dashboard page not found!");
		
		BrowserFactory.tearDown();
		
	}
	
	@Test
	public void validateAlertMessages() {
		
		driver = BrowserFactory.init();
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.clickSigninButton();
		String userAlertMsg = loginPage.validateAlertMsg();
		Assert.assertEquals(userAlertMsg, expectedUserAlertMsg, "User alert msg doesn't match!");
		
		loginPage.insertUserName(userName);
		loginPage.clickSigninButton();
		String passwordAlertMsg = loginPage.validateAlertMsg();
		Assert.assertEquals(passwordAlertMsg, expectedPasswordAlertMsg, "Password alert msg doesn't match!");
		
		BrowserFactory.tearDown();
		
	}

}
