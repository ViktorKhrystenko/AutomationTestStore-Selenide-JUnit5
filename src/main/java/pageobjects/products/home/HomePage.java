package pageobjects.products.home;

import exceptions.PageNavigationException;
import org.openqa.selenium.WebDriver;
import pageobjects.products.PageWithProducts;

import static constants.url.BaseUrls.HOME_BASE_URL;

public class HomePage extends PageWithProducts {

    public HomePage(WebDriver driver) throws PageNavigationException {
        super(driver, HOME_BASE_URL, "Home page");
    }
}
