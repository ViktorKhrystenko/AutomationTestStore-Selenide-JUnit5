package exceptions;

public class WrongProductPriceCalculationException extends RuntimeException {
    public WrongProductPriceCalculationException(String productName, double unitPrice, long productQuantity,
                                                 double actualProductPrice, double providedProductPrice) {
        super(String.format("Total price calculation for %s product is wrong. " +
                "With %f unit price and %d quantity, actual total price is %f. " +
                "Provided total price: %f",
                productName,
                unitPrice, productQuantity, actualProductPrice,
                providedProductPrice));
    }
}
