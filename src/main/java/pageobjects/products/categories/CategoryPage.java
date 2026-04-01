package pageobjects.products.categories;

import exceptions.PageNavigationException;
import pageobjects.products.PageWithProducts;

import static constants.url.BaseUrls.CATEGORY_BASE_URL;

public abstract class CategoryPage extends PageWithProducts {

    public CategoryPage(int categoryAndPath, String pageName) throws PageNavigationException {
        super(CATEGORY_BASE_URL + String.valueOf(categoryAndPath), pageName);
    }
}
