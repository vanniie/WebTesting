package com.utility;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.MLShopPageObjects.MLShopProfileLoginPage;
import com.driverInstance.DriverInstance;
import com.driverInstance.DriverManager;

import io.appium.java_client.*;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import com.google.common.collect.Ordering;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;

import java.time.Duration;

import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.io.File;
import java.util.UUID;


import static com.business.mlshop.BaseClass.prop;
import static com.driverInstance.DriverInstance.tlWebDriver;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

public class Utilities extends ExtentReporter {

    public static SoftAssert softAssert = new SoftAssert();
    @SuppressWarnings("rawtypes")
    public static TouchAction touchAction;
    public static boolean relaunch = true;
    public static String CTUserName;
    public static String CTPWD;
    public static String setPlatform = "";
    public static double amount;
    public static JavascriptExecutor js;
    public static Actions action;
    /**
     * The Constant logger.
     */
//	final static Logger logger = Logger.getLogger("rootLogger");
    static LoggingUtils logger = new LoggingUtils();
    /**
     * window handler
     */
    static ArrayList<String> win = new ArrayList<>();
    static WebDriverWait wait;
    /**
     * Time out
     */
    private static int timeout;
    /**
     * Retry Count
     */
    private static int retryCount;
    /**
     * The Android driver.
     */
    public AndroidDriver<AndroidElement> androidDriver;
    /**
     * The Android driver.
     */
    public IOSDriver<WebElement> iOSDriver;

    /*
     * public static void setTimeout(int timeout) { this.timeout = timeout; }
     */

    public Utilities() {
    }

    public static int getTimeout() {
        return 30;
    }

    public static WebDriver getWebDriver() {
        return tlWebDriver.get();
    }

    /*
     * public static void setTimeout(int timeout) { this.timeout = timeout; }
     */

    public static int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public static AppiumDriver<WebElement> getDriver() {
        return DriverInstance.tlDriver.get();
    }

    public static String getPlatform() {
        return DriverInstance.getPlatform();
    }

    public void setPlatform(String Platform) {
        DriverInstance.setPlatfrom(Platform);
    }

