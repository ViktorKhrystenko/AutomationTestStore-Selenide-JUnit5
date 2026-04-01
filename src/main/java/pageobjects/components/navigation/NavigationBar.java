package pageobjects.components.navigation;

import com.codeborne.selenide.SelenideElement;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import pageobjects.BasePage;
import pageobjects.account.AccountPage;
import pageobjects.checkout.CartPage;
import pageobjects.login.LoginPage;
import pageobjects.products.categories.ApparelAndAccessoriesCategoryPage;
import pageobjects.products.home.HomePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class NavigationBar extends BasePage {
    private SelenideElement loginOrRegisterLink = $x("//*[@id='customernav']//a[contains(@href, 'account/login')]");

    private SelenideElement accountPageLink = $x("//*[@id='customernav']//*[contains(text(), 'Welcome back')]/parent::a");

    private SelenideElement accountDropdownElement = $("#customernav ul.sub_menu");

    private SelenideElement homePageLink = $("a.menu_home");

    private SelenideElement cartPageLink = $("ul[class~=\"topcart\"] a[href*=\"checkout/cart\"]");

    private SelenideElement apparelAndAccessoriesCategoryLink = $x("//a[text()='  Apparel & accessories']");


    @Step("Click on 'Login or register' link")
    public LoginPage clickOnLoginOrRegisterLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(loginOrRegisterLink);
        return new LoginPage();
    }

    @Step("Click on 'Account page' link")
    public AccountPage clickOnAccountPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(accountPageLink);
        return new AccountPage();
    }

    @Step("Click on 'Home page' link")
    public HomePage clickOnHomePageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(homePageLink);
        return new HomePage();
    }

    @Step("Click on 'Cart page' link")
    public CartPage clickOnCartPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(cartPageLink);
        return new CartPage();
    }

    @Step("Click on 'Apparel & Accessories' category link")
    public ApparelAndAccessoriesCategoryPage clickOnApparelAndAccessoriesCategoryPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(apparelAndAccessoriesCategoryLink);
        return new ApparelAndAccessoriesCategoryPage();
    }

    public AccountDropdown hoverCursorOverAccountLink() {
        hoverCursorOverElement(accountPageLink);
        accountDropdownElement.shouldBe(visible);
        return new AccountDropdown();
    }
}
