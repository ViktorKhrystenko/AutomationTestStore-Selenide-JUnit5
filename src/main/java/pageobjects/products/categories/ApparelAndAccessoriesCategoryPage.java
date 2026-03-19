package pageobjects.products.categories;

import exceptions.PageNavigationException;
import org.openqa.selenium.WebDriver;

public class ApparelAndAccessoriesCategoryPage extends CategoryPage {

    public ApparelAndAccessoriesCategoryPage(WebDriver driver) throws PageNavigationException {
        super(driver, 68, "Apparel & accessories category page");
    }
}
