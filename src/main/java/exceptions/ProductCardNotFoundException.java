package exceptions;

public class ProductCardNotFoundException extends RuntimeException {
    public ProductCardNotFoundException(String productName, String pageName, String currentUrl) {
        super(String.format("Card of \"%s\" product is not found on %s with current url: %s",
                productName, pageName, currentUrl));
    }
}
