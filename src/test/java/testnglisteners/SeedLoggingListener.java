package testnglisteners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.datagenerator.DataGenerator;

import java.lang.reflect.Method;

public class SeedLoggingListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        Method testMethod = result.getMethod().getConstructorOrMethod().getMethod();
        DataGenerator generator = ((BaseTest) testClass).getGenerator();
        System.err.printf("Instancio = Test method '%s' from %s test class failed with seed: %d",
                testMethod.getName(),
                testClass.getClass().getName(),
                generator.getORIGINAL_SEED());
    }
}
