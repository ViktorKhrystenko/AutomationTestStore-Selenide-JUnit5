package pageobjects.products;

import exceptions.PageNavigationException;
import exceptions.ProductCardNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.BasePage;
import pageobjects.components.products.ProductCard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class PageWithProducts extends BasePage {
    private static final By PRODUCT_CARD_LOCATOR = By.xpath(
            "//div/div[@class='fixed_wrapper']/following-sibling::div[@class='thumbnail']/parent::div");

    protected final String BASE_URL;
    protected final String PAGE_NAME;

    private List<ProductCard> productCards;


    public PageWithProducts(WebDriver driver, String baseUrl, String pageName) throws PageNavigationException {
        super(driver);
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
        throw new ProductCardNotFoundException(productName, PAGE_NAME, driver.getCurrentUrl());
    }


    private void initProductCardsList() {
        productCards = new ArrayList<>();
        List<WebElement> productCardsElements = driver.findElements(PRODUCT_CARD_LOCATOR);
        for (WebElement productCardElement: productCardsElements) {
            productCards.add(new ProductCard(driver, productCardElement));
        }
    }
}
