package pageobjects;

import exceptions.PageNavigationException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.StringFormatHelper.doesStringMatchRegex;

public abstract class BasePage {
    protected static final By PARENT_ELEMENT_LOCATOR = By.xpath("..");

    protected WebDriver driver;
    protected WebDriverWait wait;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }


    protected void enterText(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    protected void waitUntilPageIsLoaded() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected void checkLocation(String regex, String pageName) {
        String currentUrl = driver.getCurrentUrl();
        if (!doesStringMatchRegex(currentUrl, regex)) {
            throw new PageNavigationException(pageName, regex, currentUrl);
        }
    }
}
