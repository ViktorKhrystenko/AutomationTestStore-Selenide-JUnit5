package pageobjects;

import com.codeborne.selenide.*;
import exceptions.PageNavigationException;
import exceptions.UnableToSelectOptionException;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.JsActionsUtil;
import utils.datagenerator.DataGeneratorManager;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static constants.FormValues.DESELECTED_OPTION;
import static utils.StringFormatHelper.doesStringMatchRegex;

import static config.ConfigReader.readConfigProperty;

public abstract class BasePage {
    private static final By ROOT_HTML_ELEMENT = By.tagName("html");


    static {
        Configuration.timeout = 10000;
    }


    protected void enterText(By locator, String text) {
        enterText($(locator), text);
    }

    protected void enterText(SelenideElement field, String text) {
        if (isChromeInDocker()) {
            JsActionsUtil.enterText(field, text);
        }
        else {
            field.shouldBe(visible)
                    .sendKeys(text);
        }
        if (text == null || text.isBlank()) {
            field.shouldBe(matchText("\\s*"));
        }
        else {
            field.shouldBe(value(text));
        }
    }

    protected String getTextFromElementLocated(By locator) {
        return $(locator)
                .shouldBe(visible)
                .getText();
    }

    protected void selectRandomOption(By selectLocator) throws UnableToSelectOptionException {
        SelenideElement selectElement = $(selectLocator)
                .shouldBe(visible, enabled);
        String selectedOption = selectElement.getSelectedOptionText();
        ElementsCollection randomOptions = selectElement.getOptions()
                .filter(not(text(DESELECTED_OPTION)))
                .filter(not(text(selectedOption)));
        SelenideElement randomOption = DataGeneratorManager.getDataGenerator()
                .selectRandomOption(randomOptions.stream().toList());
        String optionVisibleText = randomOption.text();
        selectOptionByVisibleText(selectLocator, optionVisibleText);
        $(selectLocator).shouldBe(visible)
                .getSelectedOption()
                .shouldBe(text(optionVisibleText));
    }

    protected void selectOptionByVisibleText(By selectLocator, String optionVisibleText) throws UnableToSelectOptionException {
        Allure.addAttachment("Selected option", optionVisibleText);
        SelenideElement selectElement = $(selectLocator)
                .shouldBe(visible);
        selectElement.selectOptionContainingText(optionVisibleText);
    }

    protected SelenideElement getSelectedOption(By selectLocator) {
        SelenideElement selectElement = $(selectLocator);
        return selectElement.getSelectedOption();
    }


    protected void waitUntilPageIsLoaded() {
        Wait().until(d -> Selenide
                .executeJavaScript("return document.readyState").equals("complete"));
    }

    protected void clickOnElementAndWaitPageLoad(SelenideElement elementToClickOn) throws NoSuchElementException {
        WebElement oldPageHtml = WebDriverRunner.getWebDriver().findElement(ROOT_HTML_ELEMENT);
        if (!elementToClickOn.exists()) {
            throw new NoSuchElementException("");
        }
        if (isChromeInDocker()) {
            actions().moveToElement(elementToClickOn)
                    .perform();
            JsActionsUtil.clickOnElement(elementToClickOn);
        }
        else {
            elementToClickOn.click();
        }
        Wait().until(ExpectedConditions.stalenessOf(oldPageHtml));
        waitUntilPageIsLoaded();
    }

    protected void sendEnterAndWaitPageLoad(SelenideElement field) throws NoSuchElementException {
        WebElement oldPageHtml = WebDriverRunner.getWebDriver().findElement(ROOT_HTML_ELEMENT);
        if (isChromeInDocker()) {
            field.text();
            JsActionsUtil.sendEnterToField(field);
        }
        else {
            field.sendKeys(Keys.ENTER);
        }
        Wait().until(ExpectedConditions.stalenessOf(oldPageHtml));
        waitUntilPageIsLoaded();
    }

    protected void hoverCursorOverElement(SelenideElement element) {
        if (isChromeInDocker()) {
            JsActionsUtil.hoverCursorOverElement(element);
        }
        else {
            actions().moveToElement(element).perform();
        }
    }


    protected void checkLocation(String regex, String pageName) throws PageNavigationException {
        String currentUrl = WebDriverRunner.url();
        if (!doesStringMatchRegex(currentUrl, regex)) {
            throw new PageNavigationException(pageName, regex, currentUrl);
        }
    }


    private boolean isChromeInDocker() {
        return readConfigProperty("run.target", "local").equals("jenkins-docker-agent")
                && readConfigProperty("browser", "chrome").equals("chrome");
    }
}
