package utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
public class DriverMobile {
    private DriverMobile() {
    }

    private static UiAutomator2Options options;
    private static XCUITestOptions iosOptions;
    private static AppiumDriver appiumDriver;



    public static AppiumDriver getDriver() {

        if (appiumDriver== null) {
            switch (ConfigReader.getProperty("platformName")) {
                case "Android":
                    options = new UiAutomator2Options()
                            //.setAppPackage("com.myfox.android")
                           // .setAppActivity("com.myfox.android.activity.MainActivity")
                            .setApp("src/test/resources/Apps/base.apk")
                            .setPlatformVersion("14.0")
                            .setDeviceName("RFCW219R9JR")
                            .setAutomationName("uiautomator2")
                            .setNoReset(false)
                            .setNewCommandTimeout(Duration.ofMinutes(10));

                    try {
                        appiumDriver = new AndroidDriver(
                                new URL("http://127.0.0.1:4723"), options
                        );
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case "IOS":
                    iosOptions = new XCUITestOptions();
                    iosOptions.XCODE_ORG_ID_OPTION.contains("");
                    iosOptions.setBundleId("")
                            .setDeviceName("iPhone SE")
                            .setPlatformVersion("17.6")
                            .setUdid("")
                            .setAutomationName("XCUITest")
                            .setNoReset(false)
                            .setNewCommandTimeout(Duration.ofMinutes(10));


                    try {
                        appiumDriver = new IOSDriver(
                                new URL("http://127.0.0.1:4723"), iosOptions
                        );
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;




            }
        }
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return appiumDriver;
    }

    public static void quitDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;
        }
    }



}

