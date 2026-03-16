package pageobjects.checkout;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderPage;
import pageobjects.components.producttable.ProductTable;
import utils.datagenerator.DataGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static constants.BaseUrls.CART_BASE_URL;
import static utils.FloatNumberRounder.round;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class CartPage extends BasePage {
    private static final String BASE_URL = CART_BASE_URL;
    private static final String PAGE_NAME = "Cart page";

    private static final By PRODUCT_TABLE_ROWS_LOCATOR = By.xpath("div[class~=\"product-list\"] tr:has(> td)");

    private static final By SUBTOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Sub-Total:']/parent::td/following-sibling::td");
    private static final By SHIPPING_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Flat Shipping Rate:']/parent::td/following-sibling::td");
    private static final By TOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//*[@id='totals_table']//span[text()='Total:']/parent::td/following-sibling::td");

    private static final By COUNTRY_DROPDOWN_LOCATOR = By.id("estimate_country");
    private static final By STATE_DROPDOWN_LOCATOR = By.id("estimate_country_zones");

    @FindBy(id = "cart_checkout1")
    private WebElement checkoutButton;

    @FindBy(id = "estimate_postcode")
    private WebElement zipCodeField;

    @Getter
    private ProductTable<CartProduct> productsInCart;


    public CartPage(WebDriver driver) {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        productsInCart = new ProductTable<>(driver, PRODUCT_TABLE_ROWS_LOCATOR,
                SUBTOTAL_PRICE_ELEMENT_LOCATOR, SHIPPING_PRICE_ELEMENT_LOCATOR,
                TOTAL_PRICE_ELEMENT_LOCATOR,
                productTableRow -> new CartProduct(driver, this, productTableRow));
        PageFactory.initElements(driver, this);
    }


    public CartPage selectRandomCountry(DataGenerator generator) {
        selectRandomOption(COUNTRY_DROPDOWN_LOCATOR, generator);
        return this;
    }

    public CartPage selectRandomState(DataGenerator generator) {
        selectRandomOption(STATE_DROPDOWN_LOCATOR, generator);
        return this;
    }

    public CartPage enterZipCode(String zipCode) {
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
        return this;
    }

    public CheckoutConfirmPage clickOnCheckoutButton() {
        checkoutButton.click();
        waitUntilPageIsLoaded();
        return new CheckoutConfirmPage(driver);
    }


    public static class CartProduct extends OrderPage.Product {
        private static final By QUANTITY_FIELD_LOCATOR = By.xpath("//input[contains(@name, 'quantity')]");
        private static final By TOTAL_PRICE_ELEMENT_LOCATOR = By.cssSelector("td:nth-child(6)");
        private static final By REMOVE_PRODUCT_BUTTON_LOCATOR = By.cssSelector("a[href*=remove]");

        private final CartPage cartPage;

        private final WebElement quantityField;
        private final WebElement totalPriceElement;
        private final WebElement removeProductButton;


        public CartProduct(WebDriver driver, CartPage cartPage, String productName, String modelNumber,
                           double unitPrice, long quantity, double totalPrice,
                           WebElement removeProductButton, WebElement quantityField, WebElement totalPriceElement) {
            super(driver, productName, modelNumber, unitPrice, quantity, totalPrice);
            this.cartPage = cartPage;
            this.removeProductButton = removeProductButton;
            this.quantityField = quantityField;
            this.totalPriceElement = totalPriceElement;
            setQuantity(quantity);
        }

        public CartProduct(WebDriver driver, CartPage cartPage, String productName, String modelNumber,
                           double unitPrice, long quantity,
                           WebElement removeProductButton, WebElement quantityField, WebElement totalPriceElement) {
            super(driver, productName, modelNumber, unitPrice, quantity);
            this.cartPage = cartPage;
            this.removeProductButton = removeProductButton;
            this.quantityField = quantityField;
            this.totalPriceElement = totalPriceElement;
            setQuantity(quantity);
        }

        public CartProduct(WebDriver driver, CartPage cartPage, WebElement cartTableRow) {
            super(driver, cartTableRow);
            this.cartPage = cartPage;
            quantityField = cartTableRow.findElement(QUANTITY_FIELD_LOCATOR);
            totalPriceElement = cartTableRow.findElement(TOTAL_PRICE_ELEMENT_LOCATOR);
            removeProductButton = cartTableRow.findElement(REMOVE_PRODUCT_BUTTON_LOCATOR);
            setQuantity(getQuantityFromField());
        }


        @Override
        public void setQuantity(long quantity) {
            this.quantity = quantity;
            if (quantityField != null) {
                long quantityOnField = getQuantityFromField();
                if (quantityOnField != quantity) {
                    quantityField.clear();
                    quantityField.sendKeys(String.valueOf(quantity));
                }
            }
            this.totalPrice = round(unitPrice * quantity, 2);
            if (totalPriceElement != null) {
                double totalPriceOnPage = parsePriceStringToDouble(totalPriceElement.getText());
                checkTotalPrice(totalPriceOnPage);
            }
        }

        @Override
        protected void parseTableRowIntoFields(WebElement tableRow) {
            List<String> productFields = Arrays.stream(tableRow.getText().split("\t")).toList();
            // sublist to remove first image column
            productFields = productFields.subList(1, productFields.size());
            productName = productFields.get(0).split("\n")[0];
            modelNumber = productFields.get(1);
            unitPrice = parsePriceStringToDouble(productFields.get(2));
        }


        public CartPage removeProductFromCart() {
            removeProductButton.click();
            waitUntilPageIsLoaded();
            return cartPage;
        }


        private long getQuantityFromField() {
            return Long.parseLong(quantityField.getDomProperty("value"));
        }
    }
}
