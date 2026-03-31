package pageobjects.products;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import pageobjects.checkout.CartPage;
import pageobjects.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrlFormatPatterns.PRODUCT_PAGE_FORMAT_PATTERN;
import static constants.url.BaseUrlFormatPatterns.PRODUCT_WITH_PATH_PAGE_FORMAT_PATTERN;
import static constants.url.BaseUrlRegexPatterns.PRODUCT_PAGE_REGEX_PATTERN;

import static utils.StringFormatHelper.leftOnlyCharactersInRange;

public class ProductPage extends BasePage {
    private final String BASE_URL;
    private final String PAGE_NAME;

    private SelenideElement inStockElement = $x("//span[text()='Availability:']/parent::li");

    private SelenideElement quantityField = $("#product_quantity");

    private SelenideElement quantityPerOrderLimitElement = $x("//*[@id='product_quantity']/parent::div/following-sibling::div[contains(text(), 'limit')]");

    private SelenideElement addToCartButton = $("a.cart");

    private SelenideElement outOfStockButton = $(".nostock");


    public ProductPage(int productId, String productName) throws PageNavigationException {
        BASE_URL = String.format(PRODUCT_PAGE_FORMAT_PATTERN, productId);
        PAGE_NAME = String.format("%s product page", productName);
        checkLocation(PRODUCT_PAGE_REGEX_PATTERN, PAGE_NAME);
    }

    public ProductPage(int path, int productId, String productName) throws PageNavigationException {
        BASE_URL = String.format(PRODUCT_WITH_PATH_PAGE_FORMAT_PATTERN, path, productId);
        PAGE_NAME = String.format("%s product page", productName);
        checkLocation(PRODUCT_PAGE_REGEX_PATTERN, PAGE_NAME);
    }


    @Step("Set product quantity to {0}")
    public ProductPage setQuantity(long quantity) {
        quantityField.clear();
        enterText(quantityField, String.valueOf(quantity));
        return this;
    }

    @Step("Click on \"Add to cart\" button")
    public CartPage clickOnAddToCartButton() throws PageNavigationException, NoSuchElementException {
        clickOnElementAndWaitPageLoad(addToCartButton);
        return new CartPage();
    }

    @Step("Click on \"Out of stock\" button")
    public void clickOnOutOfStockButton() throws NoSuchElementException {
        outOfStockButton.click();
        if (!WebDriverRunner.url().equals(BASE_URL)) {
            throw new IllegalStateException(String.format("Click on \"Out of stock\" button caused url change. Url had to stay: %s . " +
                    "Current url: %s",
                    BASE_URL, WebDriverRunner.url()));
        }
    }


    public long getInStockQuantity() throws IllegalStateException {
        if (!inStockElement.isDisplayed()) {
            throw new IllegalStateException("There is no in stock element displayed on product page with url: " +
                    WebDriverRunner.url());
        }
        String inStockString = inStockElement.text().split("\n")[1];
        if (inStockString.equals("In Stock")) {
            throw new IllegalStateException("Exact in stock quantity is not specified on product page with url: " +
                    WebDriverRunner.url());
        }
        return Long.parseLong(inStockString
                .substring(0, inStockString.indexOf("In Stock"))
                .trim());
    }

    public long getQuantityPerOrderLimit() throws IllegalStateException {
        if (!quantityPerOrderLimitElement.isDisplayed()) {
            throw new IllegalStateException("There is no quantity per order limit element displayed on product page with url: " +
                    WebDriverRunner.url());
        }
        String qualityPerOrderString = leftOnlyCharactersInRange(inStockElement.text(), '0', '9');
        return Long.parseLong(qualityPerOrderString);
    }
}
