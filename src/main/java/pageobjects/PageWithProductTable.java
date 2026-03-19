package pageobjects;

import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.Product;

public interface PageWithProductTable<T extends Product> {
    ProductTable<T> getProductTable();

    default boolean areAllProductsDisplayedOnPage(String... productNames) {
        ProductTable<? extends Product> productTable = getProductTable();
        try {
            for (String productName: productNames) {
                productTable.getProduct(productName);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
