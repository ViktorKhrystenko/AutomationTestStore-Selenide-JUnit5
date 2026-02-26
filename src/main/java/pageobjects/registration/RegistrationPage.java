package pageobjects.registration;

import dto.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pageobjects.BasePage;
import pageobjects.registration.success.SuccessfulRegistrationPage;
import utils.datagenerator.DataGenerator;

import static constants.BaseUrls.REGISTRATION_BASE_URL;
import static pageobjects.registration.RegistrationField.*;
import static utils.StringFormatHelper.trimCloseAlertCross;

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


    public RegistrationPage selectRandomRegionState(DataGenerator generator) {
        selectRandomOption(RegistrationDropdown.REGION_STATE_FIELD.getLocator(), generator);
        return this;
    }

    public RegistrationPage selectRandomCountry(DataGenerator generator) {
        selectRandomOption(RegistrationDropdown.COUNTRY_FIELD.getLocator(), generator);
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


    public RegistrationPage fillOnlyRequiredFields(User user, DataGenerator generator) {
        if (user.getFirstName() != null) {
            fill(FIRST_NAME_FIELD, user.getFirstName());
        }
        if (user.getLastName() != null) {
            fill(LAST_NAME_FIELD, user.getLastName());
        }
        if (user.getEmail() != null) {
            fill(EMAIL_FIELD, user.getEmail());
        }
        if (user.getAddress_1() != null) {
            fill(ADDRESS_1_FIELD, user.getAddress_1());
        }
        if (user.getCity() != null) {
            fill(CITY_FIELD, user.getCity());
        }
        if (user.getZipCode() != null) {
            fill(ZIP_CODE_FIELD, user.getZipCode());
        }
        if (user.getLoginName() != null) {
            fill(LOGIN_NAME_FIELD, user.getLoginName());
        }
        if (user.getPassword() != null) {
            fill(PASSWORD_FIELD, user.getPassword());
        }
        if (user.getPasswordConfirm() != null) {
            fill(PASSWORD_CONFIRM_FIELD, user.getPasswordConfirm());
        }
        selectRandomCountry(generator);
        selectRandomRegionState(generator);
        checkPrivacyPolicyCheckbox();
        return this;
    }

    public RegistrationPage fillAllFields(User user, DataGenerator generator) {
        if (user.getTelephone() != null) {
            fill(TELEPHONE_FIELD, user.getTelephone());
        }
        if (user.getFax() != null) {
            fill(FAX_FIELD, user.getFax());
        }
        if (user.getCompany() != null) {
            fill(COMPANY_FIELD, user.getCompany());
        }
        if (user.getAddress_2() != null) {
            fill(ADDRESS_2_FIELD, user.getAddress_2());
        }
        return fillOnlyRequiredFields(user, generator);
    }


    public SuccessfulRegistrationPage clickOnContinueButton() {
        continueButton.click();
        waitUntilPageIsLoaded();
        return new SuccessfulRegistrationPage(driver);
    }


    public String getInputErrorMessage(RegistrationInput input) {
        return driver.findElement(input.getLocator())
                .findElement(PARENT_ELEMENT_LOCATOR)
                .findElement(ERROR_MESSAGE_LOCATOR)
                .getText();
    }

    public String getRegistrationErrorMessage() {
        String registrationErrorMessage = registrationErrorAlert.getText();
        return trimCloseAlertCross(registrationErrorMessage);
    }


    private void selectRandomOption(By selectLocator, DataGenerator generator) {
        WebElement selectElement = driver.findElement(selectLocator);
        Select select = new Select(selectElement);
        WebElement randomOption = generator.selectRandomOption(select.getOptions());
        String optionVisibleText = randomOption.getText();
        select.selectByVisibleText(optionVisibleText);
    }
}
