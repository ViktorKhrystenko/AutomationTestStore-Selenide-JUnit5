package tests;

import annotations.Seed;
import io.qameta.allure.Attachment;
import lombok.Getter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import testnglisteners.SeedLoggingListener;
import utils.datagenerator.DataGenerator;
import utils.driver.DriverFactory;
import java.lang.reflect.Method;

@Listeners({SeedLoggingListener.class})
public abstract class BaseTest {
    @Getter
    protected WebDriver driver;
    @Getter
    protected DataGenerator generator;


    @BeforeMethod
    public void setup(ITestResult test) {
        driver = DriverFactory.createDriver();
        setupGenerator(test);
    }

    @AfterMethod
    public void teardown(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            attachScreenshot();
        }
        driver.quit();
    }


    private void setupGenerator(ITestResult test) {
        Method testMethod = test.getMethod().getConstructorOrMethod().getMethod();
        Seed seedAnnotation = testMethod.getAnnotation(Seed.class);
        if (seedAnnotation != null) {
            generator = new DataGenerator(seedAnnotation.value());
        }
        else {
            generator = new DataGenerator();
        }
    }

    // TODO Move screenshot attachment to TestLifecycleListener from Allure later
    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    private byte[] attachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
