package pageobjects.registration;

import org.instancio.Instancio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pageobjects.BasePage;

import java.util.List;

import static constants.BaseUrls.REGISTRATION_BASE_URL;

public class RegistrationPage extends BasePage {
    private static final String BASE_URL = REGISTRATION_BASE_URL;
    private static final String PAGE_NAME = "Registration page";

    private static final By ERROR_MESSAGE_LOCATOR = By.xpath("following-sibling::span");

    @FindBy(id = "AccountFrm_agree")
    private WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//button[@title='Continue']")
    private WebElement continueButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]")
    private WebElement registrationErrorAlert;


    public RegistrationPage(WebDriver driver) {
        super(driver);
        checkLocation(BASE_URL, PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public RegistrationPage fill(RegistrationField field, String data) {
        enterText(field.getLocator(), data);
        return this;
    }


    public RegistrationPage selectRandomRegionState() {
        selectRandomOption(RegistrationDropdown.REGION_STATE_FIELD.getLocator());
        return this;
    }

    public RegistrationPage selectRandomCountry() {
        selectRandomOption(RegistrationDropdown.COUNTRY_FIELD.getLocator());
        return this;
    }

    public RegistrationPage checkPrivacyPolicyCheckbox() {
        if (privacyPolicyCheckbox.getDomProperty("checked").equals("false")) {
            privacyPolicyCheckbox.click();
        }
        return this;
    }

    public RegistrationPage uncheckPrivacyPolicyCheckbox() {
        if (!privacyPolicyCheckbox.getDomProperty("checked").equals("false")) {
            privacyPolicyCheckbox.click();
        }
        return this;
    }


    public void clickOnContinueButton() {
        continueButton.click();
        waitUntilPageIsLoaded();
    }


    public String getInputErrorMessage(RegistrationInput input) {
        return driver.findElement(input.getLocator())
                .findElement(PARENT_ELEMENT_LOCATOR)
                .findElement(ERROR_MESSAGE_LOCATOR)
                .getText();
    }

    public String getRegistrationErrorMessage() {
        String registrationErrorMessage = registrationErrorAlert.getText();

        // we substring error message, because first 2 characters are "×\n" from close alert cross
        return registrationErrorMessage.substring(2);
    }


    private void selectRandomOption(By selectLocator) {
        WebElement selectElement = driver.findElement(selectLocator);
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        int optionIndex = Instancio.gen().ints().range(0, options.size() - 1).get();
        String optionVisibleText = options.get(optionIndex).getText();
        select.selectByVisibleText(optionVisibleText);
    }
}
