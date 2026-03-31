package utils.driver;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

import static config.ConfigReader.readConfigProperty;

public class DriverConfigurator {

    // TODO Config browsers for Selenide
    public static void configureDriver() {
        String runTarget = readConfigProperty("run.target", "local");
        String browser = readConfigProperty("browser", "chrome");
        switch (runTarget.toLowerCase()) {
            case "local":
                configureLocalDriver(browser);
                break;

            case "jenkins-docker-agent":
                configureJenkinsDockerAgentDriver(browser);
                break;

            default:
                throw new IllegalArgumentException("Unsupported \"run.target\" value: " + runTarget);
        }
    }


    private static void configureLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized")
                        .addArguments("--disable-notifications")
                        .addArguments("--incognito");
                Configuration.browser = browser;
                Configuration.browserCapabilities = options;
                break;
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-private");
                Configuration.browser = browser;
                Configuration.browserSize = "1920x1080";
                Configuration.browserCapabilities = options;
                break;
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized")
                        .addArguments("--disable-notifications")
                        .addArguments("--guest")
                        .addArguments("--inprivate");
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("translate_enabled", false);
                options.setExperimentalOption("prefs", preferences);
                Configuration.browser = browser;
                Configuration.browserCapabilities = options;
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported \"browser\" value: " + browser);
        }
    }

    private static void configureJenkinsDockerAgentDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox")
                        .addArguments("--disable-notifications")
                        .addArguments("--disable-gpu")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--remote-allow-origins=*");
                Configuration.browser = browser;
                Configuration.browserSize = "1920x1080";
                Configuration.headless = true;
                Configuration.browserCapabilities = options;
                break;
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-private");
                Configuration.browser = browser;
                Configuration.browserSize = "1920x1080";
                Configuration.headless = true;
                Configuration.browserCapabilities = options;
                break;
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--guest")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-notifications")
                        .addArguments("--disable-gpu")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--remote-allow-origins=*");
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("translate_enabled", false);
                options.setExperimentalOption("prefs", preferences);
                Configuration.browser = browser;
                Configuration.browserSize = "1920x1080";
                Configuration.headless = true;
                Configuration.browserCapabilities = options;
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported \"browser\" value: " + browser);
        }
    }
}
