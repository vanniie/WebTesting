package com.driverInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.SkipException;
import com.propertyfilereader.PropertyFileReader;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import io.appium.java_client.AppiumDriver;

public class Drivertools {

	private String host;
	private int port;
	private String deviceName;
	private static String platform;
	private int appTimeOut;
	private String remoteUrl;
	private String appPackage;
	private String appActivity;
	private String appVersion;
	private String APkFileName;
	private PropertyFileReader handler;
	private static String testName;
	private String browserType;
	private String url = "";
	public static String runModule;
	private URLConnection connection;
	private URL connectURL;
	public static boolean startTest = false;
	private static String runMode = "null";
	private static String driverVersion = "";
	public static boolean click = true;
	public static String methodName = "";
	Date date = new Date();
	long StartTime;
	public static Instant startTime ;
	public static Duration timeElapsed ;
	private static String DeviceList;
	private static String apk;
	public static String apkName = null;
	public static String dir = System.getProperty("user.dir") + "\\APK\\";
	public static String osName=System.getProperty("os.name").toLowerCase();

	private String userID;
	private String userKey;
	private String appID;
	private String bsRemoteUrl;

	public Drivertools() {

	}

	public static String getApk() {
		return apk;
	}

	public static void setApk(String apk) {
		Drivertools.apk = apk;
	}

	public static String getDeviceList() {
		return DeviceList;
	}

	public static void setDeviceList(String deviceList) {
		DeviceList = deviceList;
	}

	public static String getTestName() {
		return testName;
	}

	@SuppressWarnings("static-access")
	public void setTestName(String testName) {
		this.testName = testName;
	}
	protected String getBSuserID() {
		return this.userID;
	}

	protected void setBSuserID(String userID) {
		this.userID = userID;
	}

	protected String getBSuserKey() {
		return this.userKey;
	}

	protected void setBSuserKey(String userKey) {
		this.userKey = userKey;
	}

	protected String getBSappID() {
		return this.appID;
	}

	protected void setBSappID(String appID) {
		this.appID = appID;
	}

	protected String getBSremoteUrl() {
		return this.bsRemoteUrl;
	}

	protected void setBSremoteUrl(String bsRemoteUrl) {
		this.bsRemoteUrl = bsRemoteUrl;
	}

	public static ThreadLocal<AppiumDriver<WebElement>> tlDriver = new ThreadLocal<>();

	public static  ThreadLocal<AppiumDriver<WebElement>> driverTV = new ThreadLocal<>();

	protected DesiredCapabilities capabilities = new DesiredCapabilities();

	private static String ENV = "";

	/** The Constant logger. */
	static LoggingUtils logger = new LoggingUtils();

	public static AppiumDriver<WebElement> getDriver() {
		return tlDriver.get();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public static String getPlatform() {
		return platform;
	}

	public static void setPlatform(String Platform) {
		platform = Platform;
	}

	protected int getappTimeOut() {
		return appTimeOut;
	}

	protected void setappTimeOut(int timeOut) {
		this.appTimeOut = timeOut;
	}

	protected String getremoteUrl() {
		return this.remoteUrl;
	}

	protected void setremoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	protected void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	protected String getAppPackage() {
		return this.appPackage;
	}

	protected void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}

	protected String getappActivity() {
		return this.appActivity;
	}

	protected void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	protected String getAppVersion() {
		return this.appVersion;
	}

	protected String getAPKName() {
		return this.APkFileName;
	}

	protected void setAPKName(String apkName) {
		this.APkFileName = apkName;
	}

	protected PropertyFileReader getHandler() {
		return handler;
	}

	protected void setHandler(PropertyFileReader handler) {
		this.handler = handler;
	}

	protected void setBrowserType(String BrowserType) {
		this.browserType = BrowserType;
	}

	protected String getBrowserType() {
		return this.browserType;
	}

	protected void setURL(String url) {
		this.url = url;
	}

	protected String getURL() {
		return this.url;
	}

	protected String runMode() {
		return this.runMode();
	}

	@SuppressWarnings("static-access")
	protected void setRunModule(String runModule) {
		this.runModule = runModule;
	}

	public static String getRunModule() {
		return runModule;
	}

	@SuppressWarnings("static-access")
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	@SuppressWarnings("static-access")
	public String getRunMode() {
		return this.runMode;
	}

	@SuppressWarnings("static-access")
	public void setENV(String env) {
		this.ENV = env;
	}

	public static String getENV() {
		return ENV;
	}

	public static String getDriverVersion() {
		return driverVersion;
	}

	@SuppressWarnings("static-access")
	public void setDriverVersion(String driverVersion) {

		this.driverVersion = driverVersion;
	}

