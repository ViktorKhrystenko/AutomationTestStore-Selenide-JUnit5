package tests.checkout;

import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.account.AccountPage;
import pageobjects.account.history.OrderHistoryPage;
import pageobjects.account.history.OrderPage;
import pageobjects.checkout.CartPage;
import pageobjects.checkout.CheckoutConfirmPage;
import pageobjects.components.navigation.NavigationBar;
import pageobjects.components.products.table.ProductTable;
import pageobjects.components.products.table.item.CartProduct;
import pageobjects.components.products.table.item.CheckoutConfirmProduct;
import pageobjects.components.products.table.item.Product;
import pageobjects.login.LoginPage;
import pageobjects.login.logout.LogoutPage;
import pageobjects.products.ProductPage;
import tests.BaseTest;
import utils.datagenerator.DataGenerator;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Properties;

import static utils.PropertiesLoader.loadProperties;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import static constants.ResourcesPaths.CHECKOUT_TEST_DATA_PATH;

import static constants.url.BaseUrls.ACCOUNT_BASE_URL;
import static constants.url.BaseUrls.LOGIN_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

public class CheckoutTests extends BaseTest {
    private static final String USER_TEST_DATA_FILE_PATH = CHECKOUT_TEST_DATA_PATH + "user.properties";
    private static final String PRODUCTS_TEST_DATA_FILE_PATH = CHECKOUT_TEST_DATA_PATH + "products.properties";

    private static User user;
    private static String shippingCountry;
    private static String shippingState;

    private static String defaultProductName;
    private static String quantityLimitedProductName;
    private static String outOfStockProductName;

    private AccountPage accountPage;
    private NavigationBar navigation;

    private ZoneId timeZone;


    @Step("Login")
    private AccountPage login() {
        return new LoginPage(driver)
                .fillLoginNameField(user.getLoginName())
                .fillPasswordField(user.getPassword())
                .clickOnLoginButton();
    }

    @Step("Logout")
    private LogoutPage logout() {
        return navigation.clickOnAccountPageLink()
                .clickOnLogoffLink();
    }

    @Step("Clear cart")
    private AccountPage clearCart() {
        navigation.clickOnCartPageLink()
                .clearCart();
        return navigation.clickOnAccountPageLink();
    }

