package pageobjects.components.navigation;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.account.history.OrderHistoryPage;

public class AccountDropdown extends BasePage {
    @FindBy(css = "#customernav ul.sub_menu a[href*=\"account/history\"]")
    private WebElement orderHistoryLink;


    public AccountDropdown(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @Step("Click on \"Order history\" link")
    public OrderHistoryPage clickOnOrderHistoryLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(orderHistoryLink);
        return new OrderHistoryPage(driver);
    }
}