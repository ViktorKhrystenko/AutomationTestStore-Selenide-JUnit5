package pageobjects.registration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pageobjects.BasePage;
import pageobjects.registration.success.SuccessfulRegistrationPage;
import utils.datagenerator.DataGenerator;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static constants.url.BaseUrls.REGISTRATION_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

import static pageobjects.registration.RegistrationField.*;
import static pageobjects.registration.RegistrationDropdown.*;

import static utils.StringFormatHelper.trimCloseAlertCross;

public class RegistrationPage extends BasePage {
    private static final String BASE_URL = REGISTRATION_BASE_URL;
    private static final String PAGE_NAME = "Registration page";

    private static final By ERROR_MESSAGE_LOCATOR = By.xpath("following-sibling::span");

    private SelenideElement privacyPolicyCheckbox = $("#AccountFrm_agree");

    private SelenideElement continueButton = $x("//button[@title='Continue']");

    private SelenideElement registrationErrorAlert = $x("//div[contains(@class, 'alert-danger')]");


    public RegistrationPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
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
        SelenideElement regionStateDropdown = $(REGION_STATE_DROPDOWN.getLocator());
        SelenideElement firstRegionStateOption = regionStateDropdown
                .getOptions()
                .filter(not(text(DESELECTED_OPTION)))
                .filter(not(text(regionStateDropdown.getSelectedOptionText())))
                .get(0);
        selectRandomOption(COUNTRY_DROPDOWN.getLocator());
        firstRegionStateOption.should(disappear);
        return this;
    }

    @Step("Deselect 'Country' dropdown")
    public RegistrationPage deselectCountry() {
        SelenideElement countrySelect = $(COUNTRY_DROPDOWN.getLocator());
        countrySelect.selectOptionContainingText(DESELECTED_OPTION);
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
        return new SuccessfulRegistrationPage();
    }


    public String getRegionStateDropdownCurrentOption() {
        return getSelectedOption(REGION_STATE_DROPDOWN.getLocator()).text();
    }

    public String getInputErrorMessage(RegistrationInput input) {
        return $(input.getLocator())
                .parent()
                .$(ERROR_MESSAGE_LOCATOR)
                .text();
    }

    public String getRegistrationErrorMessage() {
        String registrationErrorMessage = registrationErrorAlert.text();
        return trimCloseAlertCross(registrationErrorMessage);
    }
}
