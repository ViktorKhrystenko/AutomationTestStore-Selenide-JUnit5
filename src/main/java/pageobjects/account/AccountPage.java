package pageobjects.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderHistoryPage;

import java.util.regex.Pattern;

import static constants.BaseUrls.ACCOUNT_BASE_URL;

public class AccountPage extends BasePage {
    private static final String BASE_URL = ACCOUNT_BASE_URL;
    private static final String PAGE_NAME = "Account page";

    @FindBy(xpath = "//a[@data-original-title='Order history']")
    private WebElement orderHistoryIcon;


    public AccountPage(WebDriver driver) {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public OrderHistoryPage clickOnOrderHistoryIcon() {
        orderHistoryIcon.click();
        waitUntilPageIsLoaded();
        return new OrderHistoryPage(driver);
    }
}
