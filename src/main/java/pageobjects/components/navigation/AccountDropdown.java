package pageobjects.components.navigation;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.account.history.OrderHistoryPage;

import static com.codeborne.selenide.Selenide.$;

public class AccountDropdown extends BasePage {
    private SelenideElement orderHistoryLink = $("#customernav ul.sub_menu a[href*=\"account/history\"]");


    @Step("Click on \"Order history\" link")
    public OrderHistoryPage clickOnOrderHistoryLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(orderHistoryLink);
        return new OrderHistoryPage();
    }
}