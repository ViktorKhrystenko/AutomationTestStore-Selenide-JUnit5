package tests.registration;

import com.codeborne.selenide.WebDriverRunner;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pageobjects.components.navigation.NavigationBar;
import pageobjects.registration.RegistrationPage;
import tests.BaseTest;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.open;
import static constants.url.BaseUrls.REGISTRATION_BASE_URL;
import static constants.url.BaseUrls.SUCCESSFUL_REGISTRATION_BASE_URL;
import static constants.url.BaseUrls.HOME_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static pageobjects.registration.RegistrationField.*;
import static pageobjects.registration.RegistrationDropdown.*;

import static utils.datagenerator.DataGenerator.*;

import static utils.datagenerator.generators.InvalidTelephoneGenerator.TelephoneErrorType.*;

import static utils.StringFormatHelper.doesStringMatchRegex;
import static utils.StringFormatHelper.addSymbolsToField;
import static utils.EmailValidator.isEmailValid;

@Epic("Registration")
@Tag("registration")
@DisplayName("Registration tests")
public class RegistrationTests extends BaseTest {
    private RegistrationPage registrationPage;
    private NavigationBar navigation;

    private User user;


    @BeforeEach
    public void setupRegistration() {
        open(REGISTRATION_BASE_URL);
        registrationPage = new RegistrationPage();
        navigation = new NavigationBar();

        user = generateUser();
    }


