package tests;

import annotations.Seed;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.testng.SeedLoggingListener;
import utils.datagenerator.DataGenerator;
import utils.driver.DriverFactory;
import utils.driver.DriverManager;

import java.lang.reflect.Method;

@Listeners({SeedLoggingListener.class})
public abstract class BaseTest {
    @Getter
    protected WebDriver driver;
    @Getter
    protected DataGenerator generator;


    @BeforeMethod(groups = {"lifecycle"})
    public void setup(ITestResult test) {
        DriverManager.setWebDriver(DriverFactory.createDriver());
        driver = DriverManager.getWebDriver();
        setupGenerator(test);
    }

    @AfterMethod(groups = {"lifecycle"})
    public void teardown() {
        DriverManager.quitDriver();
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
}
