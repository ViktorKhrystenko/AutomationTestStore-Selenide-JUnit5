package pageobjects.account.history;

import dto.Address;
import exceptions.WrongProductPriceCalculationException;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.components.producttable.ProductTable;
import utils.DateUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static constants.BaseUrls.ORDER_BASE_URL;

import static constants.DateTimePatterns.ORDER_PAGE_PATTERN;

import static utils.StringFormatHelper.parsePriceStringToDouble;

import static utils.FloatNumberRounder.round;

public class OrderPage extends BasePage {
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

    @FindBy(xpath = "//td/b[text()='Shipping Address']/../address")
    private WebElement shippingAddressElement;

    @FindBy(xpath = "//td/b[text()='Payment Address']/../address")
    private WebElement paymentAddressElement;

    @FindBy(xpath = "//td/b[contains(text(), 'Order ID')]/parent::td")
    private WebElement orderFirstColumn;

    @FindBy(xpath = "//th[text()='Date Added']/parent::tr/following-sibling::tr/td[1]")
    private WebElement orderDateElement;

    @Getter
    private ProductTable<Product> orderedProducts;


    public OrderPage(WebDriver driver, int orderId) {
        super(driver);
        this.orderId = orderId;
        BASE_URL = ORDER_BASE_URL + String.valueOf(orderId);
        PAGE_NAME = String.format("Order #%d page", orderId);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        orderedProducts = new ProductTable<>(driver, PRODUCT_TABLE_ROWS_LOCATOR,
                SUBTOTAL_PRICE_ELEMENT_LOCATOR, SHIPPING_PRICE_ELEMENT_LOCATOR,
                TOTAL_PRICE_ELEMENT_LOCATOR,
                productTableRow -> new Product(driver, productTableRow));
        PageFactory.initElements(driver, this);
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


    @Getter
    public static class Product extends BasePage {
        protected String productName;
        protected String modelNumber;
        protected double unitPrice;
        protected double totalPrice;
        protected long quantity;


        public Product(WebDriver driver, String productName, String modelNumber,
                       double unitPrice, long quantity, double totalPrice) {
            super(driver);
            this.productName = productName;
            this.modelNumber = modelNumber;
            this.unitPrice = round(unitPrice, 2);
            setQuantity(quantity);
            checkTotalPrice(totalPrice);
        }

        public Product(WebDriver driver, String productName, String modelNumber,
                       double unitPrice, long quantity) {
            super(driver);
            this.productName = productName;
            this.modelNumber = modelNumber;
            this.unitPrice = round(unitPrice, 2);
            setQuantity(quantity);
        }

        public Product(WebDriver driver, WebElement tableRow) {
            super(driver);
            parseTableRowIntoFields(tableRow);
        }


        public void setQuantity(long quantity) {
            this.quantity = quantity;
            this.totalPrice = round(unitPrice * quantity, 2);
        }


        protected void checkTotalPrice(double totalPrice) {
            if (this.totalPrice != totalPrice) {
                throw new WrongProductPriceCalculationException(
                        this.productName, this.unitPrice, this.quantity, this.totalPrice, totalPrice);
            }
        }


        protected void parseTableRowIntoFields(WebElement tableRow) {
            List<String> productFields = Arrays.stream(tableRow.getText().split("\t")).toList();
            // sublist to remove first image column
            productFields = productFields.subList(1, productFields.size());
            productName = productFields.get(0).split("\n")[0];
            modelNumber = productFields.get(1);
            unitPrice = parsePriceStringToDouble(productFields.get(3));
            setQuantity(Integer.parseInt(productFields.get(2)));
            checkTotalPrice(parsePriceStringToDouble(productFields.get(4)));
        }
    }
}
