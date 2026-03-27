package pageobjects.products;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import pageobjects.checkout.CartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;

import static constants.url.BaseUrlFormatPatterns.PRODUCT_PAGE_FORMAT_PATTERN;
import static constants.url.BaseUrlFormatPatterns.PRODUCT_WITH_PATH_PAGE_FORMAT_PATTERN;
import static constants.url.BaseUrlRegexPatterns.PRODUCT_PAGE_REGEX_PATTERN;

import static utils.StringFormatHelper.leftOnlyCharactersInRange;

public class ProductPage extends BasePage {
    private final String BASE_URL;
    private final String PAGE_NAME;

    @FindBy(xpath = "//span[text()='Availability:']/parent::li")
    private WebElement inStockElement;

    @FindBy(id = "product_quantity")
    private WebElement quantityField;

    @FindBy(xpath = "//*[@id='product_quantity']/parent::div/following-sibling::div[contains(text(), 'limit')]")
    private WebElement quantityPerOrderLimitElement;

    @FindBy(css = "a.cart")
    private WebElement addToCartButton;

    @FindBy(className = "nostock")
    private WebElement outOfStockButton;


    public ProductPage(WebDriver driver, int productId, String productName) throws PageNavigationException {
        super(driver);
        BASE_URL = String.format(PRODUCT_PAGE_FORMAT_PATTERN, productId);
        PAGE_NAME = String.format("%s product page", productName);
        checkLocation(PRODUCT_PAGE_REGEX_PATTERN, PAGE_NAME);
        PageFactory.initElements(driver, this);
    }

    public ProductPage(WebDriver driver, int path, int productId, String productName) throws PageNavigationException {
        super(driver);
        BASE_URL = String.format(PRODUCT_WITH_PATH_PAGE_FORMAT_PATTERN, path, productId);
        PAGE_NAME = String.format("%s product page", productName);
        checkLocation(PRODUCT_PAGE_REGEX_PATTERN, PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    @Step("Set product quantity to {0}")
    public ProductPage setQuantity(long quantity) {
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(quantity));
        return this;
    }

    @Step("Click on \"Add to cart\" button")
    public CartPage clickOnAddToCartButton() throws PageNavigationException, NoSuchElementException {
        clickOnElementAndWaitPageLoad(addToCartButton);
        return new CartPage(driver);
    }

    @Step("Click on \"Out of stock\" button")
    public void clickOnOutOfStockButton() throws NoSuchElementException {
        outOfStockButton.click();
        if (!driver.getCurrentUrl().equals(BASE_URL)) {
            throw new IllegalStateException(String.format("Click on \"Out of stock\" button caused url change. Url had to stay: %s . " +
                    "Current url: %s",
                    BASE_URL, driver.getCurrentUrl()));
        }
    }


    public long getInStockQuantity() throws IllegalStateException {
        if (!inStockElement.isDisplayed()) {
            throw new IllegalStateException("There is no in stock element displayed on product page with url: " +
                    driver.getCurrentUrl());
        }
        String inStockString = inStockElement.getText().split("\n")[1];
        if (inStockString.equals("In Stock")) {
            throw new IllegalStateException("Exact in stock quantity is not specified on product page with url: " +
                    driver.getCurrentUrl());
        }
        return Long.parseLong(inStockString
                .substring(0, inStockString.indexOf("In Stock"))
                .trim());
    }

    public long getQuantityPerOrderLimit() throws IllegalStateException {
        if (!quantityPerOrderLimitElement.isDisplayed()) {
            throw new IllegalStateException("There is no quantity per order limit element displayed on product page with url: " +
                    driver.getCurrentUrl());
        }
        String qualityPerOrderString = leftOnlyCharactersInRange(inStockElement.getText(), '0', '9');
        return Long.parseLong(qualityPerOrderString);
    }
}
