package pageobjects.components.navigation;

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


    public OrderHistoryPage clickOnOrderHistoryLink() {
        orderHistoryLink.click();
        waitUntilPageIsLoaded();
        return new OrderHistoryPage(driver);
    }
}