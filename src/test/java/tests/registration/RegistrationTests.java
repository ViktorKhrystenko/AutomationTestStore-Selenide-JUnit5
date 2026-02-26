package tests.registration;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.navigation.NavigationBar;
import tests.BaseTest;

import static constants.BaseUrls.REGISTRATION_BASE_URL;
import static constants.BaseUrls.HOME_BASE_URL;

import static org.assertj.core.api.Assertions.*;

public class RegistrationTests extends BaseTest {
    private NavigationBar navigation;


    @BeforeMethod
    @Override
    public void setup(ITestResult test) {
        super.setup(test);
        driver.get(REGISTRATION_BASE_URL);
        navigation = new NavigationBar(driver);
    }


    @Test
    public void entranceTest() {
        driver.get(HOME_BASE_URL);

        navigation.clickOnLoginOrRegisterLink()
                .clickOnToRegistrationPageButton();

        assertThat(driver.getCurrentUrl())
                .isEqualTo(REGISTRATION_BASE_URL);
    }
}
