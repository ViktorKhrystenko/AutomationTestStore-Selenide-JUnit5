package pageobjects.login.logout;

import exceptions.PageNavigationException;
import pageobjects.BasePage;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.LOGOUT_BASE_URL;

public class LogoutPage extends BasePage {
    private static final String BASE_URL = LOGOUT_BASE_URL;
    private static final String PAGE_NAME = "Logout page";


    public LogoutPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }
}
