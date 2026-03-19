package pageobjects;

import io.qameta.allure.Allure;
import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.Product;

import java.util.Arrays;

public interface PageWithProductTable<T extends Product> {
    ProductTable<T> getProductTable();

    default boolean areAllProductsDisplayedOnPage(String... productNames) {
        return Allure.step("Check if all products are displayed",
                () -> {
                    ProductTable<? extends Product> productTable = getProductTable();
                    Allure.addAttachment("Checked products", Arrays.toString(productNames));
                    Allure.addAttachment("Displayed products", Arrays.toString(productTable.getProductNames()));
                    try {
                        for (String productName: productNames) {
                            productTable.getProduct(productName);
                        }
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                    return true;
                });
    }
}
