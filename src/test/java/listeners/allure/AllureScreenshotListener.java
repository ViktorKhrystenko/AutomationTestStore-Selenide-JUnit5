package listeners.allure;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.driver.DriverManager;

public class AllureScreenshotListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus() == Status.BROKEN || result.getStatus() == Status.FAILED || result.getStatus() == Status.SKIPPED) {
            WebDriver driver = DriverManager.getWebDriver();
            if (driver != null) {
                attachScreenshot(driver);
            }
        }
    }

    @Attachment(value = "Failed screenshot", type = "image/png", fileExtension = ".png")
    private byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