    @Step("Check order page")
    private void checkOrderPage(OrderPage orderPage,
                                ProductTable<? extends Product> checkoutProducts,
                                Temporal actualOrderDate) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(
                orderPage.checkUserInfoOnOrderPage(user, shippingState, shippingCountry)).isTrue();
        softAssertions.assertThat(
                orderPage.checkOrderDate(actualOrderDate, 5)).isTrue();
        softAssertions.assertThat(
                orderPage.areAllProductsDisplayedOnPage(checkoutProducts.getProductNames())).isTrue();
        softAssertions.assertThat(
                orderPage.getProductTable().isTotalPriceCalculatedCorrectly(checkoutProducts)).isTrue();
        softAssertions.assertAll();
    }


    @BeforeClass(groups = {"lifecycle"})
    public static void prepareTetsData() {
        Properties userTestData = loadProperties(USER_TEST_DATA_FILE_PATH);
        Properties productsTestData = loadProperties(PRODUCTS_TEST_DATA_FILE_PATH);
        user = new DataGenerator().generateUser();
        user.setFirstName(userTestData.getProperty("first-name"));
        user.setLastName(userTestData.getProperty("last-name"));
        user.setEmail(userTestData.getProperty("email"));
        user.setAddress_1(userTestData.getProperty("address"));
        user.setCity(userTestData.getProperty("city"));
        user.setZipCode(userTestData.getProperty("zip-code"));
        user.setLoginName(userTestData.getProperty("login-name"));
        user.setPassword(userTestData.getProperty("password"));

        shippingCountry = userTestData.getProperty("country");
        shippingState = userTestData.getProperty("state");

        defaultProductName = productsTestData.getProperty("default-product");
        quantityLimitedProductName = productsTestData.getProperty("quantity-limited-product");
        outOfStockProductName = productsTestData.getProperty("out-of-stock-product");
    }

    @Override
    @BeforeMethod(groups = {"lifecycle"})
    public void setup(ITestResult testResult) {
        super.setup(testResult);
        timeZone = ZoneId.of((String) ((JavascriptExecutor) driver).executeScript(
                "return Intl.DateTimeFormat().resolvedOptions().timeZone;"));
        driver.get(ACCOUNT_BASE_URL);
        navigation = new NavigationBar(driver);
        login();
        accountPage = clearCart();
    }


    @Test(description = "3.1.1.1. Test case - Entrance test",
            groups = {
                    "checkout",
                    "smoke"
            })
    public void entranceTest() {
        assertThatCode(() -> {
            accountPage.clickOnOrderHistoryIcon();
            navigation.clickOnHomePageLink()
                    .getProductCard(defaultProductName)
                    .clickOnProductTitle()
                    .clickOnAddToCartButton()
                    .clickOnCheckoutButton();
        }).doesNotThrowAnyException();
    }

    @Test(description = "3.1.1.2. Test case - Check order of one product in stock",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyCheckoutOrderingOneInStockProduct() {
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton();
        ProductTable<CartProduct> productsInCart = cartPage.getProductTable();

        assertTrue(cartPage.areAllProductsDisplayedOnPage(defaultProductName));
        assertTrue(cartPage.areEstimateShippingAndTaxesFieldsAreFilledCorrectly(shippingCountry,
                shippingState, user.getZipCode()));

        CheckoutConfirmPage checkoutConfirmPage = cartPage.clickOnCheckoutButton();
        ProductTable<CheckoutConfirmProduct> checkoutProducts = checkoutConfirmPage.getProductTable();

        assertTrue(checkoutConfirmPage.areAllProductsDisplayedOnPage(productsInCart.getProductNames()));
        assertTrue(checkoutProducts.isTotalPriceCalculatedCorrectly(productsInCart));

        ZonedDateTime actualOrderDate = ZonedDateTime.now(timeZone);
        assertThatCode(() -> checkoutConfirmPage.clickOnConfirmOrderButton())
                .doesNotThrowAnyException();

        OrderPage orderPage = navigation.hoverCursorOverAccountLink()
                .clickOnOrderHistoryLink()
                .getToOrderDetailsOfMostResentOrder();

        checkOrderPage(orderPage, checkoutProducts, actualOrderDate);
    }

    @Test(description = "3.1.1.3. Test case - Check order of more than one product in stock",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyCheckoutOrderingMultipleInStockProducts() {
        navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton();
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(quantityLimitedProductName)
                .clickOnProductTitle()
                .setQuantity(2)
                .clickOnAddToCartButton();
        ProductTable<CartProduct> productsInCart = cartPage.getProductTable();

        assertTrue(cartPage.areAllProductsDisplayedOnPage(defaultProductName));
        assertTrue(cartPage.areEstimateShippingAndTaxesFieldsAreFilledCorrectly(shippingCountry,
                shippingState, user.getZipCode()));

        CheckoutConfirmPage checkoutConfirmPage = cartPage.clickOnCheckoutButton();
        ProductTable<CheckoutConfirmProduct> checkoutProducts = checkoutConfirmPage.getProductTable();

        assertTrue(checkoutConfirmPage.areAllProductsDisplayedOnPage(productsInCart.getProductNames()));
        assertTrue(checkoutProducts.isTotalPriceCalculatedCorrectly(productsInCart));

        ZonedDateTime actualOrderDate = ZonedDateTime.now(timeZone);
        assertThatCode(() -> checkoutConfirmPage.clickOnConfirmOrderButton())
                .doesNotThrowAnyException();

        OrderHistoryPage orderHistoryPage = navigation.hoverCursorOverAccountLink()
                .clickOnOrderHistoryLink();

        assertEquals(orderHistoryPage.getOrderProductQuantityOfMostResentOrder(), 2);

        OrderPage orderPage = orderHistoryPage.getToOrderDetailsOfMostResentOrder();

        checkOrderPage(orderPage, checkoutProducts, actualOrderDate);
    }

    @Test(description = "3.1.2.1. Test case - Check if \"State\" dropdown drops its value after changing \"Country\" value",
            groups = {
                    "checkout",
                    "smoke"
            })
    public void verifyThatStateDropdownValueDropsAfterCountryValueChangedOnCartPage() {
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton();

        cartPage.selectRandomCountry();

        assertEquals(cartPage.getSelectedState(), DESELECTED_OPTION);
    }

    @Test(description = "3.2.1.1. Test case - Check adding zero products",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyAddingProductToCartWithZeroQuantity() {
        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .setQuantity(0);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());
    }

    @Test(description = "3.2.1.2. Test case - Check adding more products than in stock",
            groups = {
                    "checkout",
                    "regression"
            })
    public void verifyAddingMoreProductsToCartThanInStock() {
        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(quantityLimitedProductName)
                .clickOnProductTitle();

        long inStockQuantity = productPage.getInStockQuantity();

        CartPage cartPage = productPage.setQuantity(inStockQuantity + 1)
                .clickOnAddToCartButton();

        assertTrue(cartPage.getProductTable()
                .getProductNames()[0].contains("***"));
    }

    @Test(description = "3.2.1.3. Test case - Check adding out of stock products",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyAddingOutOfStockProductToCart() {
        ProductPage productPage = navigation.clickOnApparelAndAccessoriesCategoryPageLink()
                .getProductCard(outOfStockProductName)
                .clickOnProductTitle();

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());

        assertThatCode(() -> productPage.clickOnOutOfStockButton())
                .doesNotThrowAnyException();
    }

    @Test(description = "3.2.2.1. Test case - Check preceding to checkout after setting quantity to zero",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyPrecedingToCheckoutAfterSettingProductQuantityToZero() {
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton();

        cartPage.getProductTable().getProduct(defaultProductName).setQuantity(0);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> cartPage.clickOnCheckoutButton());
        assertThat(cartPage.getProductTable().getProductNames())
                .isEmpty();
    }

    @Test(description = "3.2.2.2. Test case - Check preceding to checkout after setting quantity to more than in stock",
            groups = {
                    "checkout",
                    "regression"
            })
    public void verifyPrecedingToCheckoutAfterSettingProductQuantityToMoreThanInStock() {
        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(quantityLimitedProductName)
                .clickOnProductTitle();

        long inStockQuantity = productPage.getInStockQuantity();

        CartPage cartPage = productPage.clickOnAddToCartButton();

        cartPage.getProductTable().getProduct(quantityLimitedProductName)
                .setQuantity(inStockQuantity + 1);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> cartPage.clickOnCheckoutButton());

        assertTrue(cartPage.getProductTable()
                .getProductNames()[0].contains("***"));
    }

    @Test(description = "3.2.2.3. Test case - Check preceding to checkout with unselected \"State\"",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyPrecedingToCheckoutWithUnselectedState() {
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton()
                .selectRandomCountry();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> cartPage.clickOnCheckoutButton());

        assertEquals(cartPage.getSelectedState(), DESELECTED_OPTION);
    }

    @Test(description = "3.2.3.1. Test case - Check adding product to cart being logged out",
            groups = {
                    "checkout",
                    "critical-path"
            })
    public void verifyAddingProductToCartBeingLoggedOut() {
        logout();

        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());
        assertEquals(driver.getCurrentUrl(), LOGIN_BASE_URL);
    }
}
