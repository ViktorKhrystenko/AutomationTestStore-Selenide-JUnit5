package utils.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();


    public static WebDriver getWebDriver() {
        return threadLocalDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    public static void quitDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove();
        }
    }
}
