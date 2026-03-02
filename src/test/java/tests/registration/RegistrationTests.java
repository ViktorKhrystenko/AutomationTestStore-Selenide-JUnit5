package tests.registration;

import dto.User;
import exceptions.PageNavigationException;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.navigation.NavigationBar;
import pageobjects.registration.RegistrationPage;
import tests.BaseTest;
import utils.datagenerator.generators.InvalidTelephoneGenerator;

import java.util.regex.Pattern;

import static constants.BaseUrls.REGISTRATION_BASE_URL;
import static constants.BaseUrls.SUCCESSFUL_REGISTRATION_BASE_URL;
import static constants.BaseUrls.HOME_BASE_URL;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import static pageobjects.registration.RegistrationField.*;

import static utils.datagenerator.generators.InvalidTelephoneGenerator.TelephoneErrorType.*;

import static utils.StringFormatHelper.doesStringMatchRegex;
import static utils.EmailValidator.isEmailValid;

public class RegistrationTests extends BaseTest {
    private RegistrationPage registrationPage;
    private NavigationBar navigation;

    private User user;


    private String addSymbolsToField(String field, String symbolsToAdd, int maxFieldLength) {
        if (field.length() + symbolsToAdd.length() >= maxFieldLength) {
            field = symbolsToAdd + field.substring(symbolsToAdd.length());
        }
        else {
            field = symbolsToAdd + field;
        }
        return field;
    }


    @BeforeMethod
    @Override
    public void setup(ITestResult test) {
        super.setup(test);
        driver.get(REGISTRATION_BASE_URL);
        registrationPage = new RegistrationPage(driver);
        navigation = new NavigationBar(driver);

        user = generator.generateUser();
    }


    @Test
    public void entranceTest() {
        driver.get(HOME_BASE_URL);

        navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithOnlyNecessaryFields() {
        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithAllFields() {
        registrationPage.fillAllFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithHyphen_Space_ApostropheInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                " '-",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithNonLatinLettersInsideFirstName() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "фбвыэглцк",
                10));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithHyphen_Space_ApostropheInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                " '-",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithNonLatinLettersInsideLastName() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "уйзьюмїкє",
                10));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithHyphen_Space_Apostrophe_DotInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                " '-.",
                128));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithHyphen_SpaceInsideZipCodeField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                " -",
                10));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyThatRegionStateDropdownValueDropsAfterCountryValueChanged() {
        registrationPage.selectRandomCountry(generator)
                .selectRandomRegionState(generator)
                .selectRandomCountry(generator);

        assertThat(registrationPage.getRegionStateDropdownCurrentOption())
                .isEqualTo(" --- Please Select --- ");
    }


    @Test
    public void verifyUserRegistrationWithEmptyFirstNameField() {
        user.setFirstName(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithTooLongFirstName() {
        user.setFirstName(generator.randomString(33));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithNumbersInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "12945",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithOnlySpacesInsideFirstNameField() {
        user.setFirstName("    ");

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithSpecialCharactersInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "!##@;&*",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithEmptyLastNameField() {
        user.setLastName(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithTooLongLastName() {
        user.setLastName(generator.randomString(33));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithNumbersInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                "02947",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithOnlySpacesInsideLastNameField() {
        user.setLastName("    ");

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithSpecialCharactersInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                "!\\/'\"#@;&*",
                32));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithEmptyEmailField() {
        user.setEmail(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithInvalidEmail() {
        user.setEmail(generator.generateRandomInvalidEmail());

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertFalse(isEmailValid(user.getEmail()));
    }

    @Test
    public void verifyUserRegistrationWithAlreadyUsedEmail() {
        User userWithSameEmail = generator.generateUser();
        userWithSameEmail.setEmail(user.getEmail());

        registrationPage.fillOnlyRequiredFields(user, generator)
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameEmail, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }


    @Test
    public void verifyUserRegistrationWithTooShortTelephone() {
        user.setTelephone(generator.generateInvalidTelephone(TOO_SHORT));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithTooLongTelephone() {
        user.setTelephone(generator.generateInvalidTelephone(TOO_LONG));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithSpecialCharactersInsideTelephoneField() {
        user.setTelephone(generator.generateInvalidTelephone(TOO_LONG));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithLettersInsideTelephoneField() {
        user.setTelephone(generator.generateInvalidTelephone(WITH_LETTERS));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    public void verifyUserRegistrationWithAlreadyUsedTelephone() {
        User userWithSameTelephone = generator.generateUser();
        userWithSameTelephone.setTelephone(user.getTelephone());

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone())
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameTelephone, generator)
                .fill(TELEPHONE_FIELD, userWithSameTelephone.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    // TODO Continue writing tests
}
