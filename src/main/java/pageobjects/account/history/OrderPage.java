package pageobjects.account.history;

import com.codeborne.selenide.SelenideElement;
import dto.Address;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import pageobjects.BasePage;
import pageobjects.PageWithProductTable;
import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.OrderPageProduct;
import utils.DateUtil;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrls.ORDER_BASE_URL;

import static constants.DateTimePatterns.ORDER_PAGE_PATTERN;

public class OrderPage extends BasePage implements PageWithProductTable<OrderPageProduct> {
    private static final By PRODUCT_TABLE_ROWS_LOCATOR = By.cssSelector("table.invoice_products tr:has(> td)");

    private static final By SUBTOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//td[text()='Sub-Total:']/following-sibling::td");
    private static final By SHIPPING_PRICE_ELEMENT_LOCATOR =
            By.xpath("//td[text()='Flat Shipping Rate:']/following-sibling::td");
    private static final By TOTAL_PRICE_ELEMENT_LOCATOR =
            By.xpath("//td[text()='Total:']/following-sibling::td");

    private final String BASE_URL;
    private final String PAGE_NAME;
    private final int orderId;

    private SelenideElement shippingAddressElement = $x("//td/b[text()='Shipping Address']/../address");

    private SelenideElement paymentAddressElement = $x("//td/b[text()='Payment Address']/../address");

    private SelenideElement orderFirstColumn = $x("//td/b[contains(text(), 'Order ID')]/parent::td");

    private SelenideElement orderDateElement = $x("//th[text()='Date Added']/parent::tr/following-sibling::tr/td[1]");

    private ProductTable<OrderPageProduct> orderedProducts;


    public OrderPage(int orderId) throws PageNavigationException {
        this.orderId = orderId;
        BASE_URL = ORDER_BASE_URL + String.valueOf(orderId);
        PAGE_NAME = String.format("Order #%d page", orderId);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        orderedProducts = new ProductTable<>(PRODUCT_TABLE_ROWS_LOCATOR,
                SUBTOTAL_PRICE_ELEMENT_LOCATOR, SHIPPING_PRICE_ELEMENT_LOCATOR,
                TOTAL_PRICE_ELEMENT_LOCATOR,
                productTableRow -> new OrderPageProduct(productTableRow));
    }


    @Override
    public ProductTable<OrderPageProduct> getProductTable() {
        return orderedProducts;
    }

    public String getEmail() {
        List<String> orderFirstColumnText = Arrays.stream(
                orderFirstColumn.getText().split("\n")).toList();
        int emailIndex = orderFirstColumnText.indexOf("E-Mail") + 1;
        return orderFirstColumnText.get(emailIndex);
    }

    public Address getShippingAddress() {
        return new Address(shippingAddressElement);
    }

    public Address getPaymentAddress() {
        return new Address(paymentAddressElement);
    }

    public LocalDateTime getOrderDate() {
        return DateUtil.parseToDate(orderDateElement.getText(), ORDER_PAGE_PATTERN);
    }


    public boolean checkUserInfoOnOrderPage(User user, String state, String country) {
        return Allure.step("Check user info on order page",
                () -> {
                    String actualLocation = Address.getLocation(user.getCity(), user.getZipCode(), state);
                    String actualFullName = Address.getFullName(user.getFirstName(), user.getLastName());
                    String emailFromPage = getEmail();
                    Address shippingAddress = getShippingAddress();
                    Address paymentAddress = getPaymentAddress();
                    Allure.addAttachment("Actual shipping location", actualLocation);
                    Allure.addAttachment("Displayed shipping location", shippingAddress.getLocation());
                    Allure.addAttachment("Actual payment location", actualLocation);
                    Allure.addAttachment("Displayed payment location", paymentAddress.getLocation());

                    Allure.addAttachment("Actual shipping address", user.getAddress_1());
                    Allure.addAttachment("Displayed shipping address", shippingAddress.getAddress());
                    Allure.addAttachment("Actual payment address", user.getAddress_1());
                    Allure.addAttachment("Displayed payment address", paymentAddress.getAddress());

                    Allure.addAttachment("Actual shipping country", country);
                    Allure.addAttachment("Displayed shipping country", shippingAddress.getCountry());
                    Allure.addAttachment("Actual payment country", country);
                    Allure.addAttachment("Displayed payment country", paymentAddress.getCountry());

                    Allure.addAttachment("Actual shipping full name", actualFullName);
                    Allure.addAttachment("Displayed shipping full name", shippingAddress.getFullName());
                    Allure.addAttachment("Actual payment full name", actualFullName);
                    Allure.addAttachment("Displayed payment full name", paymentAddress.getFullName());

                    Allure.addAttachment("Actual email", user.getEmail());
                    Allure.addAttachment("Displayed email", emailFromPage);
                    return user.getEmail().equals(emailFromPage)
                            && actualFullName.equals(shippingAddress.getFullName())
                            && actualFullName.equals(paymentAddress.getFullName())
                            && actualLocation.equals(shippingAddress.getLocation())
                            && actualLocation.equals(paymentAddress.getLocation())
                            && user.getAddress_1().equals(shippingAddress.getAddress())
                            && user.getAddress_1().equals(paymentAddress.getAddress())
                            && country.equals(shippingAddress.getCountry())
                            && country.equals(paymentAddress.getCountry());
                });
    }

    public boolean checkOrderDate(Temporal actualOrderDate, long acceptableSecondsDelta) {
        return Allure.step("Check order date accuracy",
                () -> {
                    LocalDateTime displayedOrderDate = getOrderDate();
                    Allure.addAttachment("Actual order date", actualOrderDate.toString());
                    Allure.addAttachment("Displayed order date", displayedOrderDate.toString());
                    Allure.addAttachment("Acceptable seconds delta", String.valueOf(acceptableSecondsDelta));
                    return DateUtil.areDatesEqual(displayedOrderDate, actualOrderDate, acceptableSecondsDelta);
                });
    }
}
