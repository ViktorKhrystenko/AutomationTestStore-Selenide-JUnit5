package pageobjects.account;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.account.history.OrderHistoryPage;
import pageobjects.login.logout.LogoutPage;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrls.ACCOUNT_BASE_URL;

public class AccountPage extends BasePage {
    private static final String BASE_URL = ACCOUNT_BASE_URL;
    private static final String PAGE_NAME = "Account page";

    private SelenideElement orderHistoryIcon = $x("//a[@data-original-title='Order history']");

    private SelenideElement logoffLink = $x("//ul[@class='side_account_list']//a[contains(text(), 'Logoff')]");


    public AccountPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }


    @Step("Click on \"Order history\" icon")
    public OrderHistoryPage clickOnOrderHistoryIcon() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(orderHistoryIcon);
        return new OrderHistoryPage();
    }

    @Step("Click on 'Logoff' link")
    public LogoutPage clickOnLogoffLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(logoffLink);
        return new LogoutPage();
    }
}
