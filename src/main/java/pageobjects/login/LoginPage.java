package pageobjects.login;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.AccountPage;
import pageobjects.registration.RegistrationPage;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.LOGIN_BASE_URL;
import static utils.StringFormatHelper.trimCloseAlertCross;

public class LoginPage extends BasePage {
    private static final String BASE_URL = LOGIN_BASE_URL;
    private static final String PAGE_NAME = "Login page";

    @FindBy(xpath = "//*[@id='accountFrm']//button[@type='submit']")
    private WebElement toRegistrationPageButton;

    @FindBy(id = "loginFrm_loginname")
    private WebElement loginNameField;

    @FindBy(id = "loginFrm_password")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='loginFrm']//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]")
    private WebElement loginErrorAlert;


    public LoginPage(WebDriver driver) throws PageNavigationException {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
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
        return new AccountPage(driver);
    }

    @Step("Click on 'To registration page' button")
    public RegistrationPage clickOnToRegistrationPageButton() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(toRegistrationPageButton);
        return new RegistrationPage(driver);
    }


    public String getLoginErrorMessage() {
        String loginErrorMessage = loginErrorAlert.getText();
        return trimCloseAlertCross(loginErrorMessage);
    }
}
