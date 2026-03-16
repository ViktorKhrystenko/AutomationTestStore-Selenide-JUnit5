package pageobjects.components.producttable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.BasePage;
import pageobjects.account.history.OrderPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static utils.FloatNumberRounder.round;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class ProductTable<T extends OrderPage.Product> extends BasePage {
    private final By PRODUCT_TABLE_ROWS_LOCATOR;

    private final By SUBTOTAL_PRICE_ELEMENT_LOCATOR;
    private final By SHIPPING_PRICE_ELEMENT_LOCATOR;
    private final By TOTAL_PRICE_ELEMENT_LOCATOR;

    private final Function<WebElement, T> INIT_PRODUCT_FUNCTION;


    private List<T> products;


    public ProductTable(WebDriver driver, By productTableRowsLocator,
                        By subtotalPriceElementLocator, By shippingPriceElementLocator,
                        By totalPriceElementLocator, Function<WebElement, T> initProductFunction) {
        super(driver);
        this.PRODUCT_TABLE_ROWS_LOCATOR = productTableRowsLocator;
        this.SUBTOTAL_PRICE_ELEMENT_LOCATOR = subtotalPriceElementLocator;
        this.SHIPPING_PRICE_ELEMENT_LOCATOR = shippingPriceElementLocator;
        this.TOTAL_PRICE_ELEMENT_LOCATOR = totalPriceElementLocator;
        this.INIT_PRODUCT_FUNCTION = initProductFunction;
    }


    public Optional<T> getProduct(String productName) {
        initProductsInCartList();
        for (T productFromCart: products) {
            if (productFromCart.getProductName().equals(productName)) {
                return Optional.of(productFromCart);
            }
        }
        return Optional.empty();
    }


    public double getSubtotalPrice() {
        return parsePriceStringToDouble(
                driver.findElement(SUBTOTAL_PRICE_ELEMENT_LOCATOR).getText());
    }

    public double getShippingPrice() {
        return parsePriceStringToDouble(
                driver.findElement(SHIPPING_PRICE_ELEMENT_LOCATOR).getText());
    }

    public double getTotalPrice() {
        return parsePriceStringToDouble(
                driver.findElement(TOTAL_PRICE_ELEMENT_LOCATOR).getText());
    }


    public double calculateSubtotalPrice() {
        return calculateSubtotalPrice((List<OrderPage.Product>) products);
    }

    public double calculateTotalPrice() {
        return calculateTotalPrice(calculateSubtotalPrice() + getShippingPrice(), 2);
    }

    public boolean isTotalPriceCalculatedCorrectly(List<OrderPage.Product> productsFromCart, double shippingPrice) {
        return calculateTotalPrice() == calculateTotalPrice(
                calculateSubtotalPrice(productsFromCart), shippingPrice);
    }


    private void initProductsInCartList() {
        products = new ArrayList<>();
        List<WebElement> productTableRows = driver.findElements(PRODUCT_TABLE_ROWS_LOCATOR);
        for (WebElement productTableRow: productTableRows) {
            products.add(INIT_PRODUCT_FUNCTION.apply(productTableRow));
        }
    }


    private double calculateSubtotalPrice(List<OrderPage.Product> products) {
        double subtotal = 0.0;
        for (OrderPage.Product product: products) {
            subtotal += product.getTotalPrice();
        }
        return round(subtotal, 2);
    }

    private double calculateTotalPrice(double subtotalPrice, double shippingPrice) {
        return round(subtotalPrice + shippingPrice, 2);
    }
}
