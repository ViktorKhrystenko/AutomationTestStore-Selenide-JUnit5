package pageobjects.checkout;

import com.codeborne.selenide.SelenideElement;
import dto.Address;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pageobjects.BasePage;
import pageobjects.PageWithProductTable;
import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.CheckoutConfirmProduct;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static constants.url.BaseUrls.CHECKOUT_CONFIRM_BASE_URL;

public class CheckoutConfirmPage extends BasePage implements PageWithProductTable<CheckoutConfirmProduct> {
    private static final String BASE_URL = CHECKOUT_CONFIRM_BASE_URL;
    private static final String PAGE_NAME = "Checkout confirm page";

    private static final By PRODUCT_TABLE_ROWS_LOCATOR = By.cssSelector("table.confirm_products tr");

    private static final By SUBTOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//span[text()='Sub-Total:']/parent::td/following-sibling::td");
    private static final By SHIPPING_PRICE_ELEMENT_LOCATOR =
            By.xpath("//span[text()='Flat Shipping Rate:']/parent::td/following-sibling::td");
    private static final By TOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//span[text()='Total:']/parent::td/following-sibling::td");

    private SelenideElement shippingAddressFullNameElement = $(".confirm_shippment_options td:nth-child(1)");

    private SelenideElement shippingAddressElement = $(".confirm_shippment_options td:nth-child(2)");

    private SelenideElement paymentAddressFullNameElement = $(".confirm_payment_options td:nth-child(1)");

    private SelenideElement paymentAddressElement = $(".confirm_payment_options td:nth-child(2)");

    private SelenideElement confirmOrderButton = $("#checkout_btn");

    private ProductTable<CheckoutConfirmProduct> productsInCart;


    public CheckoutConfirmPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        productsInCart = new ProductTable<>(PRODUCT_TABLE_ROWS_LOCATOR,
                SUBTOTAL_PRICE_ELEMENT_LOCATOR, SHIPPING_PRICE_ELEMENT_LOCATOR,
                TOTAL_PRICE_ELEMENT_LOCATOR,
                productTableRow -> new CheckoutConfirmProduct(productTableRow));
    }


    @Override
    public ProductTable<CheckoutConfirmProduct> getProductTable() {
        return productsInCart;
    }


    public Address getShippingAddress() {
        return new Address(shippingAddressFullNameElement, shippingAddressElement);
    }

    public Address getPaymentAddress() {
        return new Address(paymentAddressFullNameElement, paymentAddressElement);
    }


    @Step("Click on \"Confirm order\" button")
    public SuccessfulCheckoutPage clickOnConfirmOrderButton() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(confirmOrderButton);
        return new SuccessfulCheckoutPage();
    }
}
