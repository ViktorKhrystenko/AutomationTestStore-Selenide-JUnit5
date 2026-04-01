package pageobjects.products.home;

import exceptions.PageNavigationException;
import pageobjects.products.PageWithProducts;

import static constants.url.BaseUrls.HOME_BASE_URL;

public class HomePage extends PageWithProducts {

    public HomePage() throws PageNavigationException {
        super(HOME_BASE_URL, "Home page");
    }
}
