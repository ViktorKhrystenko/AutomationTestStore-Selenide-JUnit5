package utils;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebElement;

public class JsActionsUtil {

    public static void enterText(WebElement field, String text) {
        Selenide.executeJavaScript("arguments[0].value = arguments[1];", field, text);
    }

    public static void clickOnElement(WebElement elementToClickOn) {
        Selenide.executeJavaScript("arguments[0].click();", elementToClickOn);
    }

    public static void hoverCursorOverElement(WebElement element) {
        Selenide.executeJavaScript("""
                       arguments[0].dispatchEvent(new MouseEvent('mouseover', {
                            bubbles: true,
                            cancelable: true,
                            view: window
                       }));""", element);
    }

    public static void sendEnterToField(WebElement field) {
        Selenide.executeJavaScript("""
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
        Selenide.executeJavaScript(
                """
                arguments[0].closest('form').dispatchEvent(new Event('submit', {
                    bubbles: true,
                    cancelable: true
                }));""", field
        );
    }
}
