package listeners.allure;

import config.ConfigReader;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.TestResult;

import java.util.List;

public class BrowserTaggingListener implements TestLifecycleListener {
    private static final String BROWSER = ConfigReader.readConfigProperty("browser", "chrome");


    @Override
    public void beforeTestWrite(TestResult testResult) {
        List<Label> testLabels = testResult.getLabels();
        testLabels.add(new Label().setName("story").setValue(BROWSER));
        testLabels.add(new Label().setName("tag").setValue(BROWSER));

        List<Parameter> testParameters = testResult.getParameters();
        testParameters.add(new Parameter().setName("Browser").setValue(BROWSER));

        testResult.setLabels(testLabels);
        testResult.setParameters(testParameters);
        testResult.setName(testResult.getName() + " [" + BROWSER + "]");
        testResult.setHistoryId(testResult.getHistoryId() + "-" + BROWSER);
    }
}
