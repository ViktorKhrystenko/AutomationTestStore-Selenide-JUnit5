package pageobjects.checkout;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderPage;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.SUCCESSFUL_CHECKOUT_BASE_URL;

public class SuccessfulCheckoutPage extends BasePage {
    private static final String BASE_URL = SUCCESSFUL_CHECKOUT_BASE_URL;
    private static final String PAGE_NAME = "Successful checkout page";


    @FindBy(xpath = "//p[contains(text(), 'Your order')]")
    private WebElement yourOrderElement;

    @FindBy(xpath = "//a[contains(text(), 'invoice page')]")
    private WebElement toInvoicePageLink;


    public SuccessfulCheckoutPage(WebDriver driver) throws PageNavigationException {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public String getOrderId() {
        String yourOrderString = yourOrderElement.getText();
        return yourOrderString.substring(yourOrderString.indexOf('#') + 1).substring(0, ' ');
    }

    @Step("Click on \"To invoice page\" link")
    public OrderPage clickOnToInvoicePageLink() throws PageNavigationException {
        int orderId = Integer.parseInt(getOrderId());
        clickOnElementAndWaitPageLoad(toInvoicePageLink);
        return new OrderPage(driver, orderId);
    }
}
