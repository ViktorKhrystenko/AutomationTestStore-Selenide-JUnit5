package pageobjects.components.products.table.item;

import exceptions.WrongProductPriceCalculationException;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.BasePage;

import static utils.FloatNumberRounder.round;

@Getter
public abstract class Product extends BasePage {
    protected String productName;
    protected double unitPrice;
    protected double totalPrice;
    protected long quantity;


    public Product(WebDriver driver, WebElement tableRow) {
        super(driver);
        parseTableRowIntoFields(tableRow);
    }


    public void setQuantity(long quantity) {
        this.quantity = quantity;
        this.totalPrice = round(unitPrice * quantity, 2);
    }


    protected void checkTotalPrice(double totalPrice) {
        if (this.totalPrice != totalPrice) {
            throw new WrongProductPriceCalculationException(
                    this.productName, this.unitPrice, this.quantity, this.totalPrice, totalPrice);
        }
    }


    protected abstract void parseTableRowIntoFields(WebElement tableRow);
}
