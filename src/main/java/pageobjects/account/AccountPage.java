package pageobjects.account;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderHistoryPage;
import pageobjects.login.logout.LogoutPage;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.ACCOUNT_BASE_URL;

public class AccountPage extends BasePage {
    private static final String BASE_URL = ACCOUNT_BASE_URL;
    private static final String PAGE_NAME = "Account page";

    @FindBy(xpath = "//a[@data-original-title='Order history']")
    private WebElement orderHistoryIcon;

    @FindBy(xpath = "//ul[@class='side_account_list']//a[contains(text(), 'Logoff')]")
    private WebElement logoffLink;


    public AccountPage(WebDriver driver) throws PageNavigationException {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    @Step("Click on \"Order history\" icon")
    public OrderHistoryPage clickOnOrderHistoryIcon() throws PageNavigationException {
        orderHistoryIcon.click();
        waitUntilPageIsLoaded();
        return new OrderHistoryPage(driver);
    }

    @Step("Click on 'Logoff' link")
    public LogoutPage clickOnLogoffLink() throws PageNavigationException {
        logoffLink.click();
        waitUntilPageIsLoaded();
        return new LogoutPage(driver);
    }
}
