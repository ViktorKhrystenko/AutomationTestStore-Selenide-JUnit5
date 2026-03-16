package pageobjects.products;

import pageobjects.checkout.CartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BasePage;

import java.util.Optional;
import java.util.regex.Pattern;

import static constants.BaseUrls.PRODUCT_BASE_URL;

import static utils.StringFormatHelper.leftOnlyCharactersInRange;

public class ProductPage extends BasePage {
    private final String BASE_URL;
    private final String PAGE_NAME;

    @FindBy(xpath = "//span[text()='Availability:']/parent::li")
    private WebElement inStockElement;

    @FindBy(id = "product_quantity")
    private WebElement quantityField;

    @FindBy(xpath = "//*[@id='product_quantity']/parent::div/following-sibling::div[contains(text(), 'limit')]")
    private WebElement quantityPerOrderLimitElement;

    @FindBy(css = "a.cart")
    private WebElement addToCartButton;


    public ProductPage(WebDriver driver, int productId, String productName) {
        super(driver);
        BASE_URL = PRODUCT_BASE_URL + String.valueOf(productId);
        PAGE_NAME = String.format("%s product page", productName);
        checkLocation(Pattern.quote(BASE_URL), PAGE_NAME);
        PageFactory.initElements(driver, this);
    }


    public ProductPage setQuantity(long quantity) {
        quantityField.sendKeys(String.valueOf(quantity));
        return this;
    }

    public CartPage clickOnAddToCartButton() {
        addToCartButton.click();
        waitUntilPageIsLoaded();
        return new CartPage(driver);
    }


    public Optional<Long> getInStockQuantity() {
        if (!inStockElement.isDisplayed()) {
            return Optional.empty();
        }
        String inStockString = inStockElement.getText().split("\n")[1];
        if (inStockString.equals("In Stock")) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong(
                inStockString.substring(0, inStockString.indexOf("In Stock")).trim()));
    }

    public Optional<Long> getQuantityPerOrderLimit() {
        if (!quantityPerOrderLimitElement.isDisplayed()) {
            return Optional.empty();
        }
        String qualityPerOrderString = leftOnlyCharactersInRange(inStockElement.getText(), '0', '9');
        return Optional.of(Long.parseLong(qualityPerOrderString));
    }
}
