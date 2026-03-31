package pageobjects.products;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import exceptions.PageNavigationException;
import exceptions.ProductCardNotFoundException;
import org.openqa.selenium.By;
import pageobjects.BasePage;
import pageobjects.components.products.ProductCard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$$;

public abstract class PageWithProducts extends BasePage {
    private static final By PRODUCT_CARD_LOCATOR = By.xpath(
            "//div/div[@class='fixed_wrapper']/following-sibling::div[@class='thumbnail']/parent::div");

    protected final String BASE_URL;
    protected final String PAGE_NAME;

    private List<ProductCard> productCards;


    public PageWithProducts(String baseUrl, String pageName) throws PageNavigationException {
        BASE_URL = baseUrl;
        PAGE_NAME = pageName;
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }


    public ProductCard getProductCard(String productName) throws ProductCardNotFoundException {
        initProductCardsList();
        for (ProductCard productCard: productCards) {
            if (productCard.getProductName().equalsIgnoreCase(productName)) {
                return productCard;
            }
        }
        throw new ProductCardNotFoundException(productName, PAGE_NAME, WebDriverRunner.url());
    }


    private void initProductCardsList() {
        productCards = new ArrayList<>();
        ElementsCollection productCardsElements = $$(PRODUCT_CARD_LOCATOR);
        for (SelenideElement productCardElement: productCardsElements) {
            productCards.add(new ProductCard(productCardElement));
        }
    }
}
