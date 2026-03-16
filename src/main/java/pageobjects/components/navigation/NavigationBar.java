package pageobjects.components.navigation;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;
import pageobjects.login.LoginPage;
import pageobjects.products.categories.ApparelAndAccessoriesCategoryPage;
import pageobjects.products.home.HomePage;

public class NavigationBar extends BasePage {
    @FindBy(xpath = "//*[@id='customernav']//a[contains(@href, 'account/login')]")
    private WebElement loginOrRegisterLink;

    @FindBy(css = "a.menu_home")
    private WebElement homePageLink;

    @FindBy(xpath = "//a[text()='  Apparel & accessories']")
    private WebElement apparelAndAccessoriesCategoryLink;


    public NavigationBar(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @Step("Click on 'Login or register' link")
    public LoginPage clickOnLoginOrRegisterLink() {
        loginOrRegisterLink.click();
        waitUntilPageIsLoaded();
        return new LoginPage(driver);
    }

    public HomePage clickOnHomePageLink() {
        homePageLink.click();
        waitUntilPageIsLoaded();
        return new HomePage(driver);
    }

    public ApparelAndAccessoriesCategoryPage clickOnApparelAndAccessoriesCategoryPageLink() {
        apparelAndAccessoriesCategoryLink.click();
        waitUntilPageIsLoaded();
        return new ApparelAndAccessoriesCategoryPage(driver);
    }
}
