package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.driver.DriverManager;

public class JsActionsUtil {
    public static void clickOnElement(WebElement elementToClickOn) {
        ((JavascriptExecutor) DriverManager.getWebDriver())
                .executeScript("arguments[0].click();", elementToClickOn);
    }

    public static void hoverCursorOverElement(WebElement element) {
        ((JavascriptExecutor) DriverManager.getWebDriver())
                .executeScript("""
                       arguments[0].dispatchEvent(new MouseEvent('mouseover', {
                            bubbles: true,
                            cancelable: true,
                            view: window
                       }));""", element);
    }

    public static void sendEnterToField(WebElement field) {
        ((JavascriptExecutor) DriverManager.getWebDriver())
                .executeScript("""
                        arguments[0].dispatchEvent(new KeyboardEvent('keydown', {
                            key: 'Enter',
                            code: 'Enter',
                            which: 13,
                            keyCode: 13,
                            bubbles: true,
                            cancelable: true
                        }));""", field);
    }
}
