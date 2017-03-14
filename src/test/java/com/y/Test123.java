package com.y;

 
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class Test123 {

	WebDriver driver;
	@Test
	public void test_FirefoxDockerGridNavigatesToLoginPage() throws Exception  {
	//System.setProperty("webdriver.gecko.driver","C:\\Users\\neslavath\\Downloads\\geckodriver-v0.14.0-win64\\geckodriver.exe");
	   DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	   capabilities.setCapability("webdriver.gecko.driver","C:\\Users\\neslavath\\Downloads\\geckodriver-v0.14.0-win64\\geckodriver.exe");
	   capabilities.setBrowserName("firefox");
	   capabilities.setPlatform(Platform.WINDOWS);
	 //  URL url = new URL( "http", "localhost", 4444, "/wd/hub" );
	   driver = new RemoteWebDriver(new URL("http://192.168.99.100:32769/wd/hub"), capabilities);
	   driver.get("http://the-internet.herokuapp.com/login");
	   //assertThat(driver.getTitle(), is(equalTo("The Internet")));


	}


}
