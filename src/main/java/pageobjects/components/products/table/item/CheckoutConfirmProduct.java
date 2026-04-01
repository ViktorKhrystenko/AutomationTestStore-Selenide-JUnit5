package pageobjects.components.products.table.item;

import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.List;

import static utils.StringFormatHelper.parsePriceStringToDouble;

public class CheckoutConfirmProduct extends Product {

    public CheckoutConfirmProduct(SelenideElement tableRow) {
        super(tableRow);
    }


    @Override
    protected void parseTableRowIntoFields(SelenideElement tableRow) {
        List<String> productFields = Arrays.stream(tableRow.getDomProperty("innerText")
                .split("\t")).toList();
        // sublist to remove first image column
        productFields = productFields.subList(1, productFields.size());
        productName = productFields.get(0);
        unitPrice = parsePriceStringToDouble(productFields.get(1));
        setQuantity(Integer.parseInt(productFields.get(2)));
        checkTotalPrice(parsePriceStringToDouble(productFields.get(3)));
    }
}