    @Test
    @Tag("smoke")
    @DisplayName("1.1.1.1. Test case - Entrance test")
    public void entranceTest() {
        open(HOME_BASE_URL);

        navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));
    }

    @Test
    @Tag("smoke")
    @DisplayName("1.1.1.2. Test case - Check user registration with only necessary fields filled")
    public void verifyUserRegistrationWithOnlyNecessaryFields() {
        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    @Tag("smoke")
    @DisplayName("1.1.1.3. Test case - Check user registration with all fields filled")
    public void verifyUserRegistrationWithAllFields() {
        registrationPage.fillAllFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    @Feature("First name field")
    @Tag("regression")
    @DisplayName("1.1.2.1. Test case - First name with space ' - characters")
    public void verifyUserRegistrationWithHyphen_Space_ApostropheInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                " '-",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    @Feature("First name field")
    @Tag("critical-path")
    @DisplayName("1.1.2.2. Test case - First name with non a-z letters")
    public void verifyUserRegistrationWithNonLatinLettersInsideFirstName() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "фбыглк",
                10));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    @Feature("Last name field")
    @Tag("regression")
    @DisplayName("1.1.3.1. Test case - Last name with space ' - characters")
    public void verifyUserRegistrationWithHyphen_Space_ApostropheInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                " '-",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }

    @Test
    @Feature("Last name field")
    @Tag("critical-path")
    @DisplayName("1.1.3.2. Test case - Last name with non a-z letters")
    public void verifyUserRegistrationWithNonLatinLettersInsideLastName() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "уйзьюмїкє",
                10));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    @Feature("City dropdown")
    @Tag("critical-path")
    @DisplayName("1.1.4.1. Test case - City with space - ' . characters")
    public void verifyUserRegistrationWithHyphen_Space_Apostrophe_DotInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                " '-.",
                128));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    @Feature("ZIP code field")
    @Tag("smoke")
    @DisplayName("1.1.5.1. Test case - ZIP code with space - characters")
    public void verifyUserRegistrationWithHyphen_SpaceInsideZipCodeField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                " -",
                10));

        registrationPage.fillOnlyRequiredFields(user);

        registrationPage.clickOnContinueButton();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(SUCCESSFUL_REGISTRATION_BASE_URL)));
    }


    @Test
    @Feature("Region / State dropdown")
    @Tag("smoke")
    @DisplayName("1.1.6.1 Test case - Does \"Region / State\" drops after \"Country\" changed")
    public void verifyThatRegionStateDropdownValueDropsAfterCountryValueChanged() {
        registrationPage.selectRandomCountry()
                .selectRandomRegionState()
                .selectRandomCountry();

        assertThat(registrationPage.getRegionStateDropdownCurrentOption())
                .isEqualTo(DESELECTED_OPTION);
    }


    @Test
    @Feature("First name field")
    @Tag("critical-path")
    @DisplayName("1.2.1.1. Test case - Empty \"First Name\" field")
    public void verifyUserRegistrationWithEmptyFirstNameField() {
        user.setFirstName(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not be empty!");
    }

    @Test
    @Feature("First name field")
    @Tag("regression")
    @DisplayName("1.2.1.2. Test case - Too long first name")
    public void verifyUserRegistrationWithTooLongFirstName() {
        user.setFirstName(randomString(33));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must be between 1 and 32 characters!");
    }

    @Test
    @Feature("First name field")
    @Tag("critical-path")
    @DisplayName("1.2.1.3. Test case - First name with numbers")
    public void verifyUserRegistrationWithNumbersInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "12945",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not contain numbers!");
    }

    @Test
    @Feature("First name field")
    @Tag("critical-path")
    @DisplayName("1.2.1.4. Test case - First name with only spaces")
    public void verifyUserRegistrationWithOnlySpacesInsideFirstNameField() {
        user.setFirstName("    ");

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not be empty!");
    }

    @Test
    @Feature("First name field")
    @Tag("regression")
    @DisplayName("1.2.1.5. Test case - First name with special characters")
    public void verifyUserRegistrationWithSpecialCharactersInsideFirstNameField() {
        user.setFirstName(addSymbolsToField(
                user.getFirstName(),
                "!##@;&*",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(FIRST_NAME_FIELD))
                .isEqualTo("First Name must not contain special characters!");
    }


    @Test
    @Feature("Last name field")
    @Tag("critical-path")
    @DisplayName("1.2.2.1. Test case - Empty \"Last Name\" field")
    public void verifyUserRegistrationWithEmptyLastNameField() {
        user.setLastName(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not be empty!");
    }

    @Test
    @Feature("Last name field")
    @Tag("regression")
    @DisplayName("1.2.2.2. Test case - Too long last name")
    public void verifyUserRegistrationWithTooLongLastName() {
        user.setLastName(randomString(33));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must be between 1 and 32 characters!");
    }

    @Test
    @Feature("Last name field")
    @Tag("critical-path")
    @DisplayName("1.2.2.3. Test case - Last name with numbers")
    public void verifyUserRegistrationWithNumbersInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                "02947",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not contain numbers!");
    }

    @Test
    @Feature("Last name field")
    @Tag("critical-path")
    @DisplayName("1.2.2.4. Test case - Last name with only spaces")
    public void verifyUserRegistrationWithOnlySpacesInsideLastNameField() {
        user.setLastName("    ");

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not be empty!");
    }

    @Test
    @Feature("Last name field")
    @Tag("regression")
    @DisplayName("1.2.2.5. Test case - Last name with special characters")
    public void verifyUserRegistrationWithSpecialCharactersInsideLastNameField() {
        user.setLastName(addSymbolsToField(
                user.getLastName(),
                "!\\/'\"#@;&*",
                32));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LAST_NAME_FIELD))
                .isEqualTo("Last Name must not contain special characters!");
    }


    @Test
    @Feature("Email field")
    @Tag("critical-path")
    @DisplayName("1.2.3.1. Test case - Empty \"Email\" field")
    public void verifyUserRegistrationWithEmptyEmailField() {
        user.setEmail(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("Email must not be empty!");
    }

    @Test
    @Feature("Email field")
    @Tag("critical-path")
    @DisplayName("1.2.3.2. Test case - Invalid email")
    public void verifyUserRegistrationWithInvalidEmail() {
        user.setEmail(generateRandomInvalidEmail());

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertFalse(isEmailValid(user.getEmail()));

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("Email Address does not appear to be valid!");
    }

    @Test
    @Feature("Email field")
    @Tag("regression")
    @DisplayName("1.2.3.3. Test case - Registration with already used email")
    public void verifyUserRegistrationWithAlreadyUsedEmail() {
        User userWithSameEmail = generateUser();
        userWithSameEmail.setEmail(user.getEmail());

        registrationPage.fillOnlyRequiredFields(user)
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameEmail);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(EMAIL_FIELD))
                .isEqualTo("E-Mail Address is already registered!");
    }


    @Test
    @Feature("Telephone field")
    @Tag("critical-path")
    @DisplayName("1.2.4.1. Test case - Mobile phone with less than 7 digits")
    public void verifyUserRegistrationWithTooShortTelephone() {
        user.setTelephone(generateInvalidTelephone(TOO_SHORT));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must be between 7 and 15 numbers!");
    }

    @Test
    @Feature("Telephone field")
    @Tag("regression")
    @DisplayName("1.2.4.2. Test case - Mobile phone with more than 15 digits")
    public void verifyUserRegistrationWithTooLongTelephone() {
        user.setTelephone(generateInvalidTelephone(TOO_LONG));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must be between 7 and 15 numbers!");
    }

    @Test
    @Feature("Telephone field")
    @Tag("regression")
    @DisplayName("1.2.4.3. Test case - Mobile phone with letters")
    public void verifyUserRegistrationWithLettersInsideTelephoneField() {
        user.setTelephone(generateInvalidTelephone(WITH_LETTERS));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must not contain letters!");
    }

    @Test
    @Feature("Telephone field")
    @Tag("critical-path")
    @DisplayName("1.2.4.4. Test case - Mobile phone with special characters")
    public void verifyUserRegistrationWithSpecialCharactersInsideTelephoneField() {
        user.setTelephone(generateInvalidTelephone(WITH_SPECIAL_CHARACTERS));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(TELEPHONE_FIELD, user.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone must not contain special characters!");
    }

    @Test
    @Feature("Telephone field")
    @Tag("regression")
    @DisplayName("1.2.4.5. Test case - Registration with already used mobile phone")
    public void verifyUserRegistrationWithAlreadyUsedTelephone() {
        User userWithSameTelephone = generateUser();
        userWithSameTelephone.setTelephone(user.getTelephone());

        registrationPage.fillOnlyRequiredFields(user)
                .fill(TELEPHONE_FIELD, user.getTelephone())
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameTelephone)
                .fill(TELEPHONE_FIELD, userWithSameTelephone.getTelephone());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(TELEPHONE_FIELD))
                .isEqualTo("Telephone is already in use!");
    }


    @Test
    @Feature("Address 1 field")
    @Tag("critical-path")
    @DisplayName("1.2.5.1. Test case - Empty \"Address 1\" field")
    public void verifyUserRegistrationWithEmptyAddress_1_Field() {
        user.setAddress_1(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must not be empty!");
    }

    @Test
    @Feature("Address 1 field")
    @Tag("critical-path")
    @DisplayName("1.2.5.2. Test case - Too short address 1")
    public void verifyUserRegistrationWithTooShortAddress_1() {
        user.setAddress_1(user.getAddress_1().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must be between 3 and 128 characters!");
    }

    @Test
    @Feature("Address 1 field")
    @Tag("regression")
    @DisplayName("1.2.5.3. Test case - Too long address 1")
    public void verifyUserRegistrationWithTooLongAddress_1() {
        user.setAddress_1(randomString(129));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_1_FIELD))
                .isEqualTo("Address 1 must be between 3 and 128 characters!");
    }


    @Test
    @Feature("Address 2 field")
    @Tag("critical-path")
    @DisplayName("1.2.6.1. Test case - Too short address 2")
    public void verifyUserRegistrationWithTooShortAddress_2() {
        user.setAddress_2(user.getAddress_2().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(ADDRESS_2_FIELD, user.getAddress_2());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_2_FIELD))
                .isEqualTo("Address 2 must be between 3 and 128 characters!");
    }

    @Test
    @Feature("Address 2 field")
    @Tag("regression")
    @DisplayName("1.2.6.2. Test case - Too long address 2")
    public void verifyUserRegistrationWithTooLongAddress_2() {
        user.setAddress_2(randomString(129));

        registrationPage.fillOnlyRequiredFields(user)
                .fill(ADDRESS_2_FIELD, user.getAddress_2());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ADDRESS_2_FIELD))
                .isEqualTo("Address 2 must be between 3 and 128 characters!");
    }


    @Test
    @Feature("City field")
    @Tag("critical-path")
    @DisplayName("1.2.7.1. Test case - Empty \"City\" field")
    public void verifyUserRegistrationWithEmptyCityField() {
        user.setCity(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not be empty!");
    }

    @Test
    @Feature("City field")
    @Tag("critical-path")
    @DisplayName("1.2.7.2. Test case - Too short city name")
    public void verifyUserRegistrationWithTooShortCity() {
        user.setCity(user.getCity().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must be between 3 and 128 characters!");
    }

    @Test
    @Feature("City field")
    @Tag("regression")
    @DisplayName("1.2.7.3. Test case - Too long city name")
    public void verifyUserRegistrationWithTooLongCity() {
        user.setCity(randomString(129));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must be between 3 and 128 characters!");
    }

    @Test
    @Feature("City field")
    @Tag("critical-path")
    @DisplayName("1.2.7.4. Test case - City name with numbers")
    public void verifyUserRegistrationWithNumbersInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                "131249",
                128));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not contain numbers!");
    }

    @Test
    @Feature("City field")
    @Tag("regression")
    @DisplayName("1.2.7.5. Test case - City name with special characters (except - ' . space)")
    public void verifyUserRegistrationWithSpecialCharactersInsideCityField() {
        user.setCity(addSymbolsToField(
                user.getCity(),
                "\")({}][$%&*",
                128));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(CITY_FIELD))
                .isEqualTo("City must not contain special characters!");
    }


    @Test
    @Feature("ZIP code field")
    @Tag("critical-path")
    @DisplayName("1.2.8.1. Test case - Empty \"Zip Code\" field")
    public void verifyUserRegistrationWithEmptyZipCodeField() {
        user.setZipCode(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must not be empty!");
    }

    @Test
    @Feature("ZIP code field")
    @Tag("critical-path")
    @DisplayName("1.2.8.2. Test case - Too short ZIP cade")
    public void verifyUserRegistrationWithTooShortZipCode() {
        user.setZipCode(String.valueOf(
                user.getZipCode().charAt(0)));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must be between 3 and 10 characters!");
    }

    @Test
    @Feature("ZIP code field")
    @Tag("critical-path")
    @DisplayName("1.2.8.3. Test case - Too long ZIP code")
    public void verifyUserRegistrationWithTooLongZipCode() {
        user.setZipCode(randomNumericString(11));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must be between 3 and 10 characters!");
    }

    @Test
    @Feature("ZIP code field")
    @Tag("regression")
    @DisplayName("1.2.8.4. Test case - ZIP code with special characters (except - space)")
    public void verifyUserRegistrationWithSpecialCharactersInsideZipCodeField() {
        user.setZipCode(addSymbolsToField(
                user.getZipCode(),
                "\"<?",
                10));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(ZIP_CODE_FIELD))
                .isEqualTo("Zip/postal code must not contain special characters!");
    }


    @Test
    @Feature("Region / State dropdown")
    @Tag("critical-path")
    @DisplayName("1.2.9.1. Test case - Not selected region/state")
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
                .selectRandomCountry();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(REGION_STATE_DROPDOWN))
                .isEqualTo("Please select a region / state!");
    }


    @Test
    @Feature("Country dropdown")
    @Tag("critical-path")
    @DisplayName("1.2.10.1. Test case - Not selected country")
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
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(COUNTRY_DROPDOWN))
                .isEqualTo("Please select a country!");
    }


    @Test
    @Feature("Login name field")
    @Tag("critical-path")
    @DisplayName("1.2.11.1. Test case - Empty \"Login name\" field")
    public void verifyUserRegistrationWithEmptyLoginNameField() {
        user.setLoginName(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must not be empty!");
    }

    @Test
    @Feature("Login name field")
    @Tag("critical-path")
    @DisplayName("1.2.11.2. Test case - Too short login name")
    public void verifyUserRegistrationWithTooShortLoginName() {
        user.setLoginName(user.getLoginName().substring(0, 1));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test
    @Feature("Login name field")
    @Tag("regression")
    @DisplayName("1.2.11.3. Test case - Too long login name")
    public void verifyUserRegistrationWithTooLongLoginName() {
        user.setLoginName(randomString(65));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test
    @Feature("Login name field")
    @Tag("critical-path")
    @DisplayName("1.2.11.4. Test case - Login name with special characters (except . - space)")
    public void verifyUserRegistrationWithSpecialCharactersInsideLoginNameField() {
        user.setLoginName(addSymbolsToField(
                user.getLastName(),
                "!\\/'\"#@;&*",
                64));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("Login name must be alphanumeric only and between 5 and 64 characters!");
    }

    @Test
    @Feature("Login name field")
    @Tag("regression")
    @DisplayName("1.2.11.5. Test case - Registration with already used login name")
    public void verifyUserRegistrationWithAlreadyUsedLoginName() {
        User userWithSameLoginName = generateUser();
        userWithSameLoginName.setLoginName(user.getLoginName());

        registrationPage.fillOnlyRequiredFields(user)
                .clickOnContinueButton()
                .clickOnLogoffLink();

        registrationPage = navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        registrationPage.fillOnlyRequiredFields(userWithSameLoginName);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(LOGIN_NAME_FIELD))
                .isEqualTo("This login name is not available. Try different login name!");
    }


    @Test
    @Feature("Password field")
    @Tag("critical-path")
    @DisplayName("1.2.12.1. Test case - Empty \"Password\" field")
    public void verifyUserRegistrationWithEmptyPasswordField() {
        user.setPassword(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must not be empty!");
    }

    @Test
    @Feature("Password field")
    @Tag("critical-path")
    @DisplayName("1.2.12.2. Test case - Too short password")
    public void verifyUserRegistrationWithTooShortPassword() {
        user.setPassword(user.getLoginName().substring(0, 2));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must be between 4 and 20 characters!");
    }

    @Test
    @Feature("Password field")
    @Tag("critical-path")
    @DisplayName("1.2.12.3. Test case - Too long password")
    public void verifyUserRegistrationWithTooLongPassword() {
        user.setPassword(randomString(21));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password must be between 4 and 20 characters!");
    }


    @Test
    @Feature("Password confirm field")
    @Tag("critical-path")
    @DisplayName("1.2.13.1. Test case - Empty \"Password Confirm\" field")
    public void verifyUserRegistrationWithEmptyPasswordConfirmField() {
        user.setPasswordConfirm(null);

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password confirm must not be empty!");
    }

    @Test
    @Feature("Password confirm field")
    @Tag("critical-path")
    @DisplayName("1.2.13.2. Test case - Password confirmation error")
    public void verifyUserRegistrationWithPasswordConfirmationError() {
        user.setPasswordConfirm(randomString(5, 20));

        registrationPage.fillOnlyRequiredFields(user);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getInputErrorMessage(PASSWORD_FIELD))
                .isEqualTo("Password confirmation does not match password!");
    }


    @Test
    @Feature("Privacy Policy checkbox")
    @Tag("critical-path")
    @DisplayName("1.2.14.1. Test case - Not checked \"Privacy Policy\" checkbox")
    public void verifyUserRegistrationWithNotSelectedPrivacyPolicy() {
        registrationPage.fillOnlyRequiredFields(user)
                .uncheckPrivacyPolicyCheckbox();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> registrationPage.clickOnContinueButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(REGISTRATION_BASE_URL)));

        assertThat(registrationPage.getRegistrationErrorMessage())
                .isEqualTo("Error: You must agree to the Privacy Policy!");
    }
}
