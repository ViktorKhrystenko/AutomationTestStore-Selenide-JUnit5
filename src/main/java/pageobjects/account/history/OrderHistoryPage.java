package pageobjects.account.history;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static constants.url.BaseUrls.ORDER_HISTORY_BASE_URL;

import static utils.StringFormatHelper.getTextAfterColon;
import static utils.StringFormatHelper.parsePriceStringToDouble;

public class OrderHistoryPage extends BasePage {
    private static final String BASE_URL = ORDER_HISTORY_BASE_URL;
    private static final String PAGE_NAME = "Order history page";

    @FindBy(xpath = "//div[@class='container-fluid mt20']")
    private List<WebElement> orderElementsList;

    private final List<Order> orderList;


    public OrderHistoryPage(WebDriver driver) {
        super(driver);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
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

    public OrderPage getToOrderDetails(int orderIndex) {
        int orderId = getOrderId(orderIndex);
        getViewDetailsButton(orderIndex).click();
        waitUntilPageIsLoaded();
        return new OrderPage(driver, orderId);
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

    public OrderPage getToOrderDetailsOfMostResentOrder() {
        int orderId = getOrderIdOfMostResentOrder();
        getViewDetailsButton(0).click();
        waitUntilPageIsLoaded();
        return new OrderPage(driver, orderId);
    }


    private void initOrderList() {
        orderList.clear();
        for (WebElement orderElement: orderElementsList) {
            orderList.add(new Order(orderElement));
        }
    }


    private WebElement getViewDetailsButton(int orderIndex) {
        return orderList.get(orderIndex).getViewDetailsButton();
    }


    static class Order {
        private static final By ORDER_ID_LOCATOR = By.xpath("//div/b[contains(text(), 'Order ID')]");
        private static final By STATUS_LOCATOR = By.xpath("//div/b[contains(text(), 'Status')]");
        private static final By PRODUCTS_QUANTITY_LOCATOR = By.xpath("//table//td[contains(text(), 'Products')]");
        private static final By TOTAL_LOCATOR = By.xpath("//table//td[contains(text(), 'Total')]");

        @Getter
        private int orderId;
        @Getter
        private String status;
        @Getter
        private int productsQuantity;
        @Getter
        private double total;
        @Getter
        private WebElement viewDetailsButton;


        public Order(WebElement orderElement) {
            orderId = Integer.parseInt(getTextAfterColon(orderElement
                    .findElement(ORDER_ID_LOCATOR)
                    .findElement(PARENT_ELEMENT_LOCATOR).getText())
                    // substring to remove first '#' symbol
                    .substring(1));
            status = getTextAfterColon(orderElement
                    .findElement(STATUS_LOCATOR)
                    .findElement(PARENT_ELEMENT_LOCATOR).getText());
            productsQuantity = Integer.parseInt(
                    getTextAfterColon(orderElement.findElement(PRODUCTS_QUANTITY_LOCATOR).getText()));
            total = parsePriceStringToDouble(
                    getTextAfterColon(orderElement.findElement(TOTAL_LOCATOR).getText()));
            viewDetailsButton = orderElement.findElement(By.id("button_edit"));
        }
    }
}
