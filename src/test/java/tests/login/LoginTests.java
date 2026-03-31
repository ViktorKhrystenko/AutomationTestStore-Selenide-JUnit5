package tests.login;

import com.codeborne.selenide.WebDriverRunner;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Epic;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
import static org.testng.Assert.*;

import static utils.StringFormatHelper.doesStringMatchRegex;

@Epic("Login")
@Tag("login")
public class LoginTests extends BaseTest {
    private static final String TEST_DATA_FILE_PATH = LOGIN_TEST_DATA_PATH + "user.properties";

    private LoginPage loginPage;

    private User user;


    @BeforeMethod(alwaysRun = true)
    public void setupLogin() {
        open(LOGIN_BASE_URL);
        loginPage = new LoginPage();

        Properties testUserData = loadProperties(TEST_DATA_FILE_PATH);
        user = generator.generateUser();
        user.setLoginName(testUserData.getProperty("login-name"));
        user.setPassword(testUserData.getProperty("password"));
    }


    @Tag("smoke")
    @Test(description = "2.1.1.1. Test case - Check successful login",
            groups = {
            "login",
            "smoke"
    })
    public void verifyUserLoginWithRegisteredData() {
        loginPage.fillLoginNameField(user.getLoginName())
                .fillPasswordField(user.getPassword());

        assertThatCode(() -> loginPage.clickOnLoginButton())
                .doesNotThrowAnyException();

        assertTrue(doesStringMatchRegex(
                WebDriverRunner.url(),
                Pattern.quote(ACCOUNT_BASE_URL)));
    }


    @Tag("critical-path")
    @Test(description = "2.2.1.1. Test case - Empty \"Login Name\" field",
            groups = {
            "login",
            "critical-path"
    })
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

    @Tag("critical-path")
    @Test(description = "2.2.1.2. Test case - Incorrect login name",
            groups = {
            "login",
            "critical-path"
    })
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


    @Tag("critical-path")
    @Test(description = "2.2.2.1. Test case - Empty \"Password\" field",
            groups = {
            "login",
            "critical-path"
    })
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

    @Tag("critical-path")
    @Test(description = "2.2.2.2. Test case - Incorrect password",
            groups = {
            "login",
            "critical-path"
    })
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
