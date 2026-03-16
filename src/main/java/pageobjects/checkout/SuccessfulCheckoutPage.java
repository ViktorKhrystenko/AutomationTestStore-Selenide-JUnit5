package pageobjects.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderPage;

import java.util.regex.Pattern;

import static constants.BaseUrls.SUCCESSFUL_CHECKOUT_BASE_URL;

public class SuccessfulCheckoutPage extends BasePage {
    private static final String BASE_URL = SUCCESSFUL_CHECKOUT_BASE_URL;
    private static final String PAGE_NAME = "Successful checkout page";


    @FindBy(xpath = "//p[contains(text(), 'Your order')]")
    private WebElement yourOrderElement;

    @FindBy(xpath = "//a[contains(text(), 'invoice page')]")
    private WebElement toInvoicePageLink;


    public SuccessfulCheckoutPage(WebDriver driver) {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public String getOrderId() {
        String yourOrderString = yourOrderElement.getText();
        return yourOrderString.substring(yourOrderString.indexOf('#') + 1).substring(0, ' ');
    }

    public OrderPage clickOnToInvoicePageLink() {
        int orderId = Integer.parseInt(getOrderId());
        toInvoicePageLink.click();
        waitUntilPageIsLoaded();
        return new OrderPage(driver, orderId);
    }
}
