package tests;

import annotations.Seed;
import com.codeborne.selenide.Selenide;
import lombok.Getter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.testng.SeedLoggingListener;
import utils.datagenerator.DataGenerator;
import utils.datagenerator.DataGeneratorManager;
import utils.driver.DriverConfigurator;

import java.lang.reflect.Method;

@Listeners({SeedLoggingListener.class})
public abstract class BaseTest {
    @Getter
    protected DataGenerator generator;


    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult test) {
        DriverConfigurator.configureDriver();
        setupGenerator(test);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        Selenide.closeWebDriver();
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
        DataGeneratorManager.setDataGenerator(generator);
    }
}