    public static void initDriver() {
        if (getPlatform().equals("Web")) {
            wait = new WebDriverWait(getWebDriver(), 600);
            js = (JavascriptExecutor) getWebDriver();
            action = new Actions(getWebDriver());
            getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } else if (getPlatform().equals("Android") || getPlatform().equals("MPWA") || getPlatform().equals("iOS")|| getPlatform().equals("BrowserStack") || getPlatform().equals("IOSBrowserStack")) {
            wait = new WebDriverWait(DriverManager.getAppiumDriver(), 600);
            js = (JavascriptExecutor) DriverManager.getAppiumDriver();
            action = new Actions(DriverManager.getAppiumDriver());
            DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    public static void javaScriptExecutor() {
        if (getPlatform().equals("Web")) {
            JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        } else if (getPlatform().equals("Android") || getPlatform().equals("MPWA") || getPlatform().equals("TV")) {
        }
    }

    public static void setScreenshotSource() {
        if (getPlatformFromtools().equalsIgnoreCase("Web")) {
            src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        } else if (getPlatformFromtools().equals("Android") || getPlatformFromtools().equals("PWA") || getPlatformFromtools().equals("TV")) {
            src = ((TakesScreenshot) DriverManager.getAppiumDriver())
                    .getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        } else if (getPlatformFromtools().equalsIgnoreCase("MPWA")) {
            src = ((TakesScreenshot) DriverManager.getAppiumDriver())
                    .getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        }
    }

    public static void JSClick(By byLocator, String text) throws Exception {
        try {
            js = (JavascriptExecutor) getWebDriver();
            js.executeScript("arguments[0].click();", getWebDriver().findElement(byLocator));
            logger.info("" + text + " " + " is clicked");
            ExtentReporter.extentLoggerPass("checkElementPresent", "" + text + " is clicked");
            //return true;
        } catch (Exception e) {
            logger.error(text + " " + " is not clicked");
            ExtentReporter.extentLoggerFail("checkElementNotPresent", "" + text + " is not clicked\n" + e);
            ExtentReporter.screencapture();
            throw new AssertionError("Unable to click an element " + byLocator, e);
//			e.printStackTrace();
            //return false;
        }
    }

    public static WebElement findElement(By byLocator) throws Exception {
        WebElement element = null;
        if (getPlatform().equals("Web")) {
            element = getWebDriver().findElement(byLocator);
            return element;
        } else if (getPlatform().equals("Android") || getPlatform().equals("BrowserStack") || getPlatform().equals("iOS")) {
            element = DriverManager.getAppiumDriver().findElement(byLocator);
            return element;
        }
        return element;
    }

    public void setWifiConnectionToONOFF(String Value) {
        try {
            if (Value.equalsIgnoreCase("On")) {
                System.out.println("Switching On Wifi");
                String cmd = "adb shell svc wifi enable";
                Runtime.getRuntime().exec(cmd);
                waitTime(5000);
                logger.info("Wifi Data toggle is Switched On");
                ExtentReporter.extentLoggerPass("Wifi Toggle", "Wifi Data toggle is Switched On");
            } else if (Value.equalsIgnoreCase("Off")) {
                System.out.println("Switching Off Wifi");
                String cmd = "adb shell svc wifi disable";
                Runtime.getRuntime().exec(cmd);
                waitTime(3000);
                logger.info("Wifi Data toggle is Switched Off");
                ExtentReporter.extentLoggerPass("Wifi Toggle", "Wifi Data toggle is Switched Off");
            }
        } catch (Exception e) {
            //			logger.error(e);
        }
    }

    /**
     * wait until element is displayed.
     *
     * @param :
     * @return true, if successful
     */
    public static boolean waitForElementDisplayed(By byLocator, int iTimeOut) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Check element not present.
     *
     * @param : byLocator the by locator
     * @return true, if successful
     */
    public static boolean verifyElementNotPresent(By byLocator, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), iTimeOut);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    /*
     * For avoiding the stale element expception
     */
    public static boolean waitforelementtoappear(By locator, int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), time);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(locator));
        return true;
    }

    public static List<WebElement> staleexception_Click(By locator) {
        List<WebElement> outcome = null;
        int repeat = 0;
        while (repeat <= 6) {
            try {
                List<WebElement> ele = DriverManager.getDriver().findElements(locator);

                break;
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
            repeat++;
        }
        return outcome;
    }

    /**
     * Check element present.
     *
     * @param : byLocator the by locator
     * @return true, if successful
     */
    public static boolean verifyElementPresentOld(By byLocator, String validationtext) throws Exception {
        try {
            isPresentWithWait(byLocator);
            logger.info(validationtext + " is displayed");
            ExtentReporter.extentLoggerPass("checkElementPresent", validationtext + " is displayed");
            return true;
        } catch (Exception e) {
            logger.error(validationtext + " is not displayed");
            ExtentReporter.extentLoggerFail("checkElementPresent", validationtext + " is not displayed\n" + e);
            throw new AssertionError("check Element Present " + validationtext + " is not displayed", e);
            //return false;
        }
    }

    public static boolean verifyElementPresent(By byLocator, String validationtext) throws Exception {

        if (isPresentWithWait(byLocator)) {
            WebElement element = findElement(byLocator);
            softAssert.assertEquals(element.isDisplayed(), true, "" + validationtext + " " + " is displayed");
            logger.info(validationtext + " is displayed");
            ExtentReporter.extentLoggerPass("checkElementPresent", validationtext + " is displayed");
            return true;
        } else {
            softAssert.assertEquals(false, true, validationtext + " " + " is not displayed");
            logger.info(validationtext + " is not displayed");
            screencapture();
            return false;
        }
    }

    public static boolean verifyElementExist(By byLocator, String str) throws Exception {
        WebElement element;
        try {
            if (getPlatform().equalsIgnoreCase("web")) {
                element = getWebDriver().findElement(byLocator);
                explicitWaitVisibility(byLocator, 20);
            } else {
                element = DriverManager.getAppiumDriver().findElement(byLocator);
                explicitWaitVisibility(byLocator, 20);
            }
            if (element.isDisplayed()) {
                softAssert.assertEquals(element.isDisplayed(), true, "" + str + " " + " is displayed");
                ExtentReporter.extentLoggerPass("checkElementPresent", "" + str + " is displayed");
                logger.info("" + str + " is displayed");
                return true;
            }
        } catch (Exception e) {
            softAssert.assertEquals(false, true, str + " " + " is not displayed");
            ExtentReporter.extentLoggerFail("checkElementPresent", "" + str + " is not displayed\n" + e);
            logger.error("\u001B[31m" + str + " is not displayed" + "\u001B[0m");
            //return false;
            throw new AssertionError("Element does not exist " + byLocator, e);
        }
        return false;
    }

    /**
     * boolean return type for conditions
     *
     * @param byLocator
     * @return
     * @throws Exception
     */
    public static boolean verifyElementDisplayed(By byLocator,String validationtext) throws Exception {
        //String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
        if (platform.equalsIgnoreCase("web")) {
            try {
                WebElement element = DriverManager.getDriver().findElement(byLocator);
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
            }
        } else if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("IOSBrowserStack")) {
            try {
                WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
            }
        } else if (platform.equalsIgnoreCase("iOS")) {
            try {
                WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
                if (element.isDisplayed()) {
                    logger.info("" + validationtext + " " + "is displayed");
                    ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is displayed");
                    return true;
                }
            } catch (Exception e) {
            }
        }
        logger.info("" + validationtext + " " + "is not displayed");
        ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is not displayed");
        return false;
    }

    public static boolean checkElementExist(By byLocator, String str) throws Exception {

        try {
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            List<WebElement> list = getDriver().findElements(byLocator);
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            if (list.size() == 0) {
                ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
                logger.info("" + str + " is not displayed");
                return false;
            } else {
                ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
                logger.info("" + str + " is displayed");
                return list.get(0).isDisplayed();
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check element present and click.
     *
     * @param byLocator the by locator
     * @return true, if successful
     */
    public static boolean verifyElementPresentAndClick(By byLocator, String validationtext) throws Exception {
        WebElement element = null;
        try {
            if (getPlatform().equalsIgnoreCase("Web")) {
                element = getWebDriver().findElement(byLocator);
            } else if (getPlatform().equalsIgnoreCase("Android") || getPlatform().equalsIgnoreCase("BrowserStack") || getPlatform().equalsIgnoreCase("IOSBrowserStack")) {
                element = DriverManager.getAppiumDriver().findElement(byLocator);
            } else if (getPlatform().equalsIgnoreCase("iOS")) {
                element = DriverManager.getAppiumDriver().findElement(byLocator);
            }
            softAssert.assertEquals(element.isDisplayed(), true, "" + validationtext + " " + " is displayed");
            logger.info("" + validationtext + " " + "is displayed");
            ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is displayed");
            if (getPlatform().equalsIgnoreCase("Web")) {
                getWebDriver().findElement(byLocator).click();
            } else if (getPlatform().equalsIgnoreCase("Android") || getPlatform().equalsIgnoreCase("BrowserStack") || getPlatform().equalsIgnoreCase("IOSBrowserStack")) {
                DriverManager.getAppiumDriver().findElement(byLocator).click();
            } else if (getPlatform().equalsIgnoreCase("iOS")) {
                DriverManager.getAppiumDriver().findElement(byLocator).click();
            }
            logger.info("Clicked on " + validationtext);
            ExtentReporter.extentLoggerPass("click", "Clicked on " + validationtext);
            return true;
        } catch (Exception e) {
            softAssert.assertEquals(false, true, "Element" + validationtext + " " + " is not visible");
            logger.error("Element " + validationtext + " " + "is not visible");
            ExtentReporter.extentLoggerFail("checkElementPresent", "" + validationtext + " is not displayed\n" + e);
            throw new AssertionError("Element not present", e);
//            return false;
        }
    }

    public static String getAdId() throws IOException {
        String cmd = "adb shell grep adid_key /data/data/com.google.android.gms/shared_prefs/adid_settings.xml";
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String strBuffer = br.readLine().trim();
        String[] getadid = strBuffer.split(">")[1].split("<");
        System.out.println("\nAdID: " + getadid[0]);
        return getadid[0];
    }

    /**
     * @param : byLocator
     * @return true or false
     */
    public static boolean checkcondition(By byLocator) throws Exception {
        boolean iselementPresent = false;
        try {
            iselementPresent = getDriver().findElement(byLocator).isDisplayed();
            iselementPresent = true;
        } catch (Exception e) {
            iselementPresent = false;
        }
        return iselementPresent;
    }

    /**
     * Click on a web element.
     *
     * @param : byLocator the by locator
     */
    public static void click(By byLocator, String validationtext) throws Exception {
        //String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
        try {
            if (platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("IOSBrowserStack")) {
                AndroidElement element = (AndroidElement) DriverManager.getAppiumDriver().findElement(byLocator);
                element.click();
            } else if (platform.equalsIgnoreCase("mpwa")) {
                WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
                element.click();
            } else if (platform.equalsIgnoreCase("iOS")) {
                WebElement element = findElement(byLocator);
                element.click();
            } else if (platform.equalsIgnoreCase("web")) {
                WebElement element = getWebDriver().findElement(byLocator);
                element.click();
            }
            logger.info("Clicked on " + validationtext);
            ExtentReporter.extentLoggerPass("click", "Clicked on " + validationtext);
        } catch (Exception e) {
            screencapture();
            logger.info("Not clicked on " + validationtext);
            ExtentReporter.extentLoggerFail("click", "Not Clicked on " + validationtext + e);
            throw new AssertionError("Unable to click element " + byLocator, e);
        }
    }

    public static void DoubleClick(By locator, String OptionName) {
        try {

            Actions act = new Actions(DriverManager.getAppiumDriver());
            WebElement ele = DriverManager.getAppiumDriver().findElement(locator);
            act.doubleClick(ele).perform();
            logger.info("Clicked on the " + OptionName);
            ExtentReporter.extentLogger("Click", " " + OptionName);
        } catch (NoSuchElementException e) {
            logger.info("Did not click on " + OptionName);
            ExtentReporter.extentLogger("Did not Click on", " " + OptionName);
        }
    }

    public static boolean verifyElementNotPresentForWeb(By byLocator, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(getWebDriver(), iTimeOut);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    /**
     * clear the text field
     *
     * @param byLocator
     */
    public static void clearField(By byLocator, String text) {
        try {
            DriverManager.getAppiumDriver().findElement(byLocator).clear();
            logger.info("Cleared the text in : " + text);
            ExtentReporter.extentLogger("clear text", "Cleared the text in : " + text);
        } catch (Exception e) {
//			logger.error(e);
        }
    }

    /**
     * Get Text from an object
     *
     * @param byLocator
     * @return
     * @throws Exception
     */
    public static String getText(By byLocator) throws Exception {
        String platform = null;
        if (getPlatform().equalsIgnoreCase("web")) {
            platform = "web";
        } else {
            platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
        }
        String value = null;
        if (platform.equalsIgnoreCase("web")) {
            try{
            WebElement element = findElement(byLocator);
            value = element.getText();}
            catch(Exception e){
                ExtentReporter.extentLoggerFail("Fail","Could not get text " + byLocator);
                throw new AssertionError("Could not get text " + byLocator, e);
            }
        } else if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("iOS")) {
            WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
            value = element.getText();
        }
        return value;
    }

	@SuppressWarnings({ "rawtypes" })
	public String OTPNotification() throws Exception {
		ExtentReporter.HeaderChildNode("Fetching Otp From Notification");
		waitTime(2000);
		getDriver().context("NATIVE_APP");
		//		touchAction = new TouchAction(getDriver());
		//		touchAction.press(PointOption.point(500, 0))
		//		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(4000)))
		//		.moveTo(PointOption.point(1500, 500)).release().perform();
		//		waitTime(8000);
		AndroidDriver driver = (AndroidDriver) getDriver();
		driver.openNotifications();
		waitTime(3000);
		List<WebElement> allnotifications = getDriver().findElements(By.xpath("(//*[@resource-id='android:id/message_text'])[1]"));
		System.out.println("Size : " + allnotifications.size());
		String Otp = null;
		for (WebElement webElement : allnotifications) {
			Otp = webElement.getText();
			System.out.println("Get Text : " + webElement.getText());
			if (webElement.getText().contains("something")) {
				System.out.println("Notification found");
				break;
			}
		}
		Back(1);
		getDriver().context("WEBVIEW_1");
		return Otp;
	}

    public boolean verifyElementIsNotDisplayed(By by) {
        try {
            getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return getDriver().findElements(by).isEmpty();
        } catch (Exception e) {
            getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return false;
        }
    }

    public static boolean verifyIsElementDisplayed(By by) {
        List<WebElement> list = null;
        if (getPlatform().equals("Web")) {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            list = DriverManager.getDriver().findElements(by);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } else {
            DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            list = DriverManager.getAppiumDriver().findElements(by);
            DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        if (list.size() == 0) {
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    public static boolean verifyIsElementDisplayed(By by, String validationtext) {
        List<WebElement> list = null;
        if (getPlatform().equals("Web")) {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            list = DriverManager.getDriver().findElements(by);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } else {
            getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            list = getDriver().findElements(by);
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        if (list.size() == 0) {
            logger.info("Element " + validationtext + " " + " is not displayed");
            ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is not displayed");
            return false;
        } else {
            logger.info("" + validationtext + " " + "is displayed");
            ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is displayed");
            return list.get(0).isDisplayed();
        }
    }
    public static String getTextVal(By byLocator, String concatValue) throws Exception {
        String Value = null;
        WebElement element = getWebDriver().findElement(byLocator);
        Value = element.getText();
        String finalValue = Value + " " + concatValue;
        return finalValue;
    }

    public static boolean checkElementExist(By byLocator) throws Exception {
        try {
            WebElement element = DriverManager.getDriver().findElement(byLocator);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Kill or start an application using activity
     *
     * @param : command  to START or KILL an application
     * @param : activity Start an application by passing the activity
     */
    public void adbStartKill(String command, String activity) {
        String cmd;
        try {
            if (command.equalsIgnoreCase("START")) {
                cmd = "adb shell am start -n" + " " + activity;
                Runtime.getRuntime().exec(cmd);
                logger.info("Started the activity" + cmd);
                ExtentReporter.extentLogger("adbStart", "Started the activity" + cmd);
            } else if (command.equalsIgnoreCase("KILL")) {
                cmd = "adb shell am force-stop" + " " + activity;
                Runtime.getRuntime().exec(cmd);
                logger.info("Executed the App switch");
                ExtentReporter.extentLogger("adbKill", "Executed the App switch");
            }
        } catch (Exception e) {
            //			logger.error(e);
        }
    }

    /**
     * @return true if keyboard is displayed
     * @throws IOException
     */
    public boolean checkKeyboardDisplayed() throws IOException {
        boolean mInputShown = false;
        try {
            String cmd = "adb shell dumpsys input_method | grep mInputShown";
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String outputText = "";
            while ((outputText = br.readLine()) != null) {
                if (!outputText.trim().equals("")) {
                    String[] output = outputText.split(" ");
                    String[] value = output[output.length - 1].split("=");
                    String keyFlag = value[1];
                    if (keyFlag.equalsIgnoreCase("True")) {
                        mInputShown = true;
                    }
                }
            }
            br.close();
            p.waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
        return mInputShown;
    }

    /**
     * Closes the Keyboard
     */
    public static void hideKeyboard() {
        try {
            if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("BrowserStack")
                    || platform.equalsIgnoreCase("IOSBrowserStack")) {
                DriverManager.getAppiumDriver().hideKeyboard();
            } else {
                getDriver().hideKeyboard();
            }
            logger.info("Hiding keyboard was Successfull");
            ExtentReporter.extentLogger("hideKeyboard", "Hiding keyboard was Successfull");
        } catch (Exception e) {
        }
    }

    /**
     * Type on a web element.
     *
     * @param byLocator the by locator
     */
    public static void type(By byLocator, String input, String FieldName) throws Exception {
        try {
            //String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
            if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("BrowserStack")
                    || platform.equalsIgnoreCase("IOSBrowserStack")|| platform.equalsIgnoreCase("iOS")) {
                WebElement ele = DriverManager.getAppiumDriver().findElement(byLocator);
                ele.sendKeys(input);
            } else if (platform.equalsIgnoreCase("mpwa")) {
                Actions a = new Actions(DriverManager.getAppiumDriver());
                a.sendKeys(input);
                a.perform();
            } else if (platform.equalsIgnoreCase("Web")) {
                Actions a = new Actions(getWebDriver());
                a.sendKeys(input);
                a.perform();
            }
            input = input.split("\n")[0];
            logger.info("Typed the value " + input + " into " + FieldName);
            ExtentReporter.extentLoggerPass("", "Typed the value " + input + " into " + FieldName);
        } catch (Exception e) {
            softAssert.assertEquals(true, false, FieldName + " Not Typed the value");
            logger.info("Not Typed the value " + input + " into " + FieldName);
            ExtentReporter.extentLoggerFail("", "Not Typed the value " + input + " into " + FieldName + e);
            throw new AssertionError("Could not send text " + byLocator, e);
        }
    }

    public void enter(By byLocator, String input, String FieldName) {
        try {
            waitTime(1000);
            if (!getPlatform().equals("Web")) {
                Actions a = new Actions(getDriver());
                a.sendKeys(input);
                a.perform();
            } else {
                WebElement element = DriverManager.getDriver().findElement(byLocator);
                element.sendKeys(input);
            }
            input = input.split("\n")[0];
            logger.info("Typed the value into " + FieldName);
        } catch (Exception e) {
            softAssert.assertEquals(true, false);
            //			logger.error(e);
        }
    }

    /**
     * Wait
     *
     * @param : x seconds to lock
     */
    public static void Wait(int x) {
        try {
            getDriver().manage().timeouts().implicitlyWait(x, TimeUnit.SECONDS);
        } catch (Exception e) {
//			logger.error(e);
        }
    }

    public static void refresh() {
        try {
            getDriver().navigate().refresh();
        } catch (Exception e) {
//			logger.error(e);
        }
    }

    public static void waitTime(int x) {
        try {
            Thread.sleep(x);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * @param keyevent pass the android key event value to perform specific action
     */
    public void adbKeyevents(int keyevent) {
        try {
            String cmd = "adb shell input keyevent" + " " + keyevent;
            Runtime.getRuntime().exec(cmd);
            logger.info("Performed the Keyevent" + keyevent);
            ExtentReporter.extentLogger("adbKeyevent", "Performed the Keyevent" + keyevent);
        } catch (Exception e) {
//			logger.error(e);
        }
    }

    /**
     * @param byLocator
     * @returns the list count of the element
     */
    public static int getCount(By byLocator) {

        int count = 0;
        try {
            if (getPlatform().equalsIgnoreCase("web")) {
                count = getWebDriver().findElements(byLocator).size();
                logger.info("List count for" + " " + byLocator + " " + "is" + " " + count);
            } else {
                count = getDriver().findElements(byLocator).size();
                logger.info("List count for" + " " + byLocator + " " + "is" + " " + count);
                ExtentReporter.extentLogger("getCount", "List count for" + " " + byLocator + " " + "is" + " " + count);
            }
        } catch (Exception e) {
//			logger.error(e);
        }
        return count;
    }

    public static List<WebElement> findElements(By byLocator) {
        if (getPlatform().equalsIgnoreCase("web")) {
            platform = "web";
        }
        if (platform.equalsIgnoreCase("web")) {
            return getWebDriver().findElements(byLocator);
        } else if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("iOS") ) {
            return DriverManager.getAppiumDriver().findElements(byLocator);
        }
        return null;
    }

    /**
     * @param byLocator
     * @returns the By locator
     */
    public String iterativeXpathtoStringGenerator(int temp, By byLocator, String property) {

        WebElement element = null;
        String drug = null;
        try {

            String xpath = byLocator.toString();
            String var = "'" + temp + "'";
            xpath = xpath.replaceAll("__placeholder", var);
            String[] test = xpath.split(": ");
            xpath = test[1];
            element = getDriver().findElement(By.xpath(xpath));
            drug = element.getAttribute(property);
        } catch (Exception e) {
//			System.out.println(e);
        }
        return drug;
    }

    /**
     * Back
     *
     * @throws Exception
     */
    public static void Back(int x) throws Exception {

        try {
            if (getPlatform().equals("Web")) {
                for (int i = 0; i < x; i++) {
                    DriverManager.getDriver().navigate().back();
                    logger.info("Back button is tapped");
                    ExtentReporter.extentLogger("Back", "Back button is tapped");
                }
            } else if (getPlatform().equals("Android") || getPlatform().equals("MPWA")
                    || platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("IOSBrowserStack")) {
                for (int i = 0; i < x; i++) {
                    DriverManager.getAppiumDriver().navigate().back();
                    logger.info("Back button is tapped");
                    ExtentReporter.extentLogger("Back", "Back button is tapped");
                    waitTime(6000);
                }
            }
        } catch (Exception e) {
//			logger.error(e);
            ExtentReporter.screencapture();
        }
    }

    /**
     * Finding the duplicate elements in the list
     *
     * @param : mono
     * @param : content
     * @param : dosechang
     * @param : enteral
     */
    public List<String> findDuplicateElements(List<String> mono) {

        List<String> duplicate = new ArrayList<String>();
        Set<String> s = new HashSet<String>();
        try {
            if (mono.size() > 0) {
                for (String content : mono) {
                    if (s.add(content) == false) {
                        int i = 1;
                        duplicate.add(content);
                        System.out.println("List of duplicate elements is" + i + content);
                        i++;
                    }
                }
            }
        } catch (Exception e) {
//			System.out.println(e);
        }
        return duplicate;
    }

    /**
     * @param : contents
     * @return the list without duplicate elements
     */
    public List<String> removeDuplicateElements(List<String> contents) {

        LinkedHashSet<String> set = new LinkedHashSet<String>(contents);
        ArrayList<String> listWithoutDuplicateElements = new ArrayList<String>();
        try {

            if (contents.size() > 0) {
                listWithoutDuplicateElements = new ArrayList<String>(set);
            }

        } catch (Exception e) {
//			System.out.println(e);
        }
        return listWithoutDuplicateElements;
    }

    /**
     * @param : i
     * @param : byLocator
     */
    public void iteratorClick(int temp, By byLocator) {

        try {
            String xpath = byLocator.toString();
            String var = "'" + temp + "'";
            xpath = xpath.replaceAll("__placeholder", var);
            String[] test = xpath.split(": ");
            xpath = test[1];
            getDriver().findElement(By.xpath(xpath)).click();
        } catch (Exception e) {
//			System.out.println(e);
        }
    }

    /**
     * Get specific property value of a web element and stores to string variable.
     *
     * @param property  the property of the element.
     * @param byLocator the by locator
     * @return value of the property.
     */
    public static String getElementPropertyToString(String property, By byLocator, String object) {
        //String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
        String propertyValue = null;
        if (platform.equalsIgnoreCase("web")) {
            try {
                WebElement element = DriverManager.getDriver().findElement(byLocator);
                propertyValue = element.getAttribute(property);
            } catch (Exception e) {
                logger.error(e);
            }
        } else if (platform.equalsIgnoreCase("mpwa")) {
            try {
                WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
                propertyValue = element.getAttribute(property);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return propertyValue;
    }

    /**
     * @return true if the list is sorted
     * @return false if the list is not sorted
     */
    public boolean checkListIsSorted(List<String> ListToSort) {

        boolean isSorted = false;

        if (ListToSort.size() > 0) {
            try {
                if (Ordering.natural().isOrdered(ListToSort)) {
                    ExtentReporter.extentLogger("Check sorting", "List is sorted");
                    logger.info("List is sorted");
                    isSorted = true;
                    return isSorted;
                } else {
                    ExtentReporter.extentLogger("Check sorting", ListToSort + " " + "List is not sorted");
                    logger.info(ListToSort + "List is notsorted");
                    isSorted = false;
                }
            } catch (Exception e) {
//				System.out.println(e);
            }
        } else {
            logger.info("The size of the list is zero");
            ExtentReporter.extentLogger("",
                    ListToSort + " " + "There are no elements in the list to check the sort order");
        }
        return isSorted;
    }

    /**
     * @param byLocator
     * @returns the list count of the element
     */
    public int iterativeGetCount(int temp, By byLocator) {

        int count = 0;
        try {

            String xpath = byLocator.toString();
            String var = "'" + temp + "'";
            xpath = xpath.replaceAll("__placeholder", var);
            String[] test = xpath.split(": ");
            xpath = test[1];
            count = getDriver().findElements(By.xpath(xpath)).size();
            logger.info("List count for" + " " + xpath + " " + "is" + " " + count);
            ExtentReporter.extentLogger("getCount", "List count for" + " " + xpath + " " + "is" + " " + count);
        } catch (Exception e) {
//			logger.error(e);
        }
        return count;
    }

    /**
     * @param temp
     * @param byLocator
     * @return
     */
    public By iterativeXpathText(String temp, By byLocator) {

        By searchResultList = null;

        try {

            String xpath = byLocator.toString();
            String var = "'" + temp + "'";
            xpath = xpath.replaceAll("__placeholder", var);
            String[] test = xpath.split(": ");
            xpath = test[1];
            searchResultList = By.xpath(xpath);
        } catch (Exception e) {
//			System.out.println(e);
        }
        return searchResultList;
    }

    /**
     * @param byLocator Checks whether element is not displayed
     * @throws Exception
     */
    public void checkElementNotPresent(By byLocator) throws Exception {
        boolean isElementNotPresent = true;
        try {
            isElementNotPresent = checkcondition(byLocator);
            softAssert.assertEquals(isElementNotPresent, false);
            logger.info("" + byLocator + " " + "is not displayed");
            ExtentReporter.extentLogger("checkElementNotPresent", "" + byLocator + "is not displayed");
        } catch (Exception e) {
            softAssert.assertEquals(isElementNotPresent, true, "Element" + byLocator + " " + "is visible");
//			softAssert.assertAll();
            logger.error(byLocator + " " + "is visible");
            ExtentReporter.extentLogger("checkElementNotPresent", "" + byLocator + "is displayed");
            ExtentReporter.screencapture();
        }
    }

    public static void Resize_Browser(int width, int height) {
        try {

            Dimension scale = new Dimension(width, height);
            getDriver().manage().window().setSize(scale);

        } catch (Exception e) {
            logger.info("Unable to set the size of the browser");
        }

    }

    /**
     * Swipes the screen in left or right or Up or Down or direction
     */
    /*
     * @SuppressWarnings("rawtypes") public static void Swipe1(String direction, int
     * count) { String platform =
     * Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite
     * ().getName(); TouchAction touchAction=null;
     * if(platform.equalsIgnoreCase("web")) { touchAction = new
     * TouchAction(getDriver()); } else if(platform.equalsIgnoreCase("Android")) {
     * touchAction = new TouchAction(DriverManager.getAppiumDriver()); } String dire
     * = direction; try { if (dire.equalsIgnoreCase("LEFT")) {
     *
     * for (int i = 0; i < count; i++) { Dimension size =
     * getDriver().manage().window().getSize(); int startx = (int) (size.width *
     * 0.5); int endx = (int) (size.width * 0.1); int starty = size.height / 2;
     * touchAction.press(PointOption.point(startx, starty))
     * .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
     * .moveTo(PointOption.point(endx, starty)).release().perform();
     * logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1)
     * + " times"); ExtentReporter.extentLogger("SwipeLeft", "Swiping screen in " +
     * " " + dire + " direction" + " " + (i + 1) + " times"); } } else if
     * (dire.equalsIgnoreCase("RIGHT")) {
     *
     * for (int j = 0; j < count; j++) { Dimension size =
     * getDriver().manage().window().getSize(); int endx = (int) (size.width * 0.8);
     * int startx = (int) (size.width * 0.20); if (size.height > 2000) { int starty
     * = (int) (size.height / 2); touchAction.press(PointOption.point(startx,
     * starty)) .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
     * .moveTo(PointOption.point(endx, starty)).release().perform(); } else { int
     * starty = (int) (size.height / 1.5);
     * touchAction.press(PointOption.point(startx, starty))
     * .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
     * .moveTo(PointOption.point(endx, starty)).release().perform(); }
     *
     * logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1)
     * + " times"); ExtentReporter.extentLogger("SwipeRight", "Swiping screen in " +
     * " " + dire + " direction" + " " + (j + 1) + " times"); } } else if
     * (dire.equalsIgnoreCase("UP")) {
     *
     * for (int j = 0; j < count; j++) { Dimension size =
     * getDriver().manage().window().getSize(); System.out.println("size : " +
     * size); int starty = (int) (size.height * 0.70);// 0.8 int endy = (int)
     * (size.height * 0.30);// 0.2 int startx = size.width / 2;
     * touchAction.press(PointOption.point(startx, starty))
     * .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
     * .moveTo(PointOption.point(startx, endy)).release().perform();
     * logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1)
     * + " times"); ExtentReporter.extentLogger("SwipeUp", "Swiping screen in " +
     * " " + dire + " direction" + " " + (j + 1) + " times");
     *
     * } } else if (dire.equalsIgnoreCase("DOWN")) { for (int j = 0; j < count; j++)
     * {
     *
     * Dimension size = getDriver().manage().window().getSize(); int starty = (int)
     * (size.height * 0.70); int endy = (int) (size.height * 0.30); int startx =
     * size.width / 2; touchAction.press(PointOption.point(startx, endy))
     * .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
     * .moveTo(PointOption.point(startx, starty)).release().perform();
     * logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1)
     * + " times"); ExtentReporter.extentLogger("SwipeDown", "Swiping screen in " +
     * " " + dire + " direction" + " " + (j + 1) + " times"); } } } catch (Exception
     * e) { // logger.error(e); } }
     */

//    @SuppressWarnings("rawtypes")
    public void SwipeRail(By From) throws Exception {

        WebElement element = DriverManager.getDriver().findElement(From);
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.8);
        int endx = (int) (size.width * 0.1);

        String eleY = element.getAttribute("y");
        int currentPosY = Integer.parseInt(eleY);
        System.out.println(currentPosY);
        currentPosY = Integer.parseInt(eleY) + 200;
        System.out.println(currentPosY);
        touchAction = new TouchAction(getDriver());
        touchAction.press(PointOption.point(startx, currentPosY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(endx, currentPosY)).release().perform();
    }

    /**
     * Swipes the screen in left or right or Up or Down or direction
     *
     * @param direction to swipe Left or Right or Up or Down
     * @param count     to swipe
     */
    @SuppressWarnings("rawtypes")
    public void PartialSwipe(String direction, int count) {
        touchAction = new TouchAction(getDriver());
        String dire = direction;

        try {

            if (dire.equalsIgnoreCase("LEFT")) {

                for (int i = 0; i < count; i++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int startx = (int) (size.width * 0.4);
                    int endx = (int) (size.width * 0.1);
                    int starty = size.height / 2;
                    // getDriver().swipe(startx, starty, endx, starty, 1000);
                    touchAction.press(PointOption.point(startx, starty))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(endx, starty)).release().perform();
                    logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
                    ExtentReporter.extentLogger("SwipeLeft",
                            "Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
                }
            } else if (dire.equalsIgnoreCase("RIGHT")) {

                for (int j = 0; j < count; j++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int endx = (int) (size.width * 0.4);
                    int startx = (int) (size.width * 0.1);
                    int starty = size.height / 2;
                    touchAction.press(PointOption.point(startx, starty))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(endx, starty)).release().perform();
                    logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
                    ExtentReporter.extentLogger("SwipeRight",
                            "Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
                }
            } else if (dire.equalsIgnoreCase("UP")) {

                for (int j = 0; j < count; j++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int starty = (int) (size.height * 0.40);
                    int endy = (int) (size.height * 0.1);
                    int startx = size.width / 2;
                    touchAction.press(PointOption.point(startx, starty))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(startx, endy)).release().perform();
                    logger.info("Swiping screen in " + dire + " direction" + " " + (j + 1) + " times");
                    ExtentReporter.extentLogger("SwipeUp",
                            "Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

                }
            } else if (dire.equalsIgnoreCase("DOWN")) {
                for (int j = 0; j < count; j++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int starty = (int) (size.height * 0.4);
                    int endy = (int) (size.height * 0.1);
                    int startx = size.width / 2;
                    touchAction.press(PointOption.point(startx, endy))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(startx, starty)).release().perform();
                    logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
                    ExtentReporter.extentLogger("SwipeDown",
                            "Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

                }
            }

        } catch (Exception e) {
//			logger.error(e);

        }
    }

    @SuppressWarnings("rawtypes")
    public void SwipeRailContentCards(By From) throws Exception {

        Dimension size = getDriver().manage().window().getSize();
        int screenWidth = (int) (size.width * 0.8);

        WebElement element = DriverManager.getDriver().findElement(From);
        String eleX = element.getAttribute("x");
        String eleY = element.getAttribute("y");
        int currentPosX = Integer.parseInt(eleX);
        int currentPosY = Integer.parseInt(eleY);

        currentPosX = currentPosX + screenWidth;
        currentPosY = currentPosY + 150;

        touchAction = new TouchAction(getDriver());
        touchAction.press(PointOption.point(currentPosX, currentPosY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(0, currentPosY))
                .release().perform();
    }

    /**
     * Drag window
     *
     * @param byLocator, byTimer
     */
    @SuppressWarnings("rawtypes")
    public void DragWindow(By byLocator, String direction) throws Exception {
        WebElement element = getDriver().findElement(byLocator);
        touchAction = new TouchAction(getDriver());
        if (direction.equalsIgnoreCase("LEFT")) {
            Dimension size = element.getSize();
            int startx = (int) (size.width * 0.4);
            int endx = (int) (size.width * 0.1);
            int starty = size.height / 2;
            touchAction.longPress(PointOption.point(startx, starty))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endx, starty)).release().perform();
            logger.info("Swiping " + " " + direction + " direction");
            ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
        } else if (direction.equalsIgnoreCase("DOWN")) {
            Dimension size = getDriver().manage().window().getSize();
            int starty = (int) (size.height * 0.80);
            int endy = (int) (size.height * 0.20);
            int startx = size.width / 2;
            touchAction.longPress(PointOption.point(startx, endy))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point((int) startx, (int) starty)).release().perform();
            logger.info("Swiping " + " " + direction + " direction");
            ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
        }
    }

    /**
     * @param bundleID
     */
    public void launchiOSApp(String bundleID) {

        try {
            iOSDriver = (IOSDriver<WebElement>) getDriver();
            logger.info("Started the bundle id" + " " + bundleID);
            ExtentReporter.extentLogger("Started the bundle id" + " " + bundleID,
                    "Started the bundle id" + " " + bundleID);
        } catch (Exception e) {
            logger.info("Unable to Start the bundle id" + " " + bundleID);
            ExtentReporter.extentLogger("Unable to Start the bundle id" + " " + bundleID,
                    "Unable to Start the bundle id" + " " + bundleID);
        }
    }

    /**
     * Get the Mobile Orientation
     *
     * @throws Exception
     */
    public void GetAndVerifyOrientation(String Value) throws Exception {
        ScreenOrientation orientation = getDriver().getOrientation();
        String ScreenOrientation = orientation.toString();
        try {
            softAssert.assertEquals(ScreenOrientation.equalsIgnoreCase(Value), true,
                    "The screen Orientation is " + ScreenOrientation);
            logger.info("The screen Orientation is " + ScreenOrientation);
            ExtentReporter.extentLogger("Screen Orientation", "The screen Orientation is " + ScreenOrientation);
        } catch (Exception e) {
            softAssert.assertEquals(false, true, "The screen Orientation is not " + ScreenOrientation);
//			softAssert.assertAll();
            logger.error("The screen Orientation is not " + ScreenOrientation);
            ExtentReporter.extentLogger("Screen Orientation", "The screen Orientation is not " + ScreenOrientation);
            ExtentReporter.screencapture();
        }
    }

    /**
     * Closes the iOS keyboard
     */
    public void closeIosKeyboard() {

        try {
            iOSDriver = (IOSDriver<WebElement>) getDriver();
            ExtentReporter.extentLogger("Hiding keyboard successful", "Hiding keyboard successful");
        } catch (Exception e) {
            ExtentReporter.extentLogger("Hiding keyboard not successful", "Hiding keyboard not successful");
        }
    }

    /**
     * closes the application
     */
    public void closeiOSApp() {
        try {
            iOSDriver = (IOSDriver<WebElement>) getDriver();
            iOSDriver.closeApp();
            ExtentReporter.extentLogger("Killed the appliaction successfully", "Killed the appliaction successfully");
        } catch (Exception e) {
            ExtentReporter.extentLogger("Unable to Kill the application", "Unable to Kill the application");

        }
    }

    /**
     * closes the Android application
     */
    public void closeAndroidApp() {
        try {
            getDriver().resetApp();
            ExtentReporter.extentLogger("Killed the application successfully", "Killed the application successfully");
        } catch (Exception e) {
            ExtentReporter.extentLogger("Unable to Kill the application", "Unable to Kill the application");

        }
    }

    /**
     * Verifies if a new page or app is open
     */

    public boolean newPageOrNt() {
        boolean app = false;
        try {
            String cmd = "adb shell \"dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'\"";
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String outputText = "";
            while ((outputText = br.readLine()) != null) {
                if (!outputText.trim().contains("com.tv.v18.viola")) {
                    app = true;
                    logger.info("The app is navigated to ad page");
                    ExtentReporter.extentLogger("Navigated to ad page or not", "The app is navigated to ad page");
                } else {
                    logger.error("The app is not navigated to ad page");
                    ExtentReporter.extentLogger("Navigated to ad page or not", "The app is not navigated to ad page");
                }
            }
            br.close();
            p.waitFor();
        } catch (Exception e) {
//			System.out.println(e);
        }
        return app;
    }

    public void IosDragWindow(By byLocator, String direction) throws Exception {
        WebElement element = getDriver().findElement(byLocator);
        @SuppressWarnings({"rawtypes", "unused"})
        TouchAction action = new TouchAction(getDriver());
        if (direction.equalsIgnoreCase("LEFT")) {
            Dimension size = element.getSize();
            int startx = (int) (size.width * 0.4);
            System.out.println(startx);
            int endx = 0;
            System.out.println(endx);
            int starty = 1250;
            System.out.println(starty);
            SwipeAnElement(element, startx, starty);
            logger.info("Swiping " + " " + direction + " direction");
            ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
        }
    }

    public static String getAttributValue(String property, By byLocator) throws Exception {
        if (getPlatform().equalsIgnoreCase("Android")) {
            String Value = null;
            WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
            Value = element.getAttribute(property);
            return Value;
        }
        if (getPlatform().equalsIgnoreCase("web")) {
            String Value = null;
            WebElement element = getWebDriver().findElement(byLocator);
            Value = element.getAttribute(property);
            return Value;
        }
        return null;
    }

    /*
     * public void captureScreenshotAndCompare(String SSName) throws
     * InterruptedException { Thread.sleep(10000); File src =
     * getDriver().getScreenshotAs(OutputType.FILE); String dir =
     * System.getProperty("user.dir"); String fileName = dir +
     * "/Applitool/baseLine/" + SSName + ".png"; System.out.println(fileName); try {
     * FileUtils.copyFile(src, new File(fileName)); } catch (IOException e) {
     * System.out.println(e.getMessage()); } BufferedImage img; try { img =
     * ImageIO.read(new File(fileName)); getEye().checkImage(img, SSName);
     * ExtentReporter.extentLogger("UI Validation", "UI for " + SSName +
     * " is validated"); } catch (IOException e) {
     * System.out.println(e.getMessage()); } }
     */

    public void SwipeAnElement(WebElement element, int posx, int posy) {
        AndroidTouchAction touch = new AndroidTouchAction(getDriver());
        touch.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(posx, posy))
                .release().perform();
    }

    public void longPressContent(By element) throws Exception {
        AndroidTouchAction touch = new AndroidTouchAction(getDriver());
        touch.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(getDriver().findElement(element)))).release().perform();

        TouchActions action = new TouchActions(getDriver());
        action.singleTap(getDriver().findElement(element));
        action.click();

    }

    public static boolean verifyElementExist(WebElement ele, String str) throws Exception {
        try {
            WebElement element = ele;
            if (element.isDisplayed()) {
                ExtentReporter.extentLogger("checkElementPresent", "<b>" + str + "</b> is displayed");
                logger.info("" + str + " is displayed");
                return true;
            }
        } catch (Exception e) {
            ExtentReporter.extentLogger("checkElementPresent", "<b>" + str + "</b> is not displayed");
            logger.info(str + " is not displayed");
            return false;
        }
        return false;
    }

    public boolean checkcondition(String s) throws Exception {
        boolean iselementPresent = false;
        try {
            String element = "//*[@text='[" + s + "]']";
            iselementPresent = ((WebElement) getDriver().findElementsByXPath(element)).isDisplayed();
        } catch (Exception e) {
            iselementPresent = false;
        }
        return iselementPresent;
    }

    public void switchtoLandscapeMode() throws IOException {
        Runtime.getRuntime().exec(
                "adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1");
    }

    public void switchtoPortraitMode() throws IOException {
        Runtime.getRuntime().exec(
                "adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0");
    }

    @SuppressWarnings("rawtypes")
    public void PartialSwipeInConsumptionScreen(String direction, int count) {
        touchAction = new TouchAction(getDriver());
        String dire = direction;

        try {

            if (dire.equalsIgnoreCase("UP")) {

                for (int j = 0; j < count; j++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int starty = (int) (size.height * 0.8);
                    int endy = (int) (size.height * 0.5);
                    int startx = size.width / 2;
                    touchAction.press(PointOption.point(startx, starty))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(startx, endy)).release().perform();
                    logger.info("Swiping screen in " + dire + " direction" + " " + (j + 1) + " times");
                    ExtentReporter.extentLogger("SwipeUp",
                            "Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

                }
            } else if (dire.equalsIgnoreCase("DOWN")) {
                for (int j = 0; j < count; j++) {
                    Dimension size = getDriver().manage().window().getSize();
                    int starty = (int) (size.height * 0.8);
                    int endy = (int) (size.height * 0.5);
                    int startx = size.width / 2;
                    touchAction.press(PointOption.point(startx, endy))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(startx, starty)).release().perform();
                    logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
                    ExtentReporter.extentLogger("SwipeDown",
                            "Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

                }
            }

        } catch (Exception e) {
//			logger.error(e);

        }
    }

//====================================================================================================================================
	/** ::::::::::::::::Web Utilities:::::::::::: */

	/**
	 * Function to ExplicitWait Visibility
	 *
	 * @param element
	 * @param time
	 * @throws Exception
	 */
	public static void explicitWaitVisibility(By element, int time) throws Exception {
		try{
        if (getPlatform().equalsIgnoreCase("web")) {
			WebDriverWait wait = new WebDriverWait(getWebDriver(), time);
			wait.until(ExpectedConditions.visibilityOf(findElement(element)));
		} else {
			WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), time);
			wait.until(ExpectedConditions.visibilityOf(DriverManager.getAppiumDriver().findElement(element)));
        }
    } catch (Exception e) {
        ExtentReporter.extentLoggerFail("Fail", "Failed to wait for element " + element);
        throw new AssertionError("Failed to wait for element " + element, e);
        }
    }

	public static void explicitWaitVisible(By byLocator, int time) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}

	/**
	 * Function to ExplicitWait for Clickable
	 *
	 * @param element
	 * @param time
	 * @throws Exception
	 */
	public void explicitWaitClickable(By element, int time) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(element)));
	}

	/**
	 * Function to ExplicitWait for windows
	 *
	 * @param noOfWindows
	 */
	public static void explicitWaitForWindows(int noOfWindows) {
		wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
	}

	/**
	 * Function for ExplicitWait of Element Refresh
	 *
	 * @throws Exception
	 */
	public void explicitWaitForElementRefresh(By element) throws Exception {
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(element))));
	}

	/**
	 * Function for implicitWait
	 */
	public static void implicitWait(int time) {
		DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * Function to select by visible text from drop down
	 *
	 * @param element
	 * @param value
	 * @throws Exception
	 */
	public static void selectByVisibleTextFromDD(By element, String value) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getWebDriver().findElement(element));
		select.selectByVisibleText(value);
	}

	/**
	 * Function to select by value from drop down
	 *
	 * @param element
	 * @param value
	 * @throws Exception
	 */
	public void selectByValueFromDD(By element, String value) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getDriver().findElement(element));
		select.selectByValue(value);
	}

	/**
	 * Function to select By index From Drop down
	 *
	 * @param element
	 * @param index
	 * @throws Exception
	 */
	public static void selectByIndexFromDD(By element, String index) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getWebDriver().findElement(element));
		select.selectByValue(index);
	}

	/**
	 * Function to get First Element from Drop down
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public static String getFirstElementFromDD(By element) throws Exception {
		Select select = new Select(getWebDriver().findElement(element));
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Function to scroll down
	 */
	public static void scrollDownWEB() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,250)", "");
	}

	/**
	 * Function to Scroll By
	 */
	public static void scrollByWEB(int x,int y) {
		js = (JavascriptExecutor) getWebDriver();
		js.executeScript("window.scrollBy("+x+","+y+")", "");
	}

	public static void scrollToBottomToTopOfPageWEB() {
		js = (JavascriptExecutor) getWebDriver();
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * Function to scroll bottom of page
	 */
	public static void scrollToBottomOfPageWEB() {
		js = (JavascriptExecutor) getWebDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollToBottomOfPage() {
		js = (JavascriptExecutor) DriverManager.getAppiumDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}


	/**
	 * Function to scroll to top of the page
	 */
	public static void scrollToTopOfPageWEB() {
		js = (JavascriptExecutor) getWebDriver();
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	public static void scrollToTopOfPage() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	/**
	 * Function Scroll to Element
	 *
	 * @param element
	 * @throws Exception
	 */
	public static void ScrollToTheElement(By element) throws Exception {
		if (getPlatform().equalsIgnoreCase("web")) {
			js = (JavascriptExecutor) getWebDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", getWebDriver().findElement(element));
			js.executeScript("window.scrollBy(0,-50)", "");
		} else {
			js = (JavascriptExecutor) DriverManager.getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", DriverManager.getDriver().findElement(element));
			js.executeScript("window.scrollBy(0,-50)", "");
		}
	}

	/**
	 * Function Scroll to Element
	 *
	 * @param element
	 * @throws Exception
	 */
	public static void ScrollToTheElementWeb(By element) throws Exception {
		if (getPlatform().equalsIgnoreCase("web")) {
			js = (JavascriptExecutor) getWebDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", getWebDriver().findElement(element));
			js.executeScript("window.scrollBy(0,-50)", "");
		} else {
			js = (JavascriptExecutor) DriverManager.getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", DriverManager.getDriver().findElement(element));
			js.executeScript("window.scrollBy(0,-50)", "");
		}
	}

	/**
	 * Function for hard sleep
	 *
	 * @param seconds
	 */
	public void sleep(int seconds) {
		try {
			int ms = 1000 * seconds;
			Thread.sleep(ms);
		} catch (InterruptedException e) {
//			e.printStackTrace();
		}
	}

	/**
	 * Function to switch the tab
	 *
	 * @param tab
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void switchTab(int tab) {
		ArrayList<String> window = new ArrayList(DriverManager.getDriver().getWindowHandles());
		DriverManager.getDriver().switchTo().window(window.get(tab));
	}

	/**
	 * Function to generate random integer of specified maxValue
	 *
	 * @param maxValue
	 * @return
	 */
	public String generateRandomInt(int maxValue) {
		Random rand = new Random();
		int x = rand.nextInt(maxValue);
		String randomInt = Integer.toString(x);
		return randomInt;
	}

	public static String RandomIntegerGenerator(int n) {
		String number = "0123456789";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (number.length() * Math.random());

			sb.append(number.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * Function to generate Random characters of specified length
	 *
	 * @param candidateChars
	 * @param length
	 * @return
	 */
	public String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

	/**
	 * Function to generate Random Integer between range
	 *
	 * @param maxValue
	 * @param minValue
	 * @return
	 * @throws InterruptedException
	 */
	public String generateRandomIntwithrange(int maxValue, int minValue) throws InterruptedException {
		Thread.sleep(2000);
		Random rand = new Random();
		int x = rand.nextInt(maxValue - minValue) + 1;
		String randomInt = Integer.toString(x);
		System.out.println("Auto generate of number from generic method : " + randomInt);
		return randomInt;
	}

	/**
	 * Function to drag and drop an object
	 *
	 * @param From
	 * @param To
	 * @throws Exception
	 */
	public void dragnddrop(By From, By To) throws Exception {
		WebElement Drag = getDriver().findElement(From);
		WebElement Drop = getDriver().findElement(To);
		Thread.sleep(1000);
		Actions builder = new Actions(getDriver());
		builder.clickAndHold(Drag).moveToElement(Drop).release(Drop).build().perform();
	}

	/**
	 * Function Convert from String to Integer @param(String)
	 */
	public int convertToInt(String string) {
		int i = Integer.parseInt(string);
		return i;
	}

	/**
	 * Function Convert from Integer to String @param(integer)
	 */
	public String convertToString(int i) {
		String string = Integer.toString(i);
		return string;
	}

	/**
	 * Click On element without waiting or verifying
	 *
	 * @param byLocator the by locator
	 */
	public static void clickDirectly(By byLocator, String validationtext) throws Exception {
		try {
			getDriver().findElement(byLocator).click();
			logger.info("Clicked on the " + validationtext);
			ExtentReporter.extentLogger("click", "Clicked on the <b> " + validationtext);
		} catch (Exception e) {
//			logger.error(e);
			ExtentReporter.screencapture();
		}
	}

	public static void verifyAlert() {
		try {
			DriverManager.getDriver().switchTo().alert().dismiss();
			logger.info("Dismissed the alert Pop Up");
			ExtentReporter.extentLogger("Alert PopUp", "Dismissed the alert Pop Up");
		} catch (Exception e) {

		}
	}

	public void acceptAlert() {
		try {
			DriverManager.getDriver().switchTo().alert().accept();
			logger.info("Dismissed the alert Pop Up");
			ExtentReporter.extentLogger("Alert PopUp", "Dismissed the alert Pop Up");
		} catch (Exception e) {

		}
	}

	public static void acceptWebAlert() {
		try {
			getWebDriver().switchTo().alert().accept();
			logger.info("Accepts the alert Pop Up");
			ExtentReporter.extentLogger("Alert PopUp", "Accepts the alert Pop Up");
		} catch (Exception e) {

		}
	}
//	public static void leaveSiteWebAlert() {
//		try {
//			getWebDriver().switchTo().alert().accept();
//			logger.info("Accepts the alert Pop Up");
//			ExtentReporter.extentLogger("Alert PopUp", "Accepts the alert Pop Up");
//		} catch (Exception e) {
//
//		}
//	}

	public static boolean clickElementWithLocator(By locator) throws Exception {
		String url = Utilities.getParameterFromXML("url");
		//	String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (platform.equalsIgnoreCase("web")) {
			try {
				DriverManager.getDriver().findElement(locator).click();
				return true;
			} catch (Exception e) {
			}
		} else if (platform.equalsIgnoreCase("mpwa")) {
			try {
				DriverManager.getAppiumDriver().findElement(locator).click();
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static boolean clickElementWithWebElement(WebElement element) throws Exception {
		try {
			element.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int timeToSec(String s) {
		String[] t = s.split(":");
		int num = 0;
		System.out.println(t.length);

		if (t.length == 2) {
			num = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]); // minutes since 00:00
		}
		if (t.length == 3) {
			num = ((Integer.parseInt(t[0]) * 60) * 60) + Integer.parseInt(t[1]) * 60 + Integer.parseInt(t[2]);
		}

		return num;
	}

	public static void partialScrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
		jse.executeScript("window.scrollBy(0,500)", "");
	}

	public static void clickByElement(WebElement ele, String validationtext) throws Exception {
		try {
			WebElement element = ele;
			element.click();
			logger.info("Clicked on the " + validationtext);
			ExtentReporter.extentLogger("click", "Clicked on the <b> " + validationtext);
		} catch (Exception e) {
			logger.error(e);
			ExtentReporter.screencapture();
		}
	}

	public static boolean verifyElementEnabled(By byLocator, String str) throws Exception {

		try {
			WebElement element = DriverManager.getDriver().findElement(byLocator);
			if (element.isEnabled()) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
				logger.info("" + str + " is displayed");
				return true;
			}
		} catch (Exception e) {
			ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
			logger.info(str + " is not displayed");
			return false;
		}
		return false;
	}

    public static int getCountweb(By byLocator) {
        int count = 0;
        try {
            count = DriverManager.getDriver().findElements(byLocator).size();
            logger.info("List count for" + " " + byLocator + " " + "is" + " " + count);
            ExtentReporter.extentLogger("getCount", "List count for" + " " + byLocator + " " + "is" + " " + count);
        } catch (Exception e) {
//			logger.error(e);
        }
        return count;
    }

    public static boolean waitForElementAndClickIfPresent(By locator, int seconds, String message) throws Exception {
        //   try {
        if (getPlatform().equals("Web")) {
            for (int time = 0; time <= seconds; time++) {
                try {
                    getWebDriver().findElement(locator).click();
                    logger.info("Clicked element " + message);
                    ExtentReporter.extentLogger("clickedElement", "Clicked element " + message);
                    return true;
                } catch (Exception e) {
                    Thread.sleep(1000);
                    if (time == seconds) {
                        ExtentReporter.extentLoggerFail("Unable to click element " + locator, message);
                        ExtentReporter.screencapture();
                        throw new AssertionError("Unable to click element " + locator, e);
                    }
                }
            }
        } else if (getPlatform().equals("Android") || getPlatform().equals("MPWA")) {
            for (int time = 0; time <= seconds; time++) {
                try {
                    getDriver().findElement(locator).click();
                    logger.info("Clicked on " + message);
                    ExtentReporter.extentLogger("clickedElement", "Clicked on " + message);
                    return true;
                } catch (Exception e) {
                    Thread.sleep(1000);
                    if (time == seconds) {
                        ExtentReporter.extentLoggerFail("Unable to click element " + locator, message);
                        ExtentReporter.screencapture();
                        throw new AssertionError("Unable to click element " + locator, e);
                    }

                }
            }
        }
        // } catch (Exception e) {
//      logger.error(e);

        //   }
        return false;
    }

	public static String RandomStringGenerator(int n) {
		{

			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
			StringBuilder sb = new StringBuilder(n);
			for (int i = 0; i < n; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());

				sb.append(AlphaNumericString.charAt(index));
			}
			return sb.toString();
		}
	}

	/**
	 * Method to swipe bottom of page
	 *
	 * @throws Exception
	 */
	public static void swipeToBottomOfPage() throws Exception {
		for (int i = 0; i < 5; i++) {
			scrollToBottomOfPage();
			waitTime(4000);
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void androidSwitchTab() {
		ArrayList<String> window = new ArrayList(getDriver().getWindowHandles());
		getDriver().switchTo().window(window.get(window.size() - 1));
	}

	/**
	 * Function to switch to parent Window
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void AndroidSwitchToParentWindow() {
		try {
			ArrayList<String> window = new ArrayList(getDriver().getWindowHandles());
			getDriver().switchTo().window(window.get(0));
		} catch (Exception e) {
			System.out.println("\n No window is displayed!");
		}
	}

	public static String getTheOSVersion() {
		String version = null;
		try {
			String cmd1 = "adb shell getprop ro.build.version.release";
			Process p1 = Runtime.getRuntime().exec(cmd1);
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			// outputText1 ="";
			while ((version = br.readLine()) != null) {
				logger.info("Version :: " + version.toString());
				Thread.sleep(3000);
				break;
			}

		} catch (Exception e) {
			// logger.error(e);
		}
		return version;
	}

	public void TurnOFFWifi() throws Exception {
		String Deviceversion = getTheOSVersion();
		System.out.println("Turn off wifi");
		if (Deviceversion.contains("6")) {
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus disable");
			logger.info("Turning off wifi");
			ExtentReporter.extentLoggerPass("Turning off wifi", "Turning off wifi");
		} else {
			Runtime.getRuntime().exec("adb shell svc wifi disable");
			logger.info("Turning off wifi");
			ExtentReporter.extentLoggerPass("Turning off wifi", "Turning off wifi");
		}
	}

	public void TurnONWifi() throws Exception {
		String Deviceversion = getTheOSVersion();
		System.out.println("Turn on wifi");
		if (Deviceversion.contains("6")) {
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus enable");
			logger.info("Turning ON wifi");
			ExtentReporter.extentLoggerPass("Turning ON wifi", "Turning ON wifi");
		} else {
			Runtime.getRuntime().exec("adb shell svc wifi enable");
			logger.info("Turning ON wifi");
			ExtentReporter.extentLoggerPass("Turning ON wifi", "Turning ON wifi");
		}
	}

	@SuppressWarnings("rawtypes")
	public void carouselSwipe(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();

					int startx = (int) (size.width * 0.9);
					int endx = (int) (size.width * 0.20);
					int starty = size.height / 2;
					touchAction.press(PointOption.point(startx, starty))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
							.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int endx = (int) (size.width * 0.9);
					int startx = (int) (size.width * 0.20);
					if (size.height > 2000) {
						int starty = (int) (size.height / 2);
						touchAction.press(PointOption.point(startx, starty))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
								.moveTo(PointOption.point(endx, starty)).release().perform();
					} else {
						int starty = (int) (size.height / 1.5);
						touchAction.press(PointOption.point(startx, starty))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
								.moveTo(PointOption.point(endx, starty)).release().perform();
					}

					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			}
		} catch (Exception e) {
//			logger.error(e);

		}
	}

	public static void ScrollToTheElementWEB(By element) throws Exception {
		js = (JavascriptExecutor) getWebDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", getWebDriver().findElement(element));
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	public static boolean checkElementDisplayed(By byLocator, String str) throws Exception {
		//	String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (platform.equalsIgnoreCase("web")) {
			try {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				if (element.isDisplayed()) {
					ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
					logger.info("" + str + " is displayed");
					return true;
				}
			} catch (Exception e) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
				logger.info("" + str + " is not displayed");
				return false;
			}
		} else if (platform.equalsIgnoreCase("mpwa")) {
			try {
				WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
				if (element.isDisplayed()) {
					ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
					logger.info("" + str + " is displayed");
					return true;
				}
			} catch (Exception e) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
				logger.info("" + str + " is not displayed");
				return false;
			}
		}
		return false;
	}

	public static String getParameterFromXML(String param) {
		return ExtentReporter.testContext.getCurrentXmlTest().getParameter(param);
	}

	/**
	 * @param expectedtitle
	 */
	public static void getTitle(String expectedtitle) {
		String title = DriverManager.getDriver().getTitle();
		Assert.assertEquals(title, expectedtitle, "Actual and expected titles are matching");
		logger.info("Expected Title " + title + " is matching");
		logger.info("Home Page is displayed");
		extentLogger("Expected Title ", title + " is matching");
	}

	@SuppressWarnings("rawtypes")
	public void SwipeInLandscapeMode(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("DOWN") | dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();
					int xCor = (int) (size.height / 2);
					int startY = (int) (size.width * 0.20);
					int endY = (int) (size.width * 0.85);
					System.out.println(startY + " X " + endY);
					touchAction.press(PointOption.point(xCor, startY))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
							.moveTo(PointOption.point(xCor, endY)).release().perform();
					logger.info("Swiping screen in " + dire + " direction" + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + dire + " direction" + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("UP") | dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int xCor = (int) (size.height / 2);
					int startY = (int) (size.width * 0.85);
					int endY = (int) (size.width * 0.20);
					System.out.println(startY + " X " + endY);
					System.out.println(xCor);
					touchAction.press(PointOption.point(xCor, startY))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
							.moveTo(PointOption.point(xCor, endY)).release().perform();

					logger.info("Swiping screen in " + dire + " direction " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + dire + " direction " + (j + 1) + " times");
				}
			}

		} catch (Exception e) {
//			logger.error(e);
		}
	}

	public void clearBackgroundApps() throws IOException {
		String adbRecentApp = "adb shell input keyevent KEYCODE_APP_SWITCH";
		String adbSelectApp = "adb shell input keyevent KEYCODE_DPAD_DOWN";
		String adbClearApp = "adb shell input keyevent KEYCODE_DEL";
		String adbHomeScreen = "adb shell input keyevent KEYCODE_HOME";

		Runtime.getRuntime().exec(adbRecentApp);

		for (int iterator = 1; iterator <= 7; iterator++) {
			waitTime(1000);
			Runtime.getRuntime().exec(adbClearApp);
			Runtime.getRuntime().exec(adbSelectApp);
		}

		waitTime(1000);
		Runtime.getRuntime().exec(adbHomeScreen);
		System.out.println("Cleared all background Apps");
	}

	public boolean findElementInRefreshingConvivaPage(WebDriver webdriver, By locator, String displayText)
			throws Exception {
		js = (JavascriptExecutor) DriverManager.getDriver();
		for (int i = 1; i <= 500; i++) {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			try {
				DriverManager.getDriver().findElement(locator);
				logger.info(displayText + " is displayed");
				ExtentReporter.extentLogger("", displayText + " is displayed");
				return true;
			} catch (Exception e) {
				try {
					js.executeScript("window.scrollBy(0,100)", "");
					waitTime(2000);
					System.out.println("Waiting ..");
				} catch (Exception e1) {
				}
			}
		}
		return false;
	}

	// ====================================================TV=================================================
	public boolean verifyElementExistTv(By byLocator, String str) throws Exception {

		try {

			if (getDriver().findElement(byLocator).isDisplayed()) {
				ExtentReporter.extentLoggerPass("checkElementPresent", str + " is displayed");
				logger.info("" + str + " is displayed");
				return true;
			}
		} catch (Exception e) {
			ExtentReporter.extentLogger("checkElementPresent", str + " is not displayed");
			logger.info(str + " is not displayed");
			return false;
		}
		return false;
	}

	public static void BrowsertearDown() {
		DriverManager.getDriver().quit();
	}

	public void decode() {
		CTUserName = new String(Base64.getDecoder().decode(getParameterFromXML("CTUser")));
		CTPWD = new String(Base64.getDecoder().decode(getParameterFromXML("CTPwd")));
	}

	/**
	 * The method will wait for the element to not be located for a maximum of given
	 * seconds. The method terminates immediately once the element is located and
	 * throws error.
	 */
	public static void waitForElementAbsence(By locator, int seconds, String message) throws InterruptedException {
		main:
		for (int time = 0; time <= seconds; time++) {
			try {
				Utilities.findElement(locator);
				logger.error("Located element " + message);
				ExtentReporter.extentLoggerFail("locatedElement", "Located element " + message);
				break main;
			} catch (Exception e) {
				Thread.sleep(1000);
				if (time == seconds) {
					logger.info("Expected behavior: " + message + " is not displayed");
					ExtentReporter.extentLogger("failedLocateElement",
							"Expected behavior: " + message + " is not displayed");
				}
			}
		}
	}

	/**
	 * This method will wait for element presence till the given time
	 *
	 * @param locator
	 * @param seconds
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static boolean waitForElementPresence(By locator, int seconds, String message) throws Exception {
		try {
			//	String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
			if (platform.equalsIgnoreCase("web")) {
				WebDriverWait w = new WebDriverWait(DriverManager.getDriver(), seconds);
				w.until(ExpectedConditions.presenceOfElementLocated(locator));
				logger.info(message + " is displayed");
				ExtentReporter.extentLogger("element is displayed", message + " is displayed");
				return true;
			} else if (platform.equalsIgnoreCase("mpwa")) {
				WebDriverWait w = new WebDriverWait(DriverManager.getAppiumDriver(), seconds);
				w.until(ExpectedConditions.presenceOfElementLocated(locator));
				logger.info(message + " is displayed");
				ExtentReporter.extentLogger("element is displayed", message + " is displayed");
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * This method will wait for element presence till the given time
	 *
	 * @param locator
	 * @param seconds
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static boolean waitForElementVisible(By locator, int seconds, String message) throws Exception {
		try {
			WebDriverWait w = new WebDriverWait(DriverManager.getDriver(), seconds);
			w.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.info(message + " is displayed");
			ExtentReporter.extentLogger("element is displayed", message + " is displayed");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Generic method to return browser current url
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getBrowserCurrentUrl() throws Exception {
		try {
			return (DriverManager.getDriver().getCurrentUrl());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Function to switch to window
	 *
	 * @param noOfWindows
	 */
	public static void switchToWindow(int noOfWindows) {
		try {
			wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
			for (String winHandle : DriverManager.getDriver().getWindowHandles()) {
				win.add(winHandle);
				DriverManager.getDriver().switchTo().window(winHandle);
				DriverManager.getDriver().manage().window().maximize();
			}
		} catch (Exception e) {
			System.out.println("\n No window is displayed!");
		}
	}

	public static void fullScreen() {
		try {
			DriverManager.getDriver().manage().window().fullscreen();
		} catch (Exception e) {

		}
	}

    /**
     * Method to Move to Element using Actions
     *
     * @throws Exception
     */
    public static void moveToElementAction(WebElement element, String message) throws Exception {
        try {
            Actions a = new Actions(DriverManager.getDriver());
            a.moveToElement(element).build().perform();
            logger.info("Moved to element " + message);
            ExtentReporter.extentLogger("", "Moved to element " + message);
        } catch (Exception e) {
            logger.error("Failed to move to element " + message);
            ExtentReporter.extentLoggerFail("", "Failed to move to element " + message + e);
            throw new AssertionError("Unable to move to element " + element, e);
        }
    }

	public static void waitUntilElementVisible_NoCustomMessage(By by) {
		if (getPlatform().equalsIgnoreCase("Web")) {
			platform = "web";
		} else {
			platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		}
		try {
			if (platform.equalsIgnoreCase("web")) {
				WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
				wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			}
			if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("IOSBrowserStack")) {
				WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			}
			if (platform.equalsIgnoreCase("iOS")) {
				WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			}

		} catch (Exception e) {
			throw new ElementNotVisibleException(getCustomErrorMessageForReport(by), e.getCause());
		} finally {
		}
	}

	public static boolean isPresentWithWait(By by) {
		boolean flag = true;
		try {
			waitUntilElementVisible_NoCustomMessage(by);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static String getCustomErrorMessageForReport(By by) {
		String className = by.getClass().getSimpleName();
		String value = "";
		try {
			Field field = by.getClass().getDeclaredFields()[1];
			field.setAccessible(true);
			value = field.get(by).toString();
		} catch (Exception ignored) {
		}
		return "Timed out waiting for visibility of element located = " + className + "(" + value + ")";

	}

	public static void waitForElementAndClick(By locator, int seconds, String message) throws Exception {
		//String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		main:
		for (int time = 0; time <= seconds; time++) {
			try {
				if (platform.equalsIgnoreCase("web")) {
					DriverManager.getDriver().findElement(locator).click();
				} else if (platform.equalsIgnoreCase("mpwa")) {
					DriverManager.getAppiumDriver().findElement(locator).click();
				}
				logger.info("Clicked element " + message);
				ExtentReporter.extentLogger("clickedElement", "Clicked element " + message);
				break main;
			} catch (Exception e) {
				Thread.sleep(1000);
				if (time == seconds) {
					logger.error("Failed to click element " + message);
					ExtentReporter.extentLoggerFail("failedClickElement", "Failed to click element " + message);
				}
			}
		}
	}

    /**
     * Function to select by Locator text from drop down
     *
     * @param byLocator
     * @param value
     * @throws Exception
     */
    public static void selectByVisibleTextByLocator(By byLocator, String value) throws Exception{
        try {
            explicitWaitVisibility(byLocator, 20);
            Select select = new Select(findElement(byLocator));
            select.selectByVisibleText(value);
        } catch (Exception e) {
            ExtentReporter.extentLoggerFail("Fail","Unable to select element " + byLocator + value);
            throw new AssertionError("Unable to select element " + byLocator, e);
        }
    }

	public static void selectByValue(By byLocator, String value) throws Exception {
		explicitWaitVisibility(byLocator, 20);
		Select select = new Select(findElement(byLocator));
		select.selectByValue(value);
	}

	/**
	 * Robot Function to press key
	 *
	 * @throws Exception
	 */
	public static void robotClassUp() throws Exception {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);
	}

	/**
	 * Robot Function to press key
	 *
	 * @throws Exception
	 */
	public static void robotClassDown() throws Exception {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

	/**
	 * Function to switch to window
	 */
	public static void switchFrame_id(String frame_id) {
		getWebDriver().switchTo().frame(frame_id);
	}

	public static void switchFrame_parent() {
		DriverManager.getDriver().switchTo().parentFrame();
	}

	/**
	 * Method to Move to Element using Actions and click
	 *
	 * @throws Exception
	 */
	public static void moveToElementActionAndClick(By byLocator, String message) throws Exception {
		try {

            WebElement element = DriverManager.getDriver().findElement(byLocator);
            Actions a = new Actions(DriverManager.getDriver());
            a.moveToElement(element).click().build().perform();
            logger.info("Moved to element " + message);
            ExtentReporter.extentLogger("", "Moved to element and Click " + message);
        } catch (Exception e) {
            logger.error("Failed to move to element and click " + message);
            ExtentReporter.extentLoggerFail("", "Failed to move to element " + message + e);
            throw new AssertionError("Failed to move to element " + byLocator, e);
        }
    }

	/**
	 * This method will wait for element absence till the given time
	 *
	 * @param locator
	 * @param seconds
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static boolean waitForElementInVisible(By locator, int seconds, String message) throws Exception {
		try {
			WebDriverWait w = new WebDriverWait(DriverManager.getDriver(), seconds);
			w.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			logger.info(message + " is displayed");
			ExtentReporter.extentLogger("element is displayed", message + " is displayed");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void Swipe(String direction, int count) {
		touchAction = new TouchAction(DriverManager.getAppiumDriver());
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = DriverManager.getAppiumDriver().manage().window().getSize();
					int startx = (int) (size.width * 0.5);
					int endx = (int) (size.width * 0.1);
					int starty = size.height / 2;
					touchAction.press(PointOption.point(startx, starty))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
							.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = DriverManager.getAppiumDriver().manage().window().getSize();
					int endx = (int) (size.width * 0.8);
					int startx = (int) (size.width * 0.20);
					if (size.height > 2000) {
						int starty = (int) (size.height / 2);
						touchAction.press(PointOption.point(startx, starty))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
								.moveTo(PointOption.point(endx, starty)).release().perform();
					} else {
						int starty = (int) (size.height / 1.5);
						touchAction.press(PointOption.point(startx, starty))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
								.moveTo(PointOption.point(endx, starty)).release().perform();
					}

					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("UP")) {

				for (int j = 0; j < count; j++) {
					Dimension size = DriverManager.getAppiumDriver().manage().window().getSize();
					System.out.println("size : " + size);
					int starty = (int) (size.height * 0.70);// 0.8
					int endy = (int) (size.height * 0.30);// 0.2
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, starty))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
							.moveTo(PointOption.point(startx, endy)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeUp",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			} else if (dire.equalsIgnoreCase("DOWN")) {
				for (int j = 0; j < count; j++) {

					Dimension size = DriverManager.getAppiumDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.70);
					int endy = (int) (size.height * 0.30);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, endy))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
							.moveTo(PointOption.point(startx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeDown",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			}

		} catch (Exception e) {
			logger.error(e);

		}

	}

	public static void assertionValidation(String actual, String expected) throws Exception {

        Assert.assertEquals(actual, expected);
        if (actual.equals(expected)) {
            // softAssert.assertEquals(actual.equals(expected), true);
            logger.info(actual + " and " + expected + " are matched");
            ExtentReporter.extentLoggerPass("Assertion", actual + " and " + expected + " are matched");
        } else {
            // softAssert.assertEquals(actual.equals(expected), false);
            logger.info(actual + " and " + expected + " are not matched");
            ExtentReporter.extentLoggerFail("Assertion", actual + " and " + expected + " are not matched");
            throw new AssertionError(actual + " and " + expected + " are not matched");
        }

	}

	public static void scrollToFirstHorizontalScrollableElement(String textToSearch) {
		DriverManager.getAppiumDriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\""
						+ textToSearch + "\"))"));
	}

	public static void scrollToVertical(String text) {
		((FindsByAndroidUIAutomator<MobileElement>) DriverManager.getAppiumDriver()).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ text + "\").instance(0))");
		logger.info("Swiped until the element " + text + " displayed");
	}

	public static void handleOtp(String otp) throws IOException, InterruptedException {
		for (int i = 0; i < otp.length(); i++) {
			char ch = otp.charAt(i);
			Thread.sleep(1000);
			String cmd = "adb shell input text " + ch + "";
			Thread.sleep(2000);
			Runtime.getRuntime().exec(cmd);
		}
		logger.info("Entered OTP " + otp + " Successfully");
		ExtentReporter.extentLogger("Enter OTP", "Entered OTP " + otp + " Successfully");
	}

	public static MobileElement scrollToElement(String an, String av) {
		return (MobileElement) DriverManager.getAppiumDriver().findElement(MobileBy
				.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + an + "(\"" + av + "\"))"));
	}

	public static void switchPlatformToWeb(String url) {
		String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserType");
		new DriverInstance().LaunchBrowser(browser);
		waitTime(5000);
		getWebDriver().get(url);
		new Utilities().setPlatform("Web");
		System.out.println(getPlatform());
	}

	public static void switchPlatformToAndroid() {
		waitTime(6000);
		new Utilities().setPlatform("Android");
		System.out.println(getPlatform());
		initDriver();

	}

	public static void closeWebBrowser() {
		waitTime(5000);
//		Set<String> win = getWebDriver().getWindowHandles();
//		for (String winHandle : win) {
//			getWebDriver().switchTo().window(winHandle);
			getWebDriver().close();
//		}
	}

	/**
	 * @param locator
	 * @return
	 * @throws Exception
	 * @tap on the center of center for tour dashboard
	 */
	public static int tapOnFirstSuggestion(By locator) throws Exception {
		WebElement element = DriverManager.getAppiumDriver().findElement(locator);
		String tourText = getText(locator);
		Point point = element.getLocation();
		// capturing height & length of the element
		int length = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		// capturing the stating Y co-ordinate
		int getY = point.getY();
		// moving to the middle of the suggestion from the Y co-ordinate
		int middleY = (int) (getY + height * 1.5);
		TouchAction ta = new TouchAction(DriverManager.getAppiumDriver());
		ta.tap(PointOption.point(length / 2, middleY)).perform();
		logger.info(tourText + " dashboard tour is displayed Tapped at the center of Screen");
		ExtentReporter.extentLoggerPass("Tour", tourText + " dashboard tour is displayed Tapped at the center of Screen");
		return middleY;
	}

//	public static void suggestionTapMidScreen(By locator) throws Exception {
//		int guideSuggestion = 1;
//		for (int i = 0; i < guideSuggestion; i++) {
//			tapOnFirstSuggestion(locator);
//			if (verifyElementDisplayed(locator)) {
//				guideSuggestion++;
//			} else {
//				break;
//			}
//		}
//	}

	public static boolean verifyElementAvailable(By byLocator, String validationtext) throws Exception {

		if (isPresentWithWait(byLocator)) {
			WebElement element = findElement(byLocator);
			logger.info(validationtext + " is displayed");
			ExtentReporter.extentLoggerPass("checkElementPresent", validationtext + " is displayed");
			return true;
		} else {
			logger.info(validationtext + " is not displayed");
			ExtentReporter.extentLogger("checkElementPresent", validationtext + " is not displayed");
			return false;
		}
	}

	public static void SwipeBanner(By byLocator, String direction, int k) throws Exception {
		TouchAction action = new TouchAction(DriverManager.getAppiumDriver());
		waitTime(1000);
		WebElement ele = DriverManager.getAppiumDriver().findElement(byLocator);
		Dimension size = DriverManager.getAppiumDriver().manage().window().getSize();

		int startX = Math.toIntExact(Math.round(size.getWidth() * 0.70));
		int startY = Math.toIntExact(Math.round(size.getHeight() * 0.25));
		int endX = Math.toIntExact(Math.round(size.getWidth() * 0.10));
		int endY = 0;
		for (int i = 1; i <= k; i++) {
			if (direction.equalsIgnoreCase("Left")) {
				action.press(PointOption.point(startX, startY))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
						.moveTo(PointOption.point(endX, startY)).release().perform();
				logger.info("Swiping Banner in " + " " + direction + " direction");
				ExtentReporter.extentLogger("SwipeLeft", "Swiping Banner in " + " " + direction + " direction");
			} else {
				action.press(PointOption.point(startX, endY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
						.moveTo(PointOption.point(startX, startY)).release().perform();
			}
		}
	}

    public static void tapUsingCoordinates(int x, int y) throws Exception {
        TouchAction t = new TouchAction(DriverManager.getAppiumDriver());
        try {
            t.tap(PointOption.point(x, y)).perform().release();
            logger.info("Tapped on " + x + "," + y + " co-ordinates");
            ExtentReporter.extentLoggerPass("Tap", "Tapped on " + x + " , " + y + " co-ordinates");
        } catch (Exception e) {
            logger.info("Failed to tap on" + x + "," + y + " co-ordinates");
            ExtentReporter.extentLoggerFail("Tap", "Failed to Tap on" + x + "," + y + " co-ordinates " + e);
            throw new AssertionError("Tap " + "Failed to Tap on " + x + "," + y + " co-ordinates", e);
        }
    }

    public static void clearTextField(By byLocator, String FieldName) throws Exception {
		WebElement element = findElement(byLocator);
		element.clear();
		logger.info("Typed the value into " + FieldName);
//		ExtentReporter.extentLoggerPass("", "Typed the value " + value + " into " + FieldName);

	}

	public static void typeWeb(By byLocator, String value, String FieldName) {
		try {
			waitTime(2000);
			if (!getPlatform().equals("Web")) {
				Actions a = new Actions(getDriver());
				a.sendKeys(value);
				a.perform();
			} else {
				WebElement element = findElement(byLocator);
				element.clear();
				element.sendKeys(value);
			}
			value = value.split("\n")[0];

            logger.info("Typed the value " + value + " into " + FieldName);
            ExtentReporter.extentLoggerPass("", "Typed the value " + value + " into " + FieldName);
        } catch (Exception e) {
            logger.error(e);
            try {
                ExtentReporter.extentLoggerFail("Fail", "Unable to Type the value " + value + " into " + FieldName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new AssertionError("Unable to Type the value " + value + " into " + FieldName,e);
        }
    }

	public static void clearWebField() throws AWTException {
		Robot r = new Robot();
		r.keyPress(VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(VK_CONTROL);
		r.keyRelease(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_BACK_SPACE);
		r.keyRelease(KeyEvent.VK_BACK_SPACE);
	}

	// Element clicable
	public static boolean isClickable(By locator, String str) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 20);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			logger.info(str + " Element is clickable");
			ExtentReporter.extentLoggerPass("Clickable", str + " Element is clickable");
			return true;
		} catch (Exception e) {
			logger.info(str + " Element is not clickable");
			ExtentReporter.extentLoggerPass("Clickable", str + " Element is not clickable");
			return false;
		}
	}

	public static boolean isElementClickableWeb(By locator, String str) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(getWebDriver(), 20);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			logger.info(str + " Element is clickable");
			ExtentReporter.extentLoggerPass("Clickable", str + " Element is clickable");
			return true;
		} catch (Exception e) {
			logger.info(str + " Element is not clickable");
			ExtentReporter.extentLoggerPass("Clickable", str + " Element is not clickable");
			return false;
		}
	}

	/**
	 * clear the text field
	 *
	 * @param byLocator
	 */
	public static void clearWebField(By byLocator, String text) {
		try {
			findElement(byLocator).clear();
			logger.info("Cleared the text in : " + text);
			ExtentReporter.extentLogger("clear text", "Cleared the text in : " + text);
		} catch (Exception e) {
			logger.error(e);
		}
	}

    /**
     * Mouse Hover
     *
     * @param byLocator
     * @throws Exception
     */
    public static void mouseHover(By byLocator) throws Exception {
        WebElement ele = findElement(byLocator);
        Actions action = new Actions(getWebDriver());
        action.moveToElement(ele).perform();
    }

    public static void deleteRecentDownloadedFile() {
        String cmd1 = "adb shell ls /sdcard/download";
        String osName = System.getProperty("os.name").toLowerCase();
        Process listName;
        try {
            if (osName.contains("mac") || osName.contains("linux")) {
                listName = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", cmd1});
            } else {
                listName = Runtime.getRuntime().exec(cmd1);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(listName.getInputStream()));
            String fileList = br.readLine();
            while (fileList != null) {
                if (fileList.contains("frame")) {
                    String cmd2 = "adb shell rm -f /sdcard/Download/" + fileList;
                    if (osName.contains("mac") || osName.contains("linux")) {
                        Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", cmd2});
                    } else {
                        Runtime.getRuntime().exec(cmd2);
                    }

                    logger.info("File deleted");
                    ExtentReporter.extentLogger("Delete", "File deleted");
                }
                fileList = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public static void swipeTheElementHorizontally(By locator) throws Exception {
		WebElement element = findElement(locator);

        // Get the center coordinates of the element
        int centerX = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int centerY = element.getLocation().getY() + element.getSize().getHeight() / 2;

        // Set the start and end coordinates of the swipe
        int startX = centerX;
        int endX = centerX - 800;
        int startY = centerY;
        int endY = centerY;

        // Create a new TouchAction instance
        TouchAction action = new TouchAction(DriverManager.getAppiumDriver());

        // Perform the swipe
        action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY)).release().perform();


    }

    public static void reopenGCash() {
        DriverManager.getAppiumDriver().activateApp("com.globe.gcash.android.uat.tokyo");
    }

    public static void closeMobileChromeApp() {
        DriverManager.getAppiumDriver().closeApp();
    }

    public static void launchMobileChromeAppWithURL(String url) throws MalformedURLException {
        new DriverInstance().pwaLauch();
        DriverManager.getAppiumDriver().get(url);
    }

    public static void backToGCash() {
        closeMobileChromeApp();
        switchPlatformToAndroid();
        reopenGCash();
    }

    /**
     * @param locator
     * @return
     * @throws Exception
     * @tap on the center of center for tour dashboard
     */
    public static int tapOnSuggestion(By locator) throws Exception {
        WebElement element = DriverManager.getAppiumDriver().findElement(locator);
        String tourText = getText(locator);
        Point point = element.getLocation();
        // capturing height & length of the element
        int length = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        // capturing the stating Y co-ordinate
        int getY = point.getY();
        // moving to the middle of the suggestion from the Y co-ordinate
        int middleY = (int) (getY + height * 1.5);
        TouchAction ta = new TouchAction(DriverManager.getAppiumDriver());
        ta.tap(PointOption.point(length / 2, middleY)).perform();
        return middleY;
    }

    /**
     * @return
     * @throws ParseException
     * @method Get current date and time
     */
    public static String getCurrentDateTime(String continentCountry) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        TimeZone philippines = TimeZone.getTimeZone(continentCountry);
        formatter.setTimeZone(philippines);
        return formatter.format(new Date());
    }

    /**
     * @return
     * @throws ParseException
     * @method time
     */
    public static String getTime(String continentCountry) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        TimeZone philippines = TimeZone.getTimeZone(continentCountry);
        formatter.setTimeZone(philippines);
        return formatter.format(new Date());
    }

	public static void findElementAndVerifyText(By byLocator, String expectedtext)throws Exception {
		WebElement element = null;
		String nameWithProperSpacing="";
		try {
			if(getPlatform().equalsIgnoreCase("web")){
				element = getWebDriver().findElement(byLocator);
			}else{
				element = DriverManager.getAppiumDriver().findElement(byLocator);
			}
			nameWithProperSpacing = element.getText().replaceAll("\\s+", " ");
			explicitWaitVisibility(byLocator, 20);
			if (element.isDisplayed()) {
				softAssert.assertEquals(nameWithProperSpacing, expectedtext);
				logger.info(nameWithProperSpacing + " is displayed and matched");
				ExtentReporter.extentLoggerPass("Pass",nameWithProperSpacing+ " is displayed and matched");
			}
		} catch (Exception e) {
			softAssert.assertEquals(true, false,expectedtext+" Element Not able to find");
			logger.error(element.getText() +" is not displayed and matched");
			ExtentReporter.extentLoggerFail("Fail", nameWithProperSpacing + " is not displayed and matched");
		}
	}
	public static void findElementAndVerifyTextWeb(By byLocator, String expectedtext)throws Exception {
		WebElement element = null;
		try {
			element = getWebDriver().findElement(byLocator);
			String nameWithProperSpacing = element.getText().replaceAll("\\s+", " ");
			explicitWaitVisibility(byLocator, 20);
			if (element.isDisplayed()) {
				softAssert.assertEquals(nameWithProperSpacing, expectedtext);
				softAssert.assertAll();
				logger.info(nameWithProperSpacing + " is displayed and matched");
				ExtentReporter.extentLoggerPass("Pass",nameWithProperSpacing+ " is displayed and matched");
			} else {
				logger.error(element.getText() +" is not displayed and matched");
				ExtentReporter.extentLoggerFail("Fail", nameWithProperSpacing + " is not displayed and matched");
			}
		} catch (AssertionError e) {
			logger.error(e.getMessage());
			ExtentReporter.extentLoggerFail("testcase", e.getMessage());
		}
	}


	public static void findElementAndVerifyText(By byLocator, String expectedtext, String title)throws Exception {
		WebElement element = null;
		String nameWithProperSpacing = "";
		try {
			if (getPlatform().equalsIgnoreCase("web")) {
				element = getWebDriver().findElement(byLocator);
			} else {
				element = DriverManager.getAppiumDriver().findElement(byLocator);
			}
			nameWithProperSpacing = element.getText().replaceAll("\\s+", " ");
			explicitWaitVisibility(byLocator, 20);
			if (element.isDisplayed()) {
				softAssert.assertEquals(nameWithProperSpacing, expectedtext);
                logger.info(nameWithProperSpacing + " "+ title+ " is displayed and matched");
				ExtentReporter.extentLoggerPass("Pass",nameWithProperSpacing +" "+title+" is displayed and matched");
			} else {
				logger.error(element.getText() +" "+ title+" is not displayed and matched");
				ExtentReporter.extentLoggerFail("Fail", nameWithProperSpacing +" "+title+" is not displayed and matched");
                throw new AssertionError("title is not displayed and matched");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ExtentReporter.extentLoggerFail("testcase", e.getMessage());
            throw new AssertionError("title is not displayed and matched", e);
        }
	}
	/**
	 * @return
	 * @throws ParseException
	 * @method time
	 */
	public static String getDay(String continentCountry) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		TimeZone philippines = TimeZone.getTimeZone(continentCountry);
		formatter.setTimeZone(philippines);
		return formatter.format(new Date());
	}
	public static void swipeDownUntilElementVisible(String text) {
		((FindsByAndroidUIAutomator<MobileElement>) DriverManager.getAppiumDriver()).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));");
		logger.info("Swiped until the element " + text + " displayed");
	}


    public static boolean swipeElementAndroid(By particularBanner, By tillElement) throws Exception {
        boolean elementFound = false;
        // Get location of element you want to swipe
        WebElement banner = DriverManager.getAppiumDriver().findElement(particularBanner);
        //PointOption<PointOption<T> bannerPoint = banner.getLocation();
        // Get size of device screen


        //Dimension elementSize = banner.getSize();
//		int elementhight=elementSize.getHeight();
//		int elementWid=elementSize.getWidth();
        // Get start and end coordinates for horizontal swipe


        TouchAction action = new TouchAction(DriverManager.getAppiumDriver());
        Dimension screenSize = DriverManager.getAppiumDriver().findElement(particularBanner).getSize();
        System.out.println(screenSize.toString());
        int startX = Math.toIntExact(Math.round(screenSize.getWidth() * 0.50));
        int startY = Math.toIntExact(Math.round(screenSize.getHeight() * 0.50));
        int endX = Math.toIntExact(Math.round(screenSize.getWidth() * 0.10));

        int endY = 0;
        for (int i = 0; i <= 4; i++) {

            try {
                WebElement element = findElement(tillElement);

                elementFound = element.isDisplayed();

                // elementFound=verifyElementDisplayed(tillElement);

            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Swap-------Element Searching");
                if (elementFound == false) {

					action.press(PointOption.point(startX, startY))
							.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
							.moveTo(PointOption.point(endX, startY)).release();
					DriverManager.getAppiumDriver().performTouchAction(action);
				}
				elementFound = false;
			}
			if (elementFound == true)
				break;
		}
		return elementFound;
	}
	public static boolean waitForElementAndClickIfPresent_API(By locator, String message) throws Exception {
		if (getPlatform().equals("Web")) {
			try {
				getWebDriver().findElement(locator).click();
				logger.info("Clicked element " + message);
				return true;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		return relaunch;
	}


    public static boolean waitForElementAndClickIfPresent_API(By locator, int seconds, String message) throws Exception {
        if (getPlatform().equals("Web")) {

            try {
                for (int time = 0; time <= seconds; time++) {
//					WebDriverWait wait = new WebDriverWait(getWebDriver(), seconds);
//					wait.until(ExpectedConditions.elementToBeClickable(locator));
                    getWebDriver().findElement(locator).click();
                    logger.info("Clicked element " + message);
                    return true;
                }
            } catch (Exception e) {
                Thread.sleep(1000);
            }

        }
        return relaunch;
    }

    public static void typeWeb_API(By byLocator, String value, String FieldName) throws Exception {
        WebElement element = findElement(byLocator);
        element.sendKeys(value);
        logger.info("Typed the value " + value + " into " + FieldName);
    }

	/**
	 * clear the text field
	 *
	 * @param byLocator
	 */
	public static void clearWebField_API(By byLocator, String text) {
		try {
			findElement(byLocator).clear();
			logger.info("Cleared the text in : " + text);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public static boolean verifyElementAvailable_API(By byLocator, String validationtext) throws Exception {

        if (isPresentWithWait(byLocator)) {
            WebElement element = findElement(byLocator);
            logger.info(validationtext + " is displayed");
//				ExtentReporter.extentLoggerPass("checkElementPresent", validationtext + " is displayed");
            return true;
        } else {
            logger.info(validationtext + " is not displayed");
//				ExtentReporter.extentLogger("checkElementPresent", validationtext + " is not displayed");
			return false;
		}
	}
	public static boolean JSClick_API(By byLocator, String text) throws Exception {
		try {
			js = (JavascriptExecutor) getWebDriver();
			js.executeScript("arguments[0].click();", getWebDriver().findElement(byLocator));
			logger.info("" + text + " " + " is clicked");
			return true;
		} catch (Exception e) {
			logger.error(text + " " + " is not clicked");
//				e.printStackTrace();
			return false;
		}
	}
	/**
	 * Function to generate Random String of length 4
	 *
	 * @return
	 */
	public static String generateRandomString(int size) {
		String strNumbers = "abcdefghijklmnopqrstuvwxyzacvbe";
		Random rnd = new Random();
		StringBuilder strRandomNumber = new StringBuilder(9);
		strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		String s1 = strRandomNumber.toString().toUpperCase();
		for (int i = 1; i < size; i++) {
			strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		}
		return s1 + strRandomNumber.toString();
	}


	public static void softAssertVerifyText(String acualText , String expectedtext)throws Exception {
		try {
			Wait(200);
			if (acualText!=null) {
				softAssert.assertEquals(acualText, expectedtext);
				logger.info(acualText +" And "+ expectedtext + "   is  matched");
//				ExtentReporter.extentLoggerPass("Pass",acualText +" And "+ expectedtext + " is matched");
			} else {
				logger.error( acualText +" And "+ expectedtext + "   is not displayed and matched");
//				ExtentReporter.extentLoggerFail("Fail", acualText +" And "+ expectedtext  + " is not displayed and matched");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
//			ExtentReporter.extentLoggerFail("testcase", e.getMessage());
		}
	}
	/**
	 * This Method Is To Validate The Double Values
	 *
	 */

    public static void assertionValidation(Double actual, Double expected) throws Exception {

        softAssert.assertEquals(actual, expected);
        if (actual.equals(expected)) {

            logger.info(actual + " and " + expected + " are matched");
            ExtentReporter.extentLoggerPass("Assertion", actual + " and " + expected + " are matched");
        } else {

            logger.info(actual + " and " + expected + " are not matched");
            ExtentReporter.extentLoggerFail("Assertion", actual + " and " + expected + " are not matched ");
            throw new AssertionError(actual + " and " + expected + " are not matched");
        }
        softAssert.assertAll();
    }

    /**
     * This Method is to swipe the carousels horizantally
     *
     * @param : locator
     * @param : distance
     * @throws Exception
     */
    public static void swipeTheElementHorizontally(By locator, int distance) throws Exception {
        WebElement element = findElement(locator);

		// Get the center coordinates of the element
		int centerX = element.getLocation().getX()+element.getSize().getWidth()/2;
		int centerY = element.getLocation().getY()+element.getSize().getHeight()/2;

		// Set the start and end coordinates of the swipe
		int startX = centerX;
		int endX = centerX-distance;
		int startY = centerY;
		int endY = centerY;

        // Create a new TouchAction instance
        TouchAction action = new TouchAction(DriverManager.getAppiumDriver());


    }

    public static double addDoubleValue(String balance, double doubleVal) {
        try {
            amount = Double.parseDouble(balance) + doubleVal;
        } catch (Exception e) {

        }

        return amount;

	}
	/**
	 * Click on a web element.
	 *
	 * @param : byLocator the by locator
	 */
	public static void clickBtn(By byLocator, String validationtext) throws Exception {
		//String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		try {
			if (getPlatform().equalsIgnoreCase("BrowserStack") || getPlatform().equalsIgnoreCase("Android") || getPlatform().equalsIgnoreCase("IOSBrowserStack")) {
				AndroidElement element = (AndroidElement) DriverManager.getAppiumDriver().findElement(byLocator);
				element.click();
			} else if (getPlatform().equalsIgnoreCase("mpwa")) {
				WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
				element.click();
			} else if (getPlatform().equalsIgnoreCase("iOS")) {
				WebElement element = getDriver().findElement(byLocator);
				element.click();
			} else if (getPlatform().equalsIgnoreCase("web")) {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				element.click();
			}
			logger.info("Clicked on " + validationtext);
			ExtentReporter.extentLoggerPass("click", "Clicked on " + validationtext);
		} catch (Exception e) {
			screencapture();
			logger.info("Not clicked on " + validationtext);
			ExtentReporter.extentLogger("click", "Not Clicked on " + validationtext);
		}
	}

    public static void findElementAndVerify(String actualext, String expectedtext) throws Exception {
        WebElement element = null;
        try {
            softAssert.assertEquals(actualext, expectedtext);
            logger.info(actualext + " is displayed and matched");
            ExtentReporter.extentLoggerPass("Pass", actualext + " is displayed and matched");
        } catch (Exception e) {
            logger.error(actualext + " is not displayed and matched");
            ExtentReporter.extentLoggerFail("Fail", actualext + " is not displayed and matched " + e);
            throw new AssertionError(actualext + " is not displayed and matched", e);
        }
    }

    public static void findElementAndVerifyText_API(By byLocator, String expectedtext) throws Exception {
        WebElement element = null;
        try {
            element = getWebDriver().findElement(byLocator);
            String nameWithProperSpacing = element.getText().replaceAll("\\s+", " ");
            explicitWaitVisibility(byLocator, 20);
            if (element.isDisplayed()) {
                softAssert.assertEquals(nameWithProperSpacing, expectedtext);
                logger.info(nameWithProperSpacing + " is displayed and matched");
            } else {
                logger.error(element.getText() + " is not displayed and matched");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static boolean SwipeUp_API() throws Exception {
        try {
            js = (JavascriptExecutor) getWebDriver();
            js.executeScript("window.scrollBy(0,550)", "");
            logger.info("Swipe Up Successfully");
            return true;
        } catch (Exception e) {
            logger.error("Swipe Up Failed");
            return false;
        }
    }

	public static void DoubleClickWeb(By locator, String OptionName) {
		try {
			explicitWaitVisibleWeb(locator, 10);
			Actions act = new Actions(getWebDriver());
			WebElement ele = getWebDriver().findElement(locator);
			act.doubleClick(ele).build().perform();
			logger.info("Clicked on the " + OptionName);
			ExtentReporter.extentLogger("Click", " " + OptionName);
		} catch (NoSuchElementException e) {
			logger.info("Did not click on " + OptionName);
			ExtentReporter.extentLogger("Did not Click on", " " + OptionName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void explicitWaitVisibleWeb(By byLocator, int time) throws Exception {
		WebDriverWait wait = new WebDriverWait(getWebDriver(), time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}
	public static void clearWebField(By locator) {
		WebElement element = getWebDriver().findElement(locator);
		while (!element.getAttribute("value").equals("")) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}



    public static void assertionValidation(Object actual, Object expected) throws Exception {

        Assert.assertEquals(actual, expected);
        if (actual.equals(expected)) {
            // softAssert.assertEquals(actual.equals(expected), true);
            logger.info(actual + " and " + expected + " are matched");
            ExtentReporter.extentLoggerPass("Assertion", actual + " and " + expected + " are matched");
        } else {
            // softAssert.assertEquals(actual.equals(expected), false);
            logger.info(actual + " and " + expected + " are not matched");
            ExtentReporter.extentLoggerFail("Assertion", actual + " and " + expected + " are not matched");
            throw new AssertionError("Assertion" + actual + " and " + expected + " are not matched");
        }

    }

    public static void findElementAndVerifyTextAndRemoveLongSpace(By byLocator, String expectedtext) throws Exception {
        WebElement element = null;
        try {
            element = DriverManager.getAppiumDriver().findElement(byLocator);
            explicitWaitVisibility(byLocator, 20);
            if (element.isDisplayed()) {
                String actual = getText(byLocator).replaceAll("\\s+", " ");
                softAssert.assertEquals(actual, expectedtext);
                logger.info(element.getText() + "_is displayed and matched");
                ExtentReporter.extentLoggerPass("Pass", element.getText() + "::::is displayed and matched");
            } else {
                logger.error(element.getText() + "::::is not displayed and matched");
                ExtentReporter.extentLoggerFail("Fail", element.getText() + "::::is not displayed and matched");
                throw new AssertionError("Fail " + element.getText() + "::::is not displayed and matched");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ExtentReporter.extentLoggerFail("testcase", e.getMessage());
            throw new AssertionError("Fail " + element.getText() + "::::is not displayed and matched", e);
        }
    }

	public static void webFieldClear(By locator){
		WebElement element = getWebDriver().findElement(locator);
		while(!element.getAttribute("value").equals("")){
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

    /**
     * Robot Function to press Down key
     *
     * @throws Exception
     */
    public static void robotClassDownArrow() throws Exception {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    /**
     * Robot Function to press Enter key
     *
     * @throws Exception
     */
    public static void robotClassPressEnterKey() throws Exception {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }


    /**
     * Robot Function to press Tab key
     *
     * @throws Exception
     */
    public static void robotClassPressTabKey() throws Exception {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }


    /**
     * Robot Function to press Space key
     *
     * @throws Exception
     */
    public static void robotClassPressSpaceKey() throws Exception {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
    }

    /**
     * Robot Function to file upload
     *
     * @throws Exception
     */
    public static void fileUploadUsingRobotClass(By element, int timeout, String message, String filePath) throws Exception {
        waitForElementAndClickIfPresent(element, timeout, message);
        waitTime(1000);
        Robot rb = new Robot();

        // copying File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        // press Contol+V for pasting
        rb.keyPress(VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        waitTime(1000);
    }

	/**
	 * Robot Function to type text
	 *
	 * @throws Exception
	 */
	public static void typeTextUsingRobotClass(String text) throws AWTException {
		Robot rb = new Robot();
		// copying File path to Clipboard
		StringSelection str = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

		// press Contol+V for pasting
		rb.keyPress(VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);

		// release Contol+V for pasting
		rb.keyRelease(VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);

		// for pressing and releasing Enter
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		waitTime(1000);
	}
	public static boolean verifyElementAvailables(By byLocator) throws Exception {

		if (isPresentWithWait(byLocator)) {
			WebElement element = findElement(byLocator);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Robot Function to file upload
	 *
	 * @throws Exception
	 */
//	public static void fileUploadUsingRobotClassWeb(By element, int timeout, String message, String filePath) throws Exception {
//		if(!verifyElementDisplayed(element)) {
//			waitForElementAndClickIfPresent(element, timeout, message);
//		}
//		waitTime(1000);
//		Robot rb = new Robot();
//		int controlKey = IS_OS_MAC ? VK_META : VK_CONTROL;
//		// copying File path to Clipboard
//		StringSelection str = new StringSelection(filePath);
//		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
//
//		// press Contol+V for pasting
//		rb.keyPress(controlKey);
//		rb.keyPress(KeyEvent.VK_V);
//
//		// release Contol+V for pasting
//		rb.keyRelease(controlKey);
//		rb.keyRelease(KeyEvent.VK_V);
//
////		rb.keyPress(VK_TAB);
////		rb.keyRelease(VK_TAB);
//		// for pressing and releasing Enter
//		rb.keyPress(KeyEvent.VK_ENTER);
//		rb.keyRelease(KeyEvent.VK_ENTER);
//		waitTime(1000);
//	}

	public static void findElementAndVerifyTextWebUsingAttribute(By byLocator, String expectedtext) throws Exception {
		WebElement element = null;
		try {
			element = getWebDriver().findElement(byLocator);
			String nameWithProperSpacing = element.getAttribute("value").replaceAll("\\s+", " ");
			explicitWaitVisibility(byLocator, 20);
			if (element.isDisplayed()) {
				softAssert.assertEquals(nameWithProperSpacing, expectedtext);
				logger.info(nameWithProperSpacing + " is displayed and matched");
				ExtentReporter.extentLoggerPass("Pass", nameWithProperSpacing + " is displayed and matched");
			} else {
				logger.error(element.getText() + " is not displayed and matched");
				ExtentReporter.extentLoggerFail("Fail", nameWithProperSpacing + " is not displayed and matched");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ExtentReporter.extentLoggerFail("testcase", e.getMessage());
		}
	}

	public static boolean waitUntilElementDisplayed (By locator, int countWait,String validationText) throws Exception {
		int iterator=0;
		while (verifyElementDisplayed(locator,validationText)== false) {
			if (iterator <= countWait) {
				waitTime(1000);
				iterator++;
			}
			else {
				return false;
			}
		}
		return true;
	}
	public static void findElementAndValidate(By byLocator, String expectedtext) throws Exception {
		WebElement element = null;
		try {
			if (getPlatform().equalsIgnoreCase("web")) {
				element = getWebDriver().findElement(byLocator);
			} else {
				element = DriverManager.getAppiumDriver().findElement(byLocator);
			}
			explicitWaitVisibility(byLocator, 20);
			if (element.isDisplayed()) {
				softAssert.assertEquals(element, expectedtext);
				logger.info(element + " is displayed and matched");
				ExtentReporter.extentLoggerPass("Pass", element + " is displayed and matched");
			}
		} catch (Exception e) {
			softAssert.assertEquals(true, false, expectedtext + " Element Not able to find");
			logger.error(element.getText() + " is not displayed and matched");
			ExtentReporter.extentLoggerFail("Fail", element + " is not displayed and matched");
		}
	}
    public static boolean verifyElementIsEnabledWeb(By byLocator, String str) throws Exception {

        try {
            WebElement element = getWebDriver().findElement(byLocator);
            if (element.isEnabled()) {
                ExtentReporter.extentLogger("checkElementPresent", "" + str + " is Enabled");
                logger.info("" + str + " is Enabled");
                return true;
            }
        } catch (Exception e) {
            ExtentReporter.extentLogger("checkElementPresent", "" + str + " is Disabled");
            logger.info(str + " is Disabled");
            return false;
        }
        return false;
    }

    public static boolean verifyElementIsDisabledWeb(By byLocator, String str) throws Exception {

        try {
            waitTime(2000);
            WebElement element = getWebDriver().findElement(byLocator);
            boolean status = element.isEnabled();
            if (status=false) {
                ExtentReporter.extentLogger("checkElementPresent", "" + str + " is Disabled");
                logger.info("" + str + " is Enabled");
                return true;
            }
        } catch (Exception e) {
            ExtentReporter.extentLogger("checkElementPresent", "" + str + " is Enabled");
            logger.info(str + " is Enabled");
            return false;
        }
        return false;
    }
    public static void findElementAndVerifyTextAndRemoveLongSpaceWeb(By byLocator, String expectedtext) throws Exception {
        WebElement element = null;
        try {
            element = getWebDriver().findElement(byLocator);
            explicitWaitVisibility(byLocator, 20);
            if (element.isDisplayed()) {
                String actual = getText(byLocator).replaceAll("\\s+", " ");
                softAssert.assertEquals(actual, expectedtext);
                logger.info(element.getText() + "_is displayed and matched");
                ExtentReporter.extentLoggerPass("Pass", element.getText() + "::::is displayed and matched");
            } else {
                logger.error(element.getText() + "::::is not displayed and matched");
                ExtentReporter.extentLoggerFail("Fail", element.getText() + "::::is not displayed and matched");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ExtentReporter.extentLoggerFail("testcase", e.getMessage());
        }
    }
    public  static void assertNotEquals(String actual, String expected) throws Exception {
        softAssert.assertNotEquals(actual,expected);
        if(actual!=expected)
        {
            logger.info(actual+" and "+expected+" are not matched");
            extentLoggerPass("Assertion",actual+" and "+expected+" are not matched");
        }else {
            logger.info(actual+" and "+expected+" are matched");
            extentLoggerFail("Assertion",actual+" and "+expected+" are matched");
        }
    }

    public static void validateRedirectedUrl(String urlToCheck) throws Exception {
        WebDriver driver = getWebDriver();
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
        try {
            String currentUrl = driver.getCurrentUrl();
            String paymongoUrl = urlToCheck;

            if (currentUrl == null || paymongoUrl == null) {
                throw new IllegalArgumentException("One or both URLs are null.");
            }

            logger.info("Redirected to" + currentUrl + "Validated successfully");

            softAssert.assertNotEquals(currentUrl, paymongoUrl);

            String redirectedUrl = driver.getCurrentUrl();

            if (redirectedUrl.contains(paymongoUrl)) {
                logger.info("Redirected Url Contains " + paymongoUrl);
                extentLoggerPass("Assertion", redirectedUrl + " contains " + paymongoUrl);
                logger.info( redirectedUrl + " contains " + paymongoUrl);
            } else {
                logger.info(redirectedUrl + " does not contain " + paymongoUrl);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

}


