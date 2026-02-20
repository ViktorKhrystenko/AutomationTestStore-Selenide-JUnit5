package pageobjects.registration;

import org.openqa.selenium.By;

public enum RegistrationField implements RegistrationInput {
    FIRST_NAME_FIELD(By.id("AccountFrm_firstname")),
    LAST_NAME_FIELD(By.id("AccountFrm_lastname")),
    EMAIL_FIELD(By.id("AccountFrm_email")),
    TELEPHONE_FIELD(By.id("AccountFrm_telephone")),
    FAX_FIELD(By.id("AccountFrm_fax")),
    COMPANY_FIELD(By.id("AccountFrm_company")),
    ADDRESS_1_FIELD(By.id("AccountFrm_address_1")),
    ADDRESS_2_FIELD(By.id("AccountFrm_address_2")),
    CITY_FIELD(By.id("AccountFrm_city")),
    ZIP_CODE_FIELD(By.id("AccountFrm_postcode")),
    LOGIN_NAME_FIELD(By.id("AccountFrm_loginname")),
    PASSWORD_FIELD(By.id("AccountFrm_password")),
    PASSWORD_CONFIRM_FIELD(By.id("AccountFrm_confirm"));


    private final By locator;


    RegistrationField(By locator) {
        this.locator = locator;
    }


    @Override
    public By getLocator() {
        return locator;
    }
}
