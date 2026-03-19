package pageobjects.products.categories;

import org.openqa.selenium.WebDriver;
import pageobjects.products.PageWithProducts;

import static constants.url.BaseUrls.CATEGORY_BASE_URL;

public abstract class CategoryPage extends PageWithProducts {

    public CategoryPage(WebDriver driver, int categoryAndPath, String pageName) {
        super(driver, CATEGORY_BASE_URL + String.valueOf(categoryAndPath), pageName);
    }
}
