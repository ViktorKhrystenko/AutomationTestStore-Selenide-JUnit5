package pageobjects.checkout;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.account.history.OrderPage;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$x;
import static constants.url.BaseUrls.SUCCESSFUL_CHECKOUT_BASE_URL;

public class SuccessfulCheckoutPage extends BasePage {
    private static final String BASE_URL = SUCCESSFUL_CHECKOUT_BASE_URL;
    private static final String PAGE_NAME = "Successful checkout page";


    private SelenideElement yourOrderElement = $x("//p[contains(text(), 'Your order')]");

    private SelenideElement toInvoicePageLink = $x("//a[contains(text(), 'invoice page')]");


    public SuccessfulCheckoutPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
    }


    public String getOrderId() {
        String yourOrderString = yourOrderElement.text();
        return yourOrderString.substring(yourOrderString.indexOf('#') + 1).substring(0, ' ');
    }

    @Step("Click on \"To invoice page\" link")
    public OrderPage clickOnToInvoicePageLink() throws PageNavigationException {
        int orderId = Integer.parseInt(getOrderId());
        clickOnElementAndWaitPageLoad(toInvoicePageLink);
        return new OrderPage(orderId);
    }
}
