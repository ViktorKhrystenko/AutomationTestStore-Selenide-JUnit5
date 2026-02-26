package pageobjects.account;

import org.openqa.selenium.WebDriver;
import pageobjects.BasePage;

import java.util.regex.Pattern;

import static constants.BaseUrls.ACCOUNT_BASE_URL;

public class AccountPage extends BasePage {
    private static final String BASE_URL = ACCOUNT_BASE_URL;
    private static final String PAGE_NAME = "Account page";


    public AccountPage(WebDriver driver) {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }
}
