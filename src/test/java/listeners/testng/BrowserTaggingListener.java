package listeners.testng;

import config.ConfigReader;
import io.qameta.allure.Allure;
import org.testng.ITestListener;

public class BrowserTaggingListener implements ITestListener {
    private static final String BROWSER;


    static {
        BROWSER = ConfigReader.readConfigProperty("browser", "chrome");
    }


    @Override
    public void onTestStart(org.testng.ITestResult result) {
        Allure.story(BROWSER);
        Allure.label("tag", BROWSER);
        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setName(testCase.getName() + " [" + BROWSER + "]");
            testCase.setHistoryId(testCase.getHistoryId() + "-[" + BROWSER + "[");
        });
    }
}
