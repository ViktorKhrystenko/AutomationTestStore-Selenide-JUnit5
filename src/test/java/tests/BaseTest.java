package tests;

import com.codeborne.selenide.Selenide;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.driver.DriverConfigurator;

@ExtendWith(InstancioExtension.class)
public abstract class BaseTest {
    @BeforeEach
    public void setup() {
        DriverConfigurator.configureDriver();
    }

    @AfterEach
    public void teardown() {
        Selenide.closeWebDriver();
    }
}
