package tests.login;

import com.codeborne.selenide.WebDriverRunner;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pageobjects.login.LoginPage;
import tests.BaseTest;

import java.util.Properties;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.open;
import static utils.PropertiesLoader.loadProperties;

import static constants.ResourcesPaths.LOGIN_TEST_DATA_PATH;
import static constants.url.BaseUrls.LOGIN_BASE_URL;
import static  constants.url.BaseUrls.ACCOUNT_BASE_URL;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static utils.datagenerator.DataGenerator.*;

import static utils.StringFormatHelper.doesStringMatchRegex;

@Epic("Login")
@Tag("login")
@DisplayName("Login tests")
public class LoginTests extends BaseTest {
    private static final String TEST_DATA_FILE_PATH = LOGIN_TEST_DATA_PATH + "user.properties";

    private LoginPage loginPage;

    private User user;


    @BeforeEach
    public void setupLogin() {
        open(LOGIN_BASE_URL);
        loginPage = new LoginPage();

        Properties testUserData = loadProperties(TEST_DATA_FILE_PATH);
        user = generateUser();
        user.setLoginName(testUserData.getProperty("login-name"));
        user.setPassword(testUserData.getProperty("password"));
    }


    @Test
    @Tag("smoke")
    @DisplayName("2.1.1.1. Test case - Check successful login")
    public void verifyUserLoginWithRegisteredData() {
        loginPage.fillLoginNameField(user.getLoginName())
                .fillPasswordField(user.getPassword());

        assertThatCode(() -> loginPage.clickOnLoginButton())
                .doesNotThrowAnyException();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(ACCOUNT_BASE_URL)));
    }


    @Test
    @Tag("critical-path")
    @DisplayName("2.2.1.1. Test case - Empty \"Login Name\" field")
    public void verifyUserLoginWithEmptyLoginNameField() {
        loginPage.fillPasswordField(user.getPassword());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> loginPage.clickOnLoginButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(LOGIN_BASE_URL)));

        assertThat(loginPage.getLoginErrorMessage())
                .isEqualTo("Error: Incorrect login or password provided.");
    }

    @Test
    @Tag("critical-path")
    @DisplayName("2.2.1.2. Test case - Incorrect login name")
    public void verifyUserLoginWithIncorrectLoginName() {
        user.setLoginName("bob11");

        loginPage.fillLoginNameField(user.getLoginName())
                .fillPasswordField(user.getPassword());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> loginPage.clickOnLoginButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(LOGIN_BASE_URL)));

        assertThat(loginPage.getLoginErrorMessage())
                .isEqualTo("Error: Incorrect login or password provided.");
    }


    @Test
    @Tag("critical-path")
    @DisplayName("2.2.2.1. Test case - Empty \"Password\" field")
    public void verifyUserLoginWithEmptyPasswordField() {
        loginPage.fillPasswordField(user.getPassword());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> loginPage.clickOnLoginButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(LOGIN_BASE_URL)));

        assertThat(loginPage.getLoginErrorMessage())
                .isEqualTo("Error: Incorrect login or password provided.");
    }

    @Test
    @Tag("critical-path")
    @DisplayName("2.2.2.2. Test case - Incorrect password")
    public void verifyUserLoginWithIncorrectPassword() {
        user.setPassword("bob22");

        loginPage.fillLoginNameField(user.getLoginName())
                .fillPasswordField(user.getPassword());

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> loginPage.clickOnLoginButton());

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(LOGIN_BASE_URL)));

        assertThat(loginPage.getLoginErrorMessage())
                .isEqualTo("Error: Incorrect login or password provided.");
    }
}
