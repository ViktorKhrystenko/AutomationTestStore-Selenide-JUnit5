package pageobjects.login;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.account.AccountPage;
import pageobjects.registration.RegistrationPage;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrls.LOGIN_BASE_URL;
import static utils.StringFormatHelper.trimCloseAlertCross;

public class LoginPage extends BasePage {
    private static final String BASE_URL = LOGIN_BASE_URL;
    private static final String PAGE_NAME = "Login page";

    private SelenideElement toRegistrationPageButton = $x("//*[@id='accountFrm']//button[@type='submit']");

    private SelenideElement loginNameField = $("#loginFrm_loginname");

    private SelenideElement passwordField = $("#loginFrm_password");

    private SelenideElement loginButton = $x("//*[@id='loginFrm']//button[@type='submit']");

    private SelenideElement loginErrorAlert = $x("//div[contains(@class, 'alert-danger')]");


    public LoginPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }


    @Step("Fill 'Login name' field")
    public LoginPage fillLoginNameField(String loginName) {
        loginNameField.sendKeys(loginName);
        return this;
    }

    @Step("Fill 'Password' field")
    public LoginPage fillPasswordField(String password) {
        passwordField.sendKeys(password);
        return this;
    }


    @Step("Click on 'Login' button")
    public AccountPage clickOnLoginButton() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(loginButton);
        return new AccountPage();
    }

    @Step("Click on 'To registration page' button")
    public RegistrationPage clickOnToRegistrationPageButton() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(toRegistrationPageButton);
        return new RegistrationPage();
    }


    public String getLoginErrorMessage() {
        String loginErrorMessage = loginErrorAlert.text();
        return trimCloseAlertCross(loginErrorMessage);
    }
}
