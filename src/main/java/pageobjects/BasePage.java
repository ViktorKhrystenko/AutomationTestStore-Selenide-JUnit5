package pageobjects;

import exceptions.PageNavigationException;
import exceptions.UnableToSelectOptionException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    protected void selectOptionByVisibleText(By selectLocator, String optionVisibleText) {
        int counter = 0;
        while (counter < 3) {
            try {
                WebElement selectElement = wait.until(
                        ExpectedConditions.presenceOfElementLocated(selectLocator));
                if (selectElement == null) {
                    counter++;
                    continue;
                }
                Select select = new Select(selectElement);
                select.selectByVisibleText(optionVisibleText);
                return;
            }
            catch (StaleElementReferenceException e) {
                counter++;
            }
        }
        throw new UnableToSelectOptionException(optionVisibleText, selectLocator.toString());
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

    protected void waitUntilElementStopsBeingStale(WebElement element) {
        wait.until(d -> {
            try {
                element.getTagName();
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }
}
