package pageobjects.components.products.table.item;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static utils.StringFormatHelper.parsePriceStringToDouble;

public class OrderPageProduct extends Product {

    @Getter
    private String modelNumber;


    public OrderPageProduct(SelenideElement tableRow) {
        super(tableRow);
    }


    @Override
    protected void parseTableRowIntoFields(SelenideElement tableRow) {
        List<String> productFields = Arrays.stream(tableRow.getDomProperty("innerText")
                .split("\t")).toList();
        // sublist to remove first image column
        productFields = productFields.subList(1, productFields.size());
        productName = productFields.get(0).replace("\n", "");
        modelNumber = productFields.get(1);
        unitPrice = parsePriceStringToDouble(productFields.get(3));
        setQuantity(Integer.parseInt(productFields.get(2)));
        checkTotalPrice(parsePriceStringToDouble(productFields.get(4)));
    }
}
