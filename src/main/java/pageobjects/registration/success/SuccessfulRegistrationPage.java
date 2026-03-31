package pageobjects.registration.success;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.login.logout.LogoutPage;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrls.SUCCESSFUL_REGISTRATION_BASE_URL;

public class SuccessfulRegistrationPage extends BasePage {
    private static final String BASE_URL = SUCCESSFUL_REGISTRATION_BASE_URL;
    private static final String PAGE_NAME = "Successful registration page";

    private SelenideElement logoffLink = $x("//ul[@class='side_account_list']//a[contains(text(), 'Logoff')]");


    public SuccessfulRegistrationPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }

    @Step("Click on 'Logoff' link")
    public LogoutPage clickOnLogoffLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(logoffLink);
        return new LogoutPage();
    }
}
