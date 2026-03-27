package pageobjects.registration;

import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pageobjects.BasePage;
import pageobjects.registration.success.SuccessfulRegistrationPage;
import utils.datagenerator.DataGenerator;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.REGISTRATION_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

import static pageobjects.registration.RegistrationField.*;
import static pageobjects.registration.RegistrationDropdown.*;

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


    public RegistrationPage(WebDriver driver) throws PageNavigationException {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public RegistrationPage fill(RegistrationField field, String data) {
        return Allure.step(String.format("Fill '%s' field", field.name()),
                () -> {
                    enterText(field.getLocator(), data);
                    return this;
                });
    }


    @Step("Select random option in 'Region / State' dropdown")
    public RegistrationPage selectRandomRegionState() {
        selectRandomOption(REGION_STATE_DROPDOWN.getLocator());
        return this;
    }

    @Step("Select random option in 'Country' dropdown")
    public RegistrationPage selectRandomCountry() {
        selectRandomOption(COUNTRY_DROPDOWN.getLocator());
        return this;
    }

    @Step("Deselect 'Country' dropdown")
    public RegistrationPage deselectCountry() {
        Select countrySelect = new Select(driver.findElement(COUNTRY_DROPDOWN.getLocator()));
        countrySelect.selectByVisibleText(DESELECTED_OPTION);
        return this;
    }

    @Step("Check Privacy Policy checkbox")
    public RegistrationPage checkPrivacyPolicyCheckbox() {
        if (privacyPolicyCheckbox.getDomProperty("checked").equals("false")) {
            privacyPolicyCheckbox.click();
        }
        return this;
    }

    @Step("Uncheck Privacy Policy checkbox")
    public RegistrationPage uncheckPrivacyPolicyCheckbox() {
        if (!privacyPolicyCheckbox.getDomProperty("checked").equals("false")) {
            privacyPolicyCheckbox.click();
        }
        return this;
    }


    @Step("Fill only required during registration fields")
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
        selectRandomCountry();
        selectRandomRegionState();
        checkPrivacyPolicyCheckbox();
        return this;
    }

    @Step("Fill all registration fields")
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


    @Step("Click on 'Continue' button")
    public SuccessfulRegistrationPage clickOnContinueButton() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(continueButton);
        return new SuccessfulRegistrationPage(driver);
    }


    public String getRegionStateDropdownCurrentOption() {
        return getSelectedOption(REGION_STATE_DROPDOWN.getLocator()).getText();
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
}