	public static void setPlatfrom(String Platform) {
		platform = Platform;}


	public static String getENvironment() {
		return "<h5> ENV : <a href=\""+getENV()+"\" onclick='return "+click+";'\">"+getENV()+"</a></h5>";
	}

	public Drivertools(String application,String deviceName,String portno) {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		setHandler(new PropertyFileReader("properties/Execution.properties"));
		setHost(getHandler().getproperty("HOST_IP"));
		setPort(Integer.parseInt(getHandler().getproperty("HOST_PORT")));
		setappTimeOut(Integer.parseInt(getHandler().getproperty("APP_TIMEOUT")));
		setremoteUrl("http://" + getHost() + ":" + portno + "/wd/hub");
		if(platform.equalsIgnoreCase("BrowserStack") || platform.equalsIgnoreCase("IOSBrowserStack")) {
			setBSuserID(getHandler().getproperty("userID"));
			setBSuserKey(getHandler().getproperty("accessKey"));
			setBSremoteUrl("http://" + getHost() + "/wd/hub");
		}

		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));
		setAppPackage(getHandler().getproperty(application + "Package"));
		setAppActivity(getHandler().getproperty(application + "Activity"));
		setAppVersion(getHandler().getproperty(application + "Version"));
		setAPKName(getHandler().getproperty(application + "apkfile"));
		setDriverVersion(getHandler().getproperty("DriverVersion"));
		if(platform.equalsIgnoreCase("BrowserStack")) {
			setBSappID(getHandler().getproperty("appID"));
		}else if(platform.equalsIgnoreCase("IOSBrowserStack")) {
			setBSappID(getHandler().getproperty("iOSappID"));
		}
	}

	{
		setPlatform(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName());
		setTestName(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName());
		setBrowserType(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserType"));
		setURL(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("url"));
		setRunModule(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("runModule"));
		setRunMode(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("runMode"));
		if (getPlatform().equals("Android")) {
			setDeviceList(getListOfDevicesConnected().get(0).toString());
		}

		if(getTestName().equals("Android_UserSessionManagement")) {
			setPlatform(Utilities.setPlatform);
		}else if(methodName.equals("TvLogin") ) {
			setPlatform("Web");
		}

		try {
			connectURL = new URL("https://www.google.com");
			connection = connectURL.openConnection();
			connection.connect();
			connection.getInputStream().close();
		} catch (IOException e1) {
			System.out.println("<<<<<<---- Network is Down  ---->>>>>>>");
			System.exit(0);
		}

		if (getPlatform().equals("Web")) {
			if (getURL().equals("https://www.google.com/")) {
				setENV(getURL());
			}
		}else if(getURL().equals("https://www.google.co.in")) {
			setENV(getURL());
		}else if (getPlatform().equals("Android")) {
			setENV("Native App");
			setApk(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("Platform"));
			click = false;
		}
		else if (getPlatform().equals("Suite")) {
			setENV("Chrome Application");
			click = false;
		}else if (getPlatform().equals("MPWA")) {
			setENV("Chrome Application");
			click = false;
		}else if(getPlatform().equals("iOS")) {
			setENV("Native App");
			click = false;
		}else if (getPlatform().equals("BrowserStack")) {
			setENV("Native App");
			click = false;
		} else if (getPlatform().equals("IOSBrowserStack")) {
			setENV("Native App");
			click = false;
		}

		logger.info("PlatForm :: " + getPlatform());
		if (Stream.of("Android", "iOS", "Web", "MPWA","BrowserStack","IOSBrowserStack","Suite").anyMatch(getPlatform()::equals)) {
			setHandler(new PropertyFileReader("properties/ExecutionControl.properties"));
			if (getHandler().getproperty(getTestName()).equals("Y")) {
				logger.info("Running Test :: " + getTestName());
				logger.info("Run Mode :: YES");
				startTest = true;
			} else {
				logger.info(getTestName() + " : Test Skipped");
				logger.info("RunMode is :: No");
				startTest = false;
				throw new SkipException(getTestName() + " : Test Skipped ");
			}
		} else {
			throw new SkipException("PlatForm not matched...");
		}
	}

	public static ArrayList<String> getListOfDevicesConnected() {
		ArrayList<String> deviceID = new ArrayList<>();
		try {
			Process p1;
			String cmd2 = "adb devices";
			if(osName.contains("mac")) {
				p1 =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd2});
			}else {
				p1 = Runtime.getRuntime().exec(cmd2);
			}


			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String s = "";
			Thread.sleep(1000);
			while (!(s = br.readLine()).isEmpty()) {
				if (!s.equals("List of devices attached")) {
					deviceID.add(s.replaceAll("device", "").trim());
				}
			}
			return deviceID;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return deviceID;
	}
}
