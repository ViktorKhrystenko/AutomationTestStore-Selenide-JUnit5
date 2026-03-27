package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.driver.DriverManager;

public class JsActionsUtil {

    public static void enterText(WebElement field, String text) {
        ((JavascriptExecutor) DriverManager.getWebDriver())
                .executeScript("arguments[0].value = arguments[1];", field, text);
    }

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
                        const events = ['keydown', 'keypress', 'keyup'];
                        events.forEach(ev => {
                            let event = new KeyboardEvent(ev, {
                                key: 'Enter',
                                code: 'Enter',
                                which: 13,
                                keyCode: 13,
                                bubbles: true,
                                cancelable: true
                            });
                            arguments[0].dispatchEvent(event);
                        });""", field);
    }

    public static void confirmForm(WebElement field) {
        ((JavascriptExecutor) DriverManager.getWebDriver()).executeScript(
                """
                arguments[0].closest('form').dispatchEvent(new Event('submit', {
                    bubbles: true,
                    cancelable: true
                }));""", field
        );
    }
}
