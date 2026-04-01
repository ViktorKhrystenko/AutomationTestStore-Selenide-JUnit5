package tests.checkout;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import dto.User;
import exceptions.PageNavigationException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static constants.FormValues.NONE_OPTION;
import static utils.PropertiesLoader.loadProperties;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static utils.datagenerator.DataGenerator.*;

import static constants.ResourcesPaths.CHECKOUT_TEST_DATA_PATH;

import static constants.url.BaseUrls.ACCOUNT_BASE_URL;
import static constants.url.BaseUrls.LOGIN_BASE_URL;

import static constants.FormValues.DESELECTED_OPTION;

@Epic("Checkout")
@Tag("checkout")
@DisplayName("Checkout tests")
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
        return new LoginPage()
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


    @BeforeAll
    public static void prepareTetsData() {
        Properties userTestData = loadProperties(USER_TEST_DATA_FILE_PATH);
        Properties productsTestData = loadProperties(PRODUCTS_TEST_DATA_FILE_PATH);
        user = generateUser();
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

    @BeforeEach
    public void setupCheckout() {
        open(ACCOUNT_BASE_URL);
        timeZone = ZoneId.of((String) Selenide.executeJavaScript(
                "return Intl.DateTimeFormat().resolvedOptions().timeZone;"));
        navigation = new NavigationBar();
        login();
        accountPage = clearCart();
    }


    @Test
    @Tag("smoke")
    @DisplayName("3.1.1.1. Test case - Entrance test")
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

    @Test
    @Tag("critical-path")
    @DisplayName("3.1.1.2. Test case - Check order of one product in stock")
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

    @Test
    @Tag("critical-path")
    @DisplayName("3.1.1.3. Test case - Check order of more than one product in stock")
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

    @Test
    @Feature("Cart")
    @Tag("smoke")
    @DisplayName("3.1.2.1. Test case - Check if \"State\" dropdown drops its value after changing \"Country\" value")
    public void verifyThatStateDropdownValueDropsAfterCountryValueChangedOnCartPage() {
        CartPage cartPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .clickOnAddToCartButton();

        cartPage.selectRandomCountry();

        assertTrue(cartPage.getSelectedState().equals(DESELECTED_OPTION) ||
                cartPage.getSelectedState().equals(NONE_OPTION));
    }

    @Test
    @Feature("Product page")
    @Tag("critical-path")
    @DisplayName("3.2.1.1. Test case - Check adding zero products")
    public void verifyAddingProductToCartWithZeroQuantity() {
        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle()
                .setQuantity(0);

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());
    }

    @Test
    @Feature("Product page")
    @Tag("regression")
    @DisplayName("3.2.1.2. Test case - Check adding more products than in stock")
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

    @Test
    @Feature("Product page")
    @Tag("critical-path")
    @DisplayName("3.2.1.3. Test case - Check adding out of stock products")
    public void verifyAddingOutOfStockProductToCart() {
        ProductPage productPage = navigation.clickOnApparelAndAccessoriesCategoryPageLink()
                .getProductCard(outOfStockProductName)
                .clickOnProductTitle();

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());

        assertThatCode(() -> productPage.clickOnOutOfStockButton())
                .doesNotThrowAnyException();
    }


    @Test
    @Feature("Cart page")
    @Tag("critical-path")
    @DisplayName("3.2.2.1. Test case - Check preceding to checkout after setting quantity to zero")
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

    @Test
    @Feature("Cart page")
    @Tag("regression")
    @DisplayName("3.2.2.2. Test case - Check preceding to checkout after setting quantity to more than in stock")
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

    @Test
    @Feature("Cart page")
    @Tag("critical-path")
    @DisplayName("3.2.2.3. Test case - Check preceding to checkout with unselected \"State\"")
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


    @Test
    @Feature("Logged out")
    @Tag("critical-path")
    @DisplayName("3.2.3.1. Test case - Check adding product to cart being logged out")
    public void verifyAddingProductToCartBeingLoggedOut() {
        logout();

        ProductPage productPage = navigation.clickOnHomePageLink()
                .getProductCard(defaultProductName)
                .clickOnProductTitle();

        assertThatExceptionOfType(PageNavigationException.class)
                .isThrownBy(() -> productPage.clickOnAddToCartButton());
        assertEquals(WebDriverRunner.url(), LOGIN_BASE_URL);
    }
}
