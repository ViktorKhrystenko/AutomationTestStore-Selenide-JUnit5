package pageobjects;

import exceptions.PageNavigationException;
import exceptions.UnableToSelectOptionException;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JsActionsUtil;
import utils.datagenerator.DataGeneratorManager;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static constants.FormValues.DESELECTED_OPTION;
import static utils.StringFormatHelper.doesStringMatchRegex;

import static config.ConfigReader.readConfigProperty;

public abstract class BasePage {
    private static final By ROOT_HTML_ELEMENT = By.tagName("html");

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

    protected String getTextFromElementLocated(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected void selectRandomOption(By selectLocator) throws UnableToSelectOptionException {
        FluentWait<WebDriver> fWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
        fWait.until(d -> {
            WebElement selectElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(selectLocator));
            Select select = new Select(selectElement);
            String selectedOption = select.getFirstSelectedOption().getText();
            List<WebElement> randomOptions = select.getOptions();
            randomOptions = randomOptions.stream()
                    .filter(option -> !option.getText().equals(DESELECTED_OPTION)
                            & !option.getText().equals(selectedOption))
                    .toList();
            randomOptions.get(0).isEnabled();
            WebElement randomOption = DataGeneratorManager.getDataGenerator().selectRandomOption(randomOptions);
            String optionVisibleText = randomOption.getText();
            selectOptionByVisibleText(selectLocator, optionVisibleText);
            wait.until(dr -> {
                Select selectRecheck = new Select(wait.until(
                        ExpectedConditions.presenceOfElementLocated(selectLocator)));
                return selectRecheck.getFirstSelectedOption().getText().equals(optionVisibleText);
            });
            return true;
        });
    }

    protected void selectOptionByVisibleText(By selectLocator, String optionVisibleText) throws UnableToSelectOptionException {
        Allure.addAttachment("Selected option", optionVisibleText);
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

    protected WebElement getSelectedOption(By selectLocator) {
        WebElement selectElement = driver.findElement(selectLocator);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption();
    }


    protected void waitUntilPageIsLoaded() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected void waitUntilPageStartsRefreshing() {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(ROOT_HTML_ELEMENT)));
    }

    protected void performActionAndWaitPageLoad(Runnable action) {
        WebElement oldPageHtml = driver.findElement(ROOT_HTML_ELEMENT);
        action.run();
        wait.until(ExpectedConditions.stalenessOf(oldPageHtml));
        waitUntilPageIsLoaded();
    }

    protected void clickOnElementAndWaitPageLoad(WebElement elementToClickOn) {
        WebElement oldPageHtml = driver.findElement(ROOT_HTML_ELEMENT);
        if (isChromeInDocker()) {
            new Actions(driver)
                    .moveToElement(elementToClickOn)
                    .perform();
            JsActionsUtil.clickOnElement(elementToClickOn);
        }
        else {
            elementToClickOn.click();
        }
        wait.until(ExpectedConditions.stalenessOf(oldPageHtml));
        waitUntilPageIsLoaded();
    }

    protected void sendEnterAndWaitPageLoad(WebElement field) {
        WebElement oldPageHtml = driver.findElement(ROOT_HTML_ELEMENT);
        if (isChromeInDocker()) {
            JsActionsUtil.sendEnterToField(field);
        }
        else {
            field.sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.stalenessOf(oldPageHtml));
        waitUntilPageIsLoaded();
    }

    protected void hoverCursorOverElement(WebElement element) {
        if (isChromeInDocker()) {
            JsActionsUtil.hoverCursorOverElement(element);
        }
        else {
            new Actions(driver).moveToElement(element).perform();
        }
    }


    protected void checkLocation(String regex, String pageName) throws PageNavigationException {
        String currentUrl = driver.getCurrentUrl();
        if (!doesStringMatchRegex(currentUrl, regex)) {
            throw new PageNavigationException(pageName, regex, currentUrl);
        }
    }

    protected void waitUntilElementStopsBeingStale(WebElement element) {
        wait.until(d -> {
            try {
                element.getText();
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }


    private boolean isChromeInDocker() {
        return readConfigProperty("run.target", "local").equals("jenkins-docker-agent")
                && readConfigProperty("browser", "chrome").equals("chrome");
    }
}
