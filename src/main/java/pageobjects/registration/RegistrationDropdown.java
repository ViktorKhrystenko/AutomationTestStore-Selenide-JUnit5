package pageobjects.registration;

import org.openqa.selenium.By;

public enum RegistrationDropdown implements RegistrationInput {
    REGION_STATE_FIELD(By.id("AccountFrm_zone_id")),
    COUNTRY_FIELD(By.id("AccountFrm_country_id"));


    private final By locator;


    RegistrationDropdown(By locator) {
        this.locator = locator;
    }


    @Override
    public By getLocator() {
        return locator;
    }
}
