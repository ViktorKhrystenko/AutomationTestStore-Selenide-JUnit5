package pageobjects.components.products.table;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import pageobjects.BasePage;
import pageobjects.components.products.table.item.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.$$;
import static utils.FloatNumberRounder.round;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class ProductTable<T extends Product> extends BasePage {
    private final String PAGE_URL;

    private final By PRODUCT_TABLE_ROWS_LOCATOR;

    private final By SUBTOTAL_PRICE_ELEMENT_LOCATOR;
    private final By SHIPPING_PRICE_ELEMENT_LOCATOR;
    private final By TOTAL_PRICE_ELEMENT_LOCATOR;

    private final Function<SelenideElement, T> INIT_PRODUCT_FUNCTION;


    private List<T> products;

    private double subtotalPrice;
    private double shippingPrice;
    private double totalPrice;


    public ProductTable(By productTableRowsLocator,
                        By subtotalPriceElementLocator, By shippingPriceElementLocator,
                        By totalPriceElementLocator, Function<SelenideElement, T> initProductFunction) {
        PAGE_URL = WebDriverRunner.url();
        this.PRODUCT_TABLE_ROWS_LOCATOR = productTableRowsLocator;
        this.SUBTOTAL_PRICE_ELEMENT_LOCATOR = subtotalPriceElementLocator;
        this.SHIPPING_PRICE_ELEMENT_LOCATOR = shippingPriceElementLocator;
        this.TOTAL_PRICE_ELEMENT_LOCATOR = totalPriceElementLocator;
        this.INIT_PRODUCT_FUNCTION = initProductFunction;
    }


    public T getProduct(String productName) throws IllegalArgumentException {
        if (isOnInitialPage()) {
            initProductsInCartList();
        }
        for (T product: products) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        throw new IllegalArgumentException(String.format("There is (or was) no product with \"%s\" name on page with url: %s",
                productName, WebDriverRunner.url()));
    }

    public String[] getProductNames() {
        if (isOnInitialPage()) {
            initProductsInCartList();
        }
        List<String> productNames = new ArrayList<>();
        for (T product: products) {
            productNames.add(product.getProductName());
        }
        return productNames.toArray(new String[0]);
    }


    public double getSubtotalPrice() {
        if (isOnInitialPage()) {
            setSubtotalPriceFromPage();
        }
        return subtotalPrice;
    }

    public double getShippingPrice() {
        if (isOnInitialPage()) {
            setSippingPriceFromPage();
        }
        return shippingPrice;
    }

    public double getTotalPrice() {
        if (isOnInitialPage()) {
            setTotalPriceFromPage();
        }
        return totalPrice;
    }


    public double calculateSubtotalPrice() {
        return calculateSubtotalPrice(this);
    }

    public double calculateTotalPrice() {
        return calculateTotalPrice(calculateSubtotalPrice() + getShippingPrice(), 2);
    }

    public boolean isTotalPriceCalculatedCorrectly(ProductTable<? extends Product> productTable) {
        return Allure.step("Check if total prise is calculated correctly",
                () -> {
                    double actualTotalPrice = productTable.calculateTotalPrice();
                    double displayedTotalPrice = calculateTotalPrice();
                    Allure.addAttachment("Actual total price", String.valueOf(actualTotalPrice));
                    Allure.addAttachment("Displayed total price", String.valueOf(displayedTotalPrice));
                    return displayedTotalPrice == actualTotalPrice;
                });
    }


    private void initProductsInCartList() {
        products = new ArrayList<>();
        ElementsCollection productTableRows = $$(PRODUCT_TABLE_ROWS_LOCATOR);
        for (SelenideElement productTableRow: productTableRows) {
            products.add(INIT_PRODUCT_FUNCTION.apply(productTableRow));
        }
        if (!products.isEmpty()) {
            setSubtotalPriceFromPage();
            setSippingPriceFromPage();
            setTotalPriceFromPage();
        }
    }

    private void setSubtotalPriceFromPage() {
        subtotalPrice = parsePriceStringToDouble(getTextFromElementLocated(SUBTOTAL_PRICE_ELEMENT_LOCATOR));
    }

    private void setSippingPriceFromPage() {
        shippingPrice = parsePriceStringToDouble(getTextFromElementLocated(SHIPPING_PRICE_ELEMENT_LOCATOR));
    }

    private void setTotalPriceFromPage() {
        totalPrice = parsePriceStringToDouble(getTextFromElementLocated(TOTAL_PRICE_ELEMENT_LOCATOR));
    }


    private double calculateSubtotalPrice(ProductTable<? extends Product> productTable) {
        double subtotal = 0.0;
        for (Product product: productTable.products) {
            subtotal += product.getTotalPrice();
        }
        return round(subtotal, 2);
    }

    private double calculateTotalPrice(double subtotalPrice, double shippingPrice) {
        return round(subtotalPrice + shippingPrice, 2);
    }


    private boolean isOnInitialPage() {
        return WebDriverRunner.url().equals(PAGE_URL);
    }
}
