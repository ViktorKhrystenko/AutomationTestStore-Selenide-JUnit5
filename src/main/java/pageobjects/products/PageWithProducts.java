package pageobjects.products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static constants.BaseUrls.PRODUCT_BASE_URL;

public abstract class PageWithProducts extends BasePage {
    private static final By PRODUCT_CARD_LOCATOR = By.xpath(
            "//div/div[@class='fixed_wrapper']/following-sibling::div[@class='thumbnail']/parent::div");

    protected final String BASE_URL;
    protected final String PAGE_NAME;

    private List<ProductCard> productCards;


    public PageWithProducts(WebDriver driver, String baseUrl, String pageName) {
        super(driver);
        BASE_URL = baseUrl;
        PAGE_NAME = pageName;
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }


    public Optional<ProductCard> getProductCard(String productName) {
        initProductCardsList();
        for (ProductCard productCard: productCards) {
            if (productCard.getProductName().equals(productName)) {
                return Optional.of(productCard);
            }
        }
        return Optional.empty();
    }


    private void initProductCardsList() {
        productCards = new ArrayList<>();
        List<WebElement> productCardsElements = driver.findElements(PRODUCT_CARD_LOCATOR);
        for (WebElement productCardElement: productCardsElements) {
            productCards.add(new ProductCard(driver, productCardElement));
        }
    }


    public static class ProductCard extends BasePage {
        private static final By PRODUCT_TITLE_LOCATOR = By.className("prdocutname");

        private WebElement productCardElement;

        private WebElement productTitleElement;


        public ProductCard(WebDriver driver, WebElement productCardElement) {
            super(driver);
            this.productCardElement = productCardElement;
            productTitleElement = productCardElement.findElement(PRODUCT_TITLE_LOCATOR);
        }


        public ProductPage clickOnProductTitle() {
            productTitleElement.click();
            String productName = getProductName();
            int productId = Integer.parseInt(productTitleElement.getDomProperty("href")
                    .replace(PRODUCT_BASE_URL, ""));
            waitUntilPageIsLoaded();
            return new ProductPage(driver, productId, productName);
        }

        public String getProductName() {
            return productTitleElement.getText();
        }
    }
}
