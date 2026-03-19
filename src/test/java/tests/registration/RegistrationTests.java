package tests.registration;

import dto.User;
import exceptions.PageNavigationException;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.components.navigation.NavigationBar;
import pageobjects.registration.RegistrationPage;
import tests.BaseTest;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.REGISTRATION_BASE_URL;
import static constants.url.BaseUrls.SUCCESSFUL_REGISTRATION_BASE_URL;
import static constants.url.BaseUrls.HOME_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import static pageobjects.registration.RegistrationField.*;
import static pageobjects.registration.RegistrationDropdown.*;

import static utils.datagenerator.generators.InvalidTelephoneGenerator.TelephoneErrorType.*;

import static utils.StringFormatHelper.doesStringMatchRegex;
import static utils.StringFormatHelper.addSymbolsToField;
import static utils.EmailValidator.isEmailValid;

public class RegistrationTests extends BaseTest {
    private RegistrationPage registrationPage;
    private NavigationBar navigation;

    private User user;


    @BeforeMethod(groups = {"lifecycle"})
    @Override
    public void setup(ITestResult test) {
        super.setup(test);
        driver.get(REGISTRATION_BASE_URL);
        registrationPage = new RegistrationPage(driver);
        navigation = new NavigationBar(driver);

        user = generator.generateUser();
    }


