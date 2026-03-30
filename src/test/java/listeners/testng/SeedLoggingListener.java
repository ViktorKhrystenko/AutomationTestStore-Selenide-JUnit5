package listeners.testng;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.datagenerator.DataGenerator;
import utils.datagenerator.DataGeneratorManager;

import java.lang.reflect.Method;

public class SeedLoggingListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        Method testMethod = result.getMethod().getConstructorOrMethod().getMethod();
        DataGenerator generator = DataGeneratorManager.getDataGenerator();
        System.err.printf("Instancio = Test method '%s' from %s test class failed with seed: %d\n",
                testMethod.getName(),
                testClass.getClass().getName(),
                generator.getORIGINAL_SEED());
    }
}
