package pageobjects.components.products.table.item;

import exceptions.WrongProductPriceCalculationException;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.checkout.CartPage;

import java.util.Arrays;
import java.util.List;

import static utils.FloatNumberRounder.round;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class CartProduct extends Product {
    private static final String PRODUCT_TABLE_ROW_LOCATOR_FORMAT_PATTERN =
            "//div[contains(@class, 'product-list')]//td[contains(text(), '%s')]/parent::tr";

    private static final By QUANTITY_FIELD_LOCATOR = By.xpath(".//input[contains(@name, 'quantity')]");
    private static final By TOTAL_PRICE_ELEMENT_LOCATOR = By.cssSelector("td:nth-child(6)");
    private static final By REMOVE_PRODUCT_BUTTON_LOCATOR = By.cssSelector("a[href*=remove]");

    private final CartPage cartPage;

    private WebElement cartTableRow;

    private WebElement quantityField;
    private WebElement totalPriceElement;
    private WebElement removeProductButton;

    @Getter
    private String modelNumber;


    public CartProduct(WebDriver driver, CartPage cartPage, WebElement cartTableRow) {
        super(driver, cartTableRow);
        this.cartPage = cartPage;
        this.cartTableRow = cartTableRow;
        reLocateElements();
        setInitializationQuantity(getQuantityFromField());
    }


    @Override
    public void setQuantity(long quantity) throws WrongProductPriceCalculationException {
        Allure.step("Set quantity of \"" + productName + "\" product to " + quantity,
                () -> setInitializationQuantity(quantity));
    }

    @Override
    protected void parseTableRowIntoFields(WebElement tableRow) {
        List<String> productFields = Arrays.stream(tableRow.getDomProperty("innerText")
                .split("\t")).toList();
        // sublist to remove first image column
        productFields = productFields.subList(1, productFields.size());
        productName = productFields.get(0).replace("\n", "");
        modelNumber = productFields.get(1);
        unitPrice = parsePriceStringToDouble(productFields.get(2));
    }


    public CartPage removeProductFromCart() {
        return Allure.step("Remove \"" + productName + "\" product from cart",
                () -> {
                    clickOnElementAndWaitPageLoad(removeProductButton);
                    cartPage.getProductTable().getProductNames();
                    return cartPage;
                });
    }


    private void reLocateElements() {
        cartTableRow = driver.findElement(By.xpath(String.format(
                PRODUCT_TABLE_ROW_LOCATOR_FORMAT_PATTERN, modelNumber)));
        quantityField = cartTableRow.findElement(QUANTITY_FIELD_LOCATOR);
        totalPriceElement = cartTableRow.findElement(TOTAL_PRICE_ELEMENT_LOCATOR);
        removeProductButton = cartTableRow.findElement(REMOVE_PRODUCT_BUTTON_LOCATOR);
    }

    private long getQuantityFromField() {
        return Long.parseLong(quantityField.getDomProperty("value"));
    }

    private void setInitializationQuantity(long quantity) throws WrongProductPriceCalculationException {
        this.quantity = quantity;
        if (quantityField != null) {
            long quantityOnField = getQuantityFromField();
            if (quantityOnField != quantity) {
                quantityField.clear();
                quantityField.sendKeys(String.valueOf(quantity));
                sendEnterAndWaitPageLoad(quantityField);
                if (cartPage.getProductTable().getProductNames().length == 0) {
                    return;
                }
                reLocateElements();
            }
        }
        this.totalPrice = round(unitPrice * quantity, 2);
        if (totalPriceElement != null) {
            waitUntilElementStopsBeingStale(totalPriceElement);
            double totalPriceOnPage = parsePriceStringToDouble(totalPriceElement.getText());
            checkTotalPrice(totalPriceOnPage);
        }
    }
}