    @Test(description = "1.1.1.1. Test case - Entrance test",
            groups = {
            "registration",
            "smoke"
    })
    public void entranceTest() {
        driver.get(HOME_BASE_URL);

        navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test(description = "1.1.1.2. Test case - Check user registration with only necessary fields filled",
            groups = {
            "registration",
            "smoke"
    })
    public void verifyUserRegistrationWithOnlyNecessaryFields() {
        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test(description = "1.1.1.3. Test case - Check user registration with all fields filled",
            groups = {
            "registration",
            "smoke"
    })
    public void verifyUserRegistrationWithAllFields() {
        registrationPage.fillAllFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test(description = "1.1.2.1. Test case - First name with space ' - characters",
            groups = {
            "registration",
            "regression"
    })
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

    @Test(description = "1.1.2.2. Test case - First name with non a-z letters",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithNonLatinLettersInsideFirstName() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "фбыглк",
                10));

        registrationPage.fillOnlyRequiredFields(user, generator);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test(description = "1.1.3.1. Test case - Last name with space ' - characters",
            groups = {
            "registration",
            "regression"
    })
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

    @Test(description = "1.1.3.2. Test case - Lost name with non a-z letters",
            groups = {
            "registration",
            "critical-path"
    })
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


    @Test(description = "1.1.4.1. Test case - City with space - ' . characters",
            groups = {
            "registration",
            "critical-path"
    })
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


    @Test(description = "1.1.5.1. Test case - ZIP code with space - characters",
            groups = {
            "registration",
            "smoke"
    })
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


    @Test(description = "1.1.6.1 Test case - Does \"Region / State\" drops after \"Country\" changed",
            groups = {
            "registration",
            "smoke"
    })
    public void verifyThatRegionStateDropdownValueDropsAfterCountryValueChanged() {
        registrationPage.selectRandomCountry(generator)
                .selectRandomRegionState(generator)
                .selectRandomCountry(generator);

        assertThat(registrationPage.getRegionStateDropdownCurrentOption())
                .isEqualTo(DESELECTED_OPTION);
    }


    @Test(description = "1.2.1.1. Test case - Empty \"First Name\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyFirstNameField() {
        user.setFirstName(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not be empty!");
    }

    @Test(description = "1.2.1.2. Test case - Too long first name",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongFirstName() {
        user.setFirstName(generator.randomString(33));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must be between 1 and 32 characters!");
    }

    @Test(description = "1.2.1.3. Test case - First name with numbers",
            groups = {
            "registration",
            "critical-path"
    })
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

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not contain numbers!");
    }

    @Test(description = "1.2.1.4. Test case - First name with only spaces",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithOnlySpacesInsideFirstNameField() {
        user.setFirstName("    ");

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not be empty!");
    }

    @Test(description = "1.2.1.5. Test case - First name with special characters",
            groups = {
            "registration",
            "regression"
    })
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

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not contain special characters!");
    }


    @Test(description = "1.2.2.1. Test case - Empty \"Last Name\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyLastNameField() {
        user.setLastName(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not be empty!");
    }

    @Test(description = "1.2.2.2. Test case - Too long last name",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongLastName() {
        user.setLastName(generator.randomString(33));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must be between 1 and 32 characters!");
    }

    @Test(description = "1.2.2.3. Test case - Last name with numbers",
            groups = {
            "registration",
            "critical-path"
    })
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

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not contain numbers!");
    }

    @Test(description = "1.2.2.4. Test case - Last name with only spaces",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithOnlySpacesInsideLastNameField() {
        user.setLastName("    ");

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not be empty!");
    }

    @Test(description = "1.2.2.5. Test case - Last name with special characters",
            groups = {
            "registration",
            "regression"
    })
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

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not contain special characters!");
    }


    @Test(description = "1.2.3.1. Test case - Empty \"Email\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyEmailField() {
        user.setEmail(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("Email must not be empty!");
    }

    @Test(description = "1.2.3.2. Test case - Invalid email",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithInvalidEmail() {
        user.setEmail(generator.generateRandomInvalidEmail());

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertFalse(isEmailValid(user.getEmail()));

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("Email Address does not appear to be valid!");
    }

    @Test(description = "1.2.3.3. Test case - Registration with already used email",
            groups = {
            "registration",
            "regression"
    })
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

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("E-Mail Address is already registered!");
    }


    @Test(description = "1.2.4.1. Test case - Mobile phone with less than 7 digits",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortTelephone() {
        user.setTelephone(generator.generateInvalidTelephone(TOO_SHORT));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must be between 7 and 15 numbers!");
    }

    @Test(description = "1.2.4.2. Test case - Mobile phone with more than 15 digits",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongTelephone() {
        user.setTelephone(generator.generateInvalidTelephone(TOO_LONG));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must be between 7 and 15 numbers!");
    }

    @Test(description = "1.2.4.3. Test case - Mobile phone with letters",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithLettersInsideTelephoneField() {
        user.setTelephone(generator.generateInvalidTelephone(WITH_LETTERS));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must not contain letters!");
    }

    @Test(description = "1.2.4.4. Test case - Mobile phone with special characters",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithSpecialCharactersInsideTelephoneField() {
        user.setTelephone(generator.generateInvalidTelephone(WITH_SPECIAL_CHARACTERS));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must not contain special characters!");
    }

    @Test(description = "1.2.4.5. Test case - Registration with already used mobile phone",
            groups = {
            "registration",
            "regression"
    })
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

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone is already in use!");
    }


    @Test(description = "1.2.5.1. Test case - Empty \"Address 1\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyAddress_1_Field() {
        user.setAddress_1(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must not be empty!");
    }

    @Test(description = "1.2.5.2. Test case - Too short address 1",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortAddress_1() {
        user.setAddress_1(user.getAddress_1().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must be between 3 and 128 characters!");
    }

    @Test(description = "1.2.5.3. Test case - Too long address 1",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongAddress_1() {
        user.setAddress_1(generator.randomString(129));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must be between 3 and 128 characters!");
    }


    @Test(description = "1.2.6.1. Test case - Too short address 2",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortAddress_2() {
        user.setAddress_2(user.getAddress_2().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(ADDRESS_2_FIELD, user.getAddress_2());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_2_FIELD))
                .isEqualTo("Address 2 must be between 3 and 128 characters!");
    }

    @Test(description = "1.2.6.2. Test case - Too long address 2",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongAddress_2() {
        user.setAddress_2(generator.randomString(129));

        registrationPage.fillOnlyRequiredFields(user, generator)
                .fill(ADDRESS_2_FIELD, user.getAddress_2());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_2_FIELD))
                .isEqualTo("Address 2 must be between 3 and 128 characters!");
    }


    @Test(description = "1.2.7.1. Test case - Empty \"City\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyCityField() {
        user.setCity(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not be empty!");
    }

    @Test(description = "1.2.7.2. Test case - Too short city name",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortCity() {
        user.setCity(user.getCity().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must be between 3 and 128 characters!");
    }

    @Test(description = "1.2.7.3. Test case - Too long city name",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongCity() {
        user.setCity(generator.randomString(129));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must be between 3 and 128 characters!");
    }

    @Test(description = "1.2.7.4. Test case - City name with numbers",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithNumbersInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                "131249",
                128));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not contain numbers!");
    }

    @Test(description = "1.2.7.5. Test case - City name with special characters (except - ' . space)",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithSpecialCharactersInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                "\")({}][$%&*",
                128));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not contain special characters!");
    }


    @Test(description = "1.2.8.1. Test case - Empty \"Zip Code\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyZipCodeField() {
        user.setZipCode(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must not be empty!");
    }

    @Test(description = "1.2.8.2. Test case - Too short ZIP cade",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortZipCode() {
        user.setZipCode(String.valueOf(
                user.getZipCode().charAt(0)));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must be between 3 and 10 characters!");
    }

    @Test(description = "1.2.8.3. Test case - Too long ZIP code",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooLongZipCode() {
        user.setZipCode(generator.randomNumericString(11));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must be between 3 and 10 characters!");
    }

    @Test(description = "1.2.8.4. Test case - ZIP code with special characters (except - space)",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithSpecialCharactersInsideZipCodeField() {
        user.setZipCode(addSymbolsToField(
                user.getZipCode(),
                "\"<?",
                10));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must not contain special characters!");
    }


    @Test(description = "1.2.9.1. Test case - Not selected region/state",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithNotSelectedRegionState() {
        registrationPage.fill(FIRST_NAME_FIELD, user.getFirstName())
                .fill(LAST_NAME_FIELD, user.getLastName())
                .fill(EMAIL_FIELD, user.getEmail())
                .fill(ADDRESS_1_FIELD, user.getAddress_1())
                .fill(CITY_FIELD, user.getCity())
                .fill(ZIP_CODE_FIELD, user.getZipCode())
                .fill(LOGIN_NAME_FIELD, user.getLoginName())
                .fill(PASSWORD_FIELD, user.getPassword())
                .fill(PASSWORD_CONFIRM_FIELD, user.getPasswordConfirm())
                .selectRandomCountry(generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(REGION_STATE_DROPDOWN))
                .isEqualTo("Please select a region / state!");
    }


    @Test(description = "1.2.10.1. Test case - Not selected country",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithNotSelectedCountry() {
        registrationPage.fill(FIRST_NAME_FIELD, user.getFirstName())
                .fill(LAST_NAME_FIELD, user.getLastName())
                .fill(EMAIL_FIELD, user.getEmail())
                .fill(ADDRESS_1_FIELD, user.getAddress_1())
                .fill(CITY_FIELD, user.getCity())
                .fill(ZIP_CODE_FIELD, user.getZipCode())
                .fill(LOGIN_NAME_FIELD, user.getLoginName())
                .fill(PASSWORD_FIELD, user.getPassword())
                .fill(PASSWORD_CONFIRM_FIELD, user.getPasswordConfirm())
                .deselectCountry();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(COUNTRY_DROPDOWN))
                .isEqualTo("Please select a country!");
    }


    @Test(description = "1.2.11.1. Test case - Empty \"Login name\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyLoginNameField() {
        user.setLoginName(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must not be empty!");
    }

    @Test(description = "1.2.11.2. Test case - Too short login name",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortLoginName() {
        user.setLoginName(user.getLoginName().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test(description = "1.2.11.3. Test case - Too long login name",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithTooLongLoginName() {
        user.setLoginName(generator.randomString(65));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test(description = "1.2.11.4. Test case - Login name with special characters (except . - space)",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithSpecialCharactersInsideLoginNameField() {
        user.setLoginName(addSymbolsToField(
                user.getLastName(),
                "!\\/'\"#@;&*",
                64));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test(description = "1.2.11.5. Test case - Registration with already used login name",
            groups = {
            "registration",
            "regression"
    })
    public void verifyUserRegistrationWithAlreadyUsedLoginName() {
        User userWithSameLoginName = generator.generateUser();
        userWithSameLoginName.setLoginName(user.getLoginName());

        registrationPage.fillOnlyRequiredFields(user, generator)
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameLoginName, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("This login name is not available. Try different login name!");
    }


    @Test(description = "1.2.12.1. Test case - Empty \"Password\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyPasswordField() {
        user.setPassword(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must not be empty!");
    }

    @Test(description = "1.2.12.2. Test case - Too short password",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooShortPassword() {
        user.setPassword(user.getLoginName().substring(0, 2));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must be between 4 and 20 characters!");
    }

    @Test(description = "1.2.12.3. Test case - Too long password",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithTooLongPassword() {
        user.setPassword(generator.randomString(21));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must be between 4 and 20 characters!");
    }


    @Test(description = "1.2.13.1. Test case - Empty \"Password Confirm\" field",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithEmptyPasswordConfirmField() {
        user.setPasswordConfirm(null);

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password confirm must not be empty!");
    }

    @Test(description = "1.2.13.2. Test case - Password confirmation error",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithPasswordConfirmationError() {
        user.setPasswordConfirm(generator.randomString(5, 20));

        registrationPage.fillOnlyRequiredFields(user, generator);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password confirmation does not match password!");
    }


    @Test(description = "1.2.14.1. Test case - Not checked \"Privacy Policy\" checkbox",
            groups = {
            "registration",
            "critical-path"
    })
    public void verifyUserRegistrationWithNotSelectedPrivacyPolicy() {
        registrationPage.fillOnlyRequiredFields(user, generator)
                .uncheckPrivacyPolicyCheckbox();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                driver.getCurrentUrl(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getRegistrationErrorMessage())
                .isEqualTo("Error: You must agree to the Privacy Policy!");
    }
}
