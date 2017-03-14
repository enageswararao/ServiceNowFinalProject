package com.servicenow.keywords;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.servicenow.config.ConstantsValue;

public class GeneralKeywords {

	WebDriver driver;
	Properties prop;

	public GeneralKeywords() {
		
 		
		System.out.println("User_Dir===>" + System.getProperty("user.dir"));
		File file = new File(System.getProperty("user.dir")
				+ File.separatorChar + "src" + File.separatorChar + "main"
				+ File.separatorChar + "java" + File.separatorChar + "com" + File.separatorChar + "servicenow"
				+ File.separatorChar + "config" + File.separatorChar + "Objects.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop = new Properties();

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean openBrowser() {
		boolean value = false;
		/*
		 * if ("firefox".equals("firefox")) {
		 * 
		 * driver = new FirefoxDriver(); value = true; } else
		 */ if ("chrome".equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:/Users/neslavath/Desktop/Cucumber/Cucumber/chromedriver_win32/chromedriver.exe");

			// Initialize browser
			driver = new ChromeDriver();
			value = true;
		} else if ("ie".equals("ie")) {

			value = true;
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return value;
	}

	public boolean openURL(String url) throws InterruptedException {
		openBrowser();
		boolean value;
		driver.get(url);
		value = true;
		waitForSeconds(1);

		return value;

	}

	public boolean verifyText(String locator, String exceptedText) throws Exception {
		boolean value;
		waitForSeconds(2);
		WebElement element = findElement(locator);
		if (element.getText().equals(exceptedText)) {
			value = true;
			System.out
					.println("actual Text display ---" + element.getText() + "===" + "ExceptedText----" + exceptedText);
		} else {
			value = false;
			System.out
					.println("actual Text display ---" + element.getText() + "===" + "ExceptedText----" + exceptedText);
		}
		return value;

	}

	public void logout(String locator) throws Exception {

		WebElement element = findElement(locator);
		element.click();

	}

	public void browserQuit() {

		driver.close();
	}

	/*********************
	 * New Code
	 */

	public boolean enterText(String locator, String inputText) throws Exception {
		boolean action = false;
		WebElement element = null;
		boolean isFrameElement = false;
		String locValue = (String) prop.get(locator);
		if (locValue.toLowerCase().startsWith("frame")) {
			isFrameElement = true;
		}
		element = findElement(locValue);
		if (element != null) {
			element.click();
			// element.clear();
			element.sendKeys(inputText);
			element.sendKeys(Keys.TAB);
			action = true;
			// element.sendKeys(Keys.TAB);
			waitForSeconds(1);
			if (isFrameElement) {
				driver.switchTo().defaultContent();
			}
		}
		return action;
	}

	public boolean click(String locator) throws Exception {
		boolean action = false;
		WebElement element = null;
		boolean isFrameElement = false;
		String locValue = (String) prop.get(locator);
		if (locValue.startsWith("frame")) {
			isFrameElement = true;
		}
		element = findElement(locValue);
		if (element != null) {
			element.click();
			waitForAjaxAndAnimation(driver);
			action = true;
			if (isFrameElement) {
				driver.switchTo().defaultContent();
			}
		}
		return action;
	}

	/***************** Utitity Function ************************/
	// ***************** nag New Code................
	public WebElement findElement(String locator) throws Exception {

		WebElement element = null;
		String locValue = locator;
		if ((!isNullOrBlank(locator)) && (locator.toLowerCase().startsWith("frame"))) {
			System.out.println("Is FrameElement==" + locator);
			String frameElementArr[] = locator.trim().split("~~~");
			if (frameElementArr.length != 2)
				throw new Exception("Please provide correct frame element :" + locator);
			// Getting Frame Element
			String frameLocArr[] = frameElementArr[0].trim().split("=");
			locValue = frameElementArr[1].trim();
			if (frameLocArr.length != 2)
				throw new Exception("Please provide correct frame element locator :" + locator);
			else {
				String frameBy = frameLocArr[0].replace("frame", "by");
				String frameLoc = frameLocArr[1];
				By byFrame = getByLocator(frameBy, frameLoc);
				WebElement frameElement = null;
				if (byFrame != null) {
					waitForSeconds(1);
					frameElement = driver.findElement(byFrame);
				} else {
					throw new NoSuchElementException("Please Check the locator:  " + locator);
				}
				driver.switchTo().frame(frameElement);
			}
		}
		String[] locValueArr = locValue.trim().split("=", 2);
		if (locValueArr.length != 2)
			throw new Exception("Please provide correct locator :" + locator);
		else {
			String byText = locValueArr[0];
			String value = locValueArr[1];
			By by = getByLocator(byText, value);
			if (by != null) {
				element = driver.findElement(by);
			} else {
				throw new NoSuchElementException("Please Check the locator:  " + locator);
			}
		}
		return element;
	}

	public By getByLocator(String byText, String value) {
		By by = null;
		if (byText.equalsIgnoreCase("byid"))
			by = By.id(value);
		else if (byText.equalsIgnoreCase("byname"))
			by = By.name(value);
		else if (byText.equalsIgnoreCase("byxpath"))
			by = By.xpath(value);
		else if (byText.equalsIgnoreCase("bycss"))
			by = By.cssSelector(value);
		else if (byText.equalsIgnoreCase("bylink"))
			by = By.linkText(value);
		else if (byText.equalsIgnoreCase("bypartiallink"))
			by = By.partialLinkText(value);
		else if (byText.equalsIgnoreCase("byclass"))
			by = By.className(value);
		else if (byText.equalsIgnoreCase("bytagname"))
			by = By.tagName(value);
		return by;
	}

	private static boolean isNullOrBlank(String s) {
		return (s == null || s.trim().equals(""));
	}

	public void waitForSeconds(long seconds) {
		long stoptime = System.currentTimeMillis() + (seconds * 1000);
		while (System.currentTimeMillis() < stoptime) {
		}
	}

	public void click(WebDriver driver, String locator) throws Exception {
		WebElement element = null;
		boolean isFrameElement = false;
		if (locator.startsWith("frame")) {
			isFrameElement = true;
		}
		element = findElement(locator);
		if (element != null) {
			element.click();
			if (isFrameElement) {
				driver.switchTo().defaultContent();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Boolean isDisplayed(WebDriver driver, By locator, Integer timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);

			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	public boolean isElementPresent(WebDriver driver, String locator, int waitTime) throws Exception {
		boolean elementPresent = false;
		boolean isFrameElement = false;
		int count = 0;
		String locValue = locator;
		if ((!isNullOrBlank(locator)) && (locator.toLowerCase().startsWith("frame"))) {
			System.out.println("Is FrameElement==" + locator);
			isFrameElement = true;
			String frameElementArr[] = locator.trim().split("~~~");
			if (frameElementArr.length != 2)
				return elementPresent;
			// Getting Frame Element
			String frameLocArr[] = frameElementArr[0].trim().split("=");
			locValue = frameElementArr[1].trim();
			if (frameLocArr.length != 2)
				return elementPresent;
			else {
				String frameBy = frameLocArr[0].replace("frame", "by");
				String frameLoc = frameLocArr[1];
				By byFrame = getByLocator(frameBy, frameLoc);
				WebElement frameElement = null;
				if (byFrame != null) {
					while (count < waitTime) {
						System.out.println("Frame element size: " + driver.findElements(byFrame).size());
						if (!(driver.findElements(byFrame).size() > 0)) {
							waitForSeconds(1);
							count += 2;
						} else if (driver.findElements(byFrame).size() > 0) {
							count += waitTime;
						}
					}
					frameElement = driver.findElement(byFrame);
					if (frameElement == null) {
						return elementPresent;
					}
				} else {
					return elementPresent;
				}
				driver.switchTo().frame(frameElement);
			}
		}
		count = 0;
		String[] locValueArr = locValue.trim().split("=", 2);
		if (locValueArr.length != 2)
			return elementPresent;
		else {
			String byText = locValueArr[0];
			String value = locValueArr[1];
			By by = getByLocator(byText, value);
			if (by != null) {
				while (count < waitTime) {
					System.out.println("By element size: " + driver.findElements(by).size());
					if (!(driver.findElements(by).size() > 0)) {
						waitForSeconds(1);
						count++;
					} else if (driver.findElements(by).size() > 0) {
						elementPresent = true;
						System.out.println("elementPresent===> " + elementPresent);
						count += waitTime;
						if (isFrameElement) {
							driver.switchTo().defaultContent();
						}
					}
				}
			} else {
				return elementPresent;
			}
		}
		return elementPresent;
	}

	public String getPropertyValue(String key) {
		String value = null;
		File file = new File(
				"C:\\Users\\neslavath\\Desktop\\Cucumber\\Cucumber\\src\\main\\java\\com\\service\\config\\Objects.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();

		// load properties file
		try {
			prop.load(fileInput);
			value = (String) prop.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;

	}

	public String takeScreenShot(String stepName) {
		File scrFile = null;
		try {
			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// The below method will save the screen shot in d drive with name
			// "screenshot.png"
			FileUtils.copyFile(scrFile, new File(ConstantsValue.filepathScreenshot + stepName + ".png"));
		} catch (Exception exception) {
			exception.getMessage();
		}
		return scrFile.toString();
	}

	/**
	 * Wait for animated element to complete loading
	 * 
	 * @param driver
	 */
	public void waitForAnimation(WebDriver driver) {
		int iTimeout = 10;
		new WebDriverWait(driver, iTimeout) {
		}.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driverObject) {
				waitForSeconds(2);
				Long returnedValue = (Long) ((JavascriptExecutor) driverObject)
						.executeScript("return jQuery(':animated').length");
				return returnedValue == 0;
			}
		});
	}

	/**
	 * Wait for Ajax calls to complete and Animated element to load
	 * 
	 * @param driver
	 */
	public void waitForAjaxAndAnimation(WebDriver driver) {
		if (isJQueryDefined(driver)) {
			waitForAjax(driver);
			waitForAnimation(driver);
		}
	}

	public boolean isJQueryDefined(WebDriver driver) {
		return (Boolean) ((JavascriptExecutor) driver).executeScript("return typeof jQuery == 'function'");
	}

	/**
	 * Wait for ajax element to load
	 * 
	 * @param driver
	 */
	public void waitForAjax(WebDriver driver) {
		int iTimeout = 10;
		new WebDriverWait(driver, iTimeout) {
		}.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driverObject) {
				waitForSeconds(2);
				Long returnedValue = (Long) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active");
				return returnedValue == 0;
			}

		});
	}

}
