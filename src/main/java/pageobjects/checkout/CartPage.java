package pageobjects.checkout;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageobjects.BasePage;
import pageobjects.PageWithProductTable;
import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.CartProduct;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;
import static constants.FormValues.DESELECTED_OPTION;
import static constants.url.BaseUrls.CART_BASE_URL;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static pageobjects.registration.RegistrationDropdown.REGION_STATE_DROPDOWN;

public class CartPage extends BasePage implements PageWithProductTable<CartProduct> {
    private static final String BASE_URL = CART_BASE_URL;
    private static final String PAGE_NAME = "Cart page";

    private static final By PRODUCT_TABLE_ROWS_LOCATOR = By.cssSelector("div[class~=\"product-list\"] tr:has(> td)");

    private static final By SUBTOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Sub-Total:']/parent::td/following-sibling::td");
    private static final By SHIPPING_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Flat Shipping Rate:']/parent::td/following-sibling::td");
    private static final By TOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Total:']/parent::td/following-sibling::td");

    private static final By COUNTRY_DROPDOWN_LOCATOR = By.id("estimate_country");
    private static final By STATE_DROPDOWN_LOCATOR = By.id("estimate_country_zones");

    private SelenideElement updateCartButton = $("#cart_update");

    private SelenideElement checkoutButton = $("#cart_checkout1");

    private SelenideElement zipCodeField = $("#estimate_postcode");

    private ProductTable<CartProduct> productsInCart;


    public CartPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        productsInCart = new ProductTable<>(PRODUCT_TABLE_ROWS_LOCATOR,
                SUBTOTAL_PRICE_ELEMENT_LOCATOR, SHIPPING_PRICE_ELEMENT_LOCATOR,
                TOTAL_PRICE_ELEMENT_LOCATOR,
                productTableRow -> new CartProduct(this, productTableRow));
    }


    @Override
    public ProductTable<CartProduct> getProductTable() {
        return productsInCart;
    }


    @Step("Clear cart")
    public CartPage clearCart() {
        String[] productNames = productsInCart.getProductNames();
        for (String productName: productNames) {
            productsInCart.getProduct(productName).removeProductFromCart();
        }
        return this;
    }

    @Step("Select random country")
    public CartPage selectRandomCountry() {
        Select stateDropdown = new Select(WebDriverRunner.getWebDriver().findElement(STATE_DROPDOWN_LOCATOR));
        WebElement firstStateOption = stateDropdown
                .getOptions().stream()
                .filter(option -> !option.getText().equals(DESELECTED_OPTION)
                        & !option.getText().equals(stateDropdown.getFirstSelectedOption().getText()))
                .toList().get(0);
        selectRandomOption(COUNTRY_DROPDOWN_LOCATOR);
        Wait().until(stalenessOf(firstStateOption));
        return this;
    }

    public String getSelectedCountry() {
        return getSelectedOption(COUNTRY_DROPDOWN_LOCATOR).text();
    }

    @Step("Select random state")
    public CartPage selectRandomState() {
        selectRandomOption(STATE_DROPDOWN_LOCATOR);
        return this;
    }

    public String getSelectedState() {
        return getSelectedOption(STATE_DROPDOWN_LOCATOR).text();
    }

    @Step("Enter ZIP code")
    public CartPage enterZipCode(String zipCode) {
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
        return this;
    }

    public String getZipCode() {
        return zipCodeField.getDomProperty("value");
    }

    @Step("Click on \"Checkout\" button")
    public CheckoutConfirmPage clickOnCheckoutButton() throws PageNavigationException, NoSuchElementException {
        clickOnElementAndWaitPageLoad(checkoutButton);
        return new CheckoutConfirmPage();
    }

    public CartPage clickOnUpdateCartButton() {
        clickOnElementAndWaitPageLoad(updateCartButton);
        return this;
    }


    public boolean areEstimateShippingAndTaxesFieldsAreFilledCorrectly(String country, String state, String zipCode) {
        if (getSelectedCountry().equals(country)
                && getSelectedState().equalsIgnoreCase(state)
                && getZipCode().equalsIgnoreCase(zipCode)) {
            return true;
        }
        return false;
    }
}
