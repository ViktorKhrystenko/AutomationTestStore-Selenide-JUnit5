package pageobjects.components.products.table.item;

import com.codeborne.selenide.SelenideElement;
import exceptions.WrongProductPriceCalculationException;
import lombok.Getter;
import pageobjects.BasePage;

import static utils.FloatNumberRounder.round;

@Getter
public abstract class Product extends BasePage {
    protected String productName;
    protected double unitPrice;
    protected double totalPrice;
    protected long quantity;


    public Product(SelenideElement tableRow) {
        parseTableRowIntoFields(tableRow);
    }


    public void setQuantity(long quantity) {
        this.quantity = quantity;
        this.totalPrice = round(unitPrice * quantity, 2);
    }


    protected void checkTotalPrice(double totalPrice) throws WrongProductPriceCalculationException {
        if (this.totalPrice != totalPrice) {
            throw new WrongProductPriceCalculationException(
                    this.productName, this.unitPrice, this.quantity, this.totalPrice, totalPrice);
        }
    }


    protected abstract void parseTableRowIntoFields(SelenideElement tableRow);
}
