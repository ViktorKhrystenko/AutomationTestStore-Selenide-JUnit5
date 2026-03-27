package pageobjects.registration.success;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.login.logout.LogoutPage;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.SUCCESSFUL_REGISTRATION_BASE_URL;

public class SuccessfulRegistrationPage extends BasePage {
    private static final String BASE_URL = SUCCESSFUL_REGISTRATION_BASE_URL;
    private static final String PAGE_NAME = "Successful registration page";

    @FindBy(xpath = "//ul[@class='side_account_list']//a[contains(text(), 'Logoff')]")
    private WebElement logoffLink;


    public SuccessfulRegistrationPage(WebDriver driver) throws PageNavigationException {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }

    @Step("Click on 'Logoff' link")
    public LogoutPage clickOnLogoffLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(logoffLink);
        return new LogoutPage(driver);
    }
}
