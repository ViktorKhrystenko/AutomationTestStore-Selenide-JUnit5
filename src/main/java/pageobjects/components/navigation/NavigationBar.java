package pageobjects.components.navigation;

import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.BasePage;
import pageobjects.account.AccountPage;
import pageobjects.checkout.CartPage;
import pageobjects.login.LoginPage;
import pageobjects.products.categories.ApparelAndAccessoriesCategoryPage;
import pageobjects.products.home.HomePage;

public class NavigationBar extends BasePage {
    @FindBy(xpath = "//*[@id='customernav']//a[contains(@href, 'account/login')]")
    private WebElement loginOrRegisterLink;

    @FindBy(xpath = "//*[@id='customernav']//*[contains(text(), 'Welcome back')]/parent::a")
    private WebElement accountPageLink;

    @FindBy(css = "#customernav ul.sub_menu")
    private WebElement accountDropdownElement;

    @FindBy(css = "a.menu_home")
    private WebElement homePageLink;

    @FindBy(css = "ul[class~=\"topcart\"] a[href*=\"checkout/cart\"]")
    private WebElement cartPageLink;

    @FindBy(xpath = "//a[text()='  Apparel & accessories']")
    private WebElement apparelAndAccessoriesCategoryLink;


    public NavigationBar(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @Step("Click on 'Login or register' link")
    public LoginPage clickOnLoginOrRegisterLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(loginOrRegisterLink);
        return new LoginPage(driver);
    }

    @Step("Click on 'Account page' link")
    public AccountPage clickOnAccountPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(accountPageLink);
        return new AccountPage(driver);
    }

    @Step("Click on 'Home page' link")
    public HomePage clickOnHomePageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(homePageLink);
        return new HomePage(driver);
    }

    @Step("Click on 'Cart page' link")
    public CartPage clickOnCartPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(cartPageLink);
        return new CartPage(driver);
    }

    @Step("Click on 'Apparel & Accessories' category link")
    public ApparelAndAccessoriesCategoryPage clickOnApparelAndAccessoriesCategoryPageLink() throws PageNavigationException {
        clickOnElementAndWaitPageLoad(apparelAndAccessoriesCategoryLink);
        return new ApparelAndAccessoriesCategoryPage(driver);
    }

    public AccountDropdown hoverCursorOverAccountLink() {
        new Actions(driver).moveToElement(accountPageLink).perform();
        wait.until(ExpectedConditions.visibilityOf(accountDropdownElement));
        return new AccountDropdown(driver);
    }
}
