package com.blim.qa.portal.search;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(JUnit4.class)
public class SearchByTitleTest extends TestCase {
	private boolean acceptNextAlert = true;
	private static ChromeDriverService service;
	private WebDriver driver;
	private WebDriverWait wait;
	private StringBuffer verificationErrors = new StringBuffer();
	private String baseUrl;
	private String username;
	private String password;

	@BeforeClass
	public static void createAndStartService() throws IOException {
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("path/to/my/chromedriver.exe"))
				.usingAnyFreePort().build();
		service.start();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		baseUrl = System.getProperty("baseUrl");
		username = System.getProperty("username");
		password = System.getProperty("password");
	}

	@Test
	public void testSearchByTitle() {
		try {
			driver.get(baseUrl + "/cuenta/ingresar");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys(username);
			driver.findElement(By.id("login-password")).clear();
			driver.findElement(By.id("login-password")).sendKeys(password);
			driver.findElement(By.cssSelector("button.bttn.bttn--primary"))
					.click();
			wait.until(ExpectedConditions.elementToBeClickable(By
					.xpath("/html/body/div/div[1]/div/nav/div[1]/settings-header/ul/li[1]/a")));
			driver.findElement(
					By.xpath("/html/body/div/div[1]/div/nav/div[1]/settings-header/ul/li[1]/a"))
					.click();

			driver.findElement(By.cssSelector(".header-searchbox__input"))
					.sendKeys("amor\n");
			driver.findElement(By.cssSelector(".header-searchbox__input"))
					.sendKeys(Keys.RETURN);
			wait.until(ExpectedConditions.elementToBeClickable(By
					.cssSelector("ng-pluralize")));
			assertTrue(isElementPresent(By.cssSelector("ng-pluralize")));
			int indice = driver
					.findElement(By.cssSelector(".searchResultsMessage"))
					.getText().indexOf("R");
			String numAssets = driver
					.findElement(By.cssSelector(".searchResultsMessage"))
					.getText().substring(0, indice - 1);
			for (int i = 1; i <= Integer.parseInt(numAssets); i++) {
				assertTrue(isElementPresent(By
						.cssSelector("div.asset-item:nth-child(" + i
								+ ") > div:nth-child(1)")));
			}
		} catch (Error e) {
			e.printStackTrace();
			verificationErrors.append(e.toString());
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			System.out.println(verificationErrors.toString());
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}