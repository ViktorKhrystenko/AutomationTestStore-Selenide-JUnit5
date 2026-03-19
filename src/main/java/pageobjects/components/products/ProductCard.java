package pageobjects.components.products;

import exceptions.PageNavigationException;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.BasePage;
import pageobjects.products.ProductPage;

import java.util.Optional;

import static constants.url.BaseUrlRegexPatterns.PRODUCT_PAGE_REGEX_PATTERN;
import static utils.StringFormatHelper.leftOnlyCharactersInRange;
import static utils.StringFormatHelper.replaceByRegex;

public class ProductCard extends BasePage {
    private static final String PRODUCT_LINK_HREF_PATTERN = PRODUCT_PAGE_REGEX_PATTERN
            .substring(0, PRODUCT_PAGE_REGEX_PATTERN.lastIndexOf("\\d+"));

    private static final By PRODUCT_TITLE_LOCATOR = By.className("prdocutname");

    private WebElement productCardElement;

    private WebElement productTitleElement;


    public ProductCard(WebDriver driver, WebElement productCardElement) {
        super(driver);
        this.productCardElement = productCardElement;
        productTitleElement = productCardElement.findElement(PRODUCT_TITLE_LOCATOR);
    }


    public ProductPage clickOnProductTitle() throws PageNavigationException {
        String productName = getProductName();
        return Allure.step("Click on \"" + productName + "\" product title",
                () -> {
                    String productTitleHref = productTitleElement.getDomProperty("href");
                    int productId = Integer.parseInt(replaceByRegex(
                            productTitleHref,
                            PRODUCT_LINK_HREF_PATTERN,
                            ""));
                    productTitleElement.click();
                    waitUntilPageIsLoaded();
                    Optional<Integer> path = getPathFromHref(productTitleHref);
                    if (path.isEmpty()) {
                        return new ProductPage(driver, productId, productName);
                    }
                    else {
                        return new ProductPage(driver, path.get(), productId, productName);
                    }
                });
    }

    public String getProductName() {
        return productTitleElement.getText();
    }


    private Optional<Integer> getPathFromHref(String href) {
        if (!href.contains("&path=")) {
            return Optional.empty();
        }
        String pathParameter = href.substring(
                href.indexOf("path="),
                href.length() - 1);
        pathParameter = pathParameter.substring(
                0,
                pathParameter.indexOf('&'));
        int path = Integer.parseInt(leftOnlyCharactersInRange(pathParameter, '0', '9'));
        return Optional.of(path);
    }
}
