package pageobjects.products.home;

import org.openqa.selenium.WebDriver;
import pageobjects.products.PageWithProducts;

import static constants.BaseUrls.HOME_BASE_URL;

public class HomePage extends PageWithProducts {

    public HomePage(WebDriver driver) {
        super(driver, HOME_BASE_URL, "Home page");
    }
}
