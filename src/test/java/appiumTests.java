import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class appiumTests {

    private static final String APP_ANDROID = "";
    private static final String APP_IOS = "";
    private static final String APPIUM = "http://localhost:4723/wd/hub";

    AppiumDriver driver;


    private void setUpAndroid() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "Appium");
        caps.setCapability("appPackage", "com.restar.scanner");
        caps.setCapability("appActivity", "host.exp.exponent.MainActivity");
        caps.setCapability("appWaitForLaunch", "false");

        //caps.setCapability("app", "C:\\Users\\Valiantsin_Dorum\\Downloads\\app-debug.apk");
        //caps.setCapability("app", APP_ANDROID);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    private void setUpIOS() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("deviceName", "iPhone X");
        //caps.setCapability("app", APP_IOS);
        driver = new IOSDriver(new URL(APPIUM), caps);
    }

    @Before
    public void setUp() throws Exception {
        setUpAndroid();
        //       setUpIOS();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public void testLoginPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 90);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-email")));

        driver.findElementByAccessibilityId("login-email").sendKeys("dalekaya.taiga@gmail.com");
        driver.findElementByAccessibilityId("login-password").sendKeys("FFFtDC1");
        driver.findElementByAccessibilityId("login-button").click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        System.out.println("Unsuccessful login with incorrect credentials");

        driver.findElementByAccessibilityId("login-password").clear();
        driver.findElementByAccessibilityId("login-password").sendKeys("FFFtDC");
        driver.findElementByAccessibilityId("login-button").click();
        MobileElement helpIcon =  (MobileElement) driver.findElementByAccessibilityId("help_icon");
        wait.until(ExpectedConditions.visibilityOf(helpIcon));
        assert helpIcon.isDisplayed();
        System.out.println("Successful login with correct credentials");

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
