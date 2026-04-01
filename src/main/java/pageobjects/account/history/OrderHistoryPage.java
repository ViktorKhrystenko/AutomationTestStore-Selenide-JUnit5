package pageobjects.account.history;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import pageobjects.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$$x;
import static constants.url.BaseUrls.ORDER_HISTORY_BASE_URL;

import static utils.StringFormatHelper.getTextAfterColon;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class OrderHistoryPage extends BasePage {
    private static final String BASE_URL = ORDER_HISTORY_BASE_URL;
    private static final String PAGE_NAME = "Order history page";

    private ElementsCollection orderElementsList = $$x("//div[@class='container-fluid mt20']");

    private final List<Order> orderList;


    public OrderHistoryPage() throws PageNavigationException {
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        orderList = new ArrayList<>();
        initOrderList();
    }


    public int getOrderListLength() {
        return orderList.size();
    }


    public int getOrderId(int orderIndex) {
        return orderList.get(orderIndex).getOrderId();
    }

    public String getOrderStatus(int orderIndex) {
        return orderList.get(orderIndex).getStatus();
    }

    public int getOrderProductQuantity(int orderIndex) {
        return orderList.get(orderIndex).getProductsQuantity();
    }

    public double getOrderTotal(int orderIndex) {
        return orderList.get(orderIndex).getTotal();
    }

    @Step("Click on \"View\" button of {0} order")
    public OrderPage getToOrderDetails(int orderIndex) throws PageNavigationException {
        int orderId = getOrderId(orderIndex);
        clickOnElementAndWaitPageLoad(
                getViewDetailsButton(orderIndex));
        return new OrderPage(orderId);
    }


    public int getOrderIdOfMostResentOrder() {
        return orderList.get(0).getOrderId();
    }

    public String getOrderStatusOfMostResentOrder() {
        return orderList.get(0).getStatus();
    }

    public int getOrderProductQuantityOfMostResentOrder() {
        return orderList.get(0).getProductsQuantity();
    }

    public double getOrderTotalOfMostResentOrder() {
        return orderList.get(0).getTotal();
    }

    @Step("Click on \"View\" button of the most resent order")
    public OrderPage getToOrderDetailsOfMostResentOrder() throws PageNavigationException {
        int orderId = getOrderIdOfMostResentOrder();
        clickOnElementAndWaitPageLoad(
                getViewDetailsButton(0));
        return new OrderPage(orderId);
    }


    private void initOrderList() {
        orderList.clear();
        for (SelenideElement orderElement: orderElementsList) {
            orderList.add(new Order(orderElement));
        }
    }


    private SelenideElement getViewDetailsButton(int orderIndex) {
        return orderList.get(orderIndex).getViewDetailsButton();
    }


    static class Order {
        private static final By ORDER_ID_LOCATOR = By.xpath(".//div/b[contains(text(), 'Order ID')]");
        private static final By STATUS_LOCATOR = By.xpath(".//div/b[contains(text(), 'Status')]");
        private static final By PRODUCTS_QUANTITY_LOCATOR = By.xpath(".//table//td[contains(text(), 'Products')]");
        private static final By TOTAL_LOCATOR = By.xpath(".//table//td[contains(text(), 'Total')]");

        @Getter
        private int orderId;
        @Getter
        private String status;
        @Getter
        private int productsQuantity;
        @Getter
        private double total;
        @Getter
        private SelenideElement viewDetailsButton;


        public Order(SelenideElement orderElement) {
            orderId = Integer.parseInt(
                    getTextAfterColon(orderElement
                            .$(ORDER_ID_LOCATOR)
                            .parent().text())
                    // substring to remove first '#' symbol
                    .substring(1));
            status = getTextAfterColon(orderElement
                    .$(STATUS_LOCATOR)
                    .parent().text());
            productsQuantity = Integer.parseInt(
                    getTextAfterColon(orderElement.$(PRODUCTS_QUANTITY_LOCATOR).text()));
            total = parsePriceStringToDouble(
                    getTextAfterColon(orderElement.$(TOTAL_LOCATOR).text()));
            viewDetailsButton = orderElement.$("#button_edit");
        }
    }
}
