
package com.mlshop.test;

import com.aventstack.extentreports.ExtentTest;
import com.business.mlshop.*;
import org.testng.annotations.*;
import com.driverInstance.AppiumServer;
import com.propertyfilereader.PropertyFileReader;
import com.utility.LoggingUtils;
import org.testng.asserts.SoftAssert;

import static com.utility.Utilities.softAssert;

public class BaseTest {

    protected com.business.mlshop.BaseClass baseClass;

    protected MLWalletWeb mlWalletWeb;

    public static ExtentTest testLogger;
    AppiumServer server = new AppiumServer();
    protected static LoggingUtils logger = new LoggingUtils();
    public static PropertyFileReader prop;
    public String osName = System.getProperty("os.name").toLowerCase();

    //To Read Properties File Based On Current OS Of Laptop
    public void propertyFileReader() {
        if (osName.contains("mac") || osName.contains("linux")) {
            System.out.println(osName);
            prop = new PropertyFileReader(".//properties//testdata.properties");
        } else {
            prop = new PropertyFileReader(".\\properties\\testdata.properties");
        }

    }



    //Start Application
    @Parameters({"deviceName", "portno"})
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(String deviceName, String portno) throws Exception {
        baseClass = new BaseClass("mlwallet", deviceName, portno);
        mlWalletWeb = new MLWalletWeb();
        propertyFileReader();
        softAssert = new SoftAssert();
    }


//    Stop Application
    @AfterMethod
    public void afterTest() {
        baseClass.tearDown();
    }


}
