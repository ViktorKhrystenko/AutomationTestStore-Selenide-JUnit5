package utils.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

import static config.ConfigReader.readConfigProperty;

public class DriverFactory {

    public static WebDriver createDriver() {
        String runTarget = readConfigProperty("run.target", "local");
        String browser = readConfigProperty("browser", "chrome");
        switch (runTarget.toLowerCase()) {
            case "local":
                return createLocalDriver(browser);

            case "jenkins-docker-agent":
                return createJenkinsDockerAgentDriver(browser);

            default:
                throw new IllegalArgumentException("Unsupported \"run.target\" value: " + runTarget);
        }
    }


    private static WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized")
                        .addArguments("--disable-notification")
                        .addArguments("--incognito");
                return new ChromeDriver(options);
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=1920")
                        .addArguments("--height=1080")
                        .addArguments("-private");
                return new FirefoxDriver(options);
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized")
                        .addArguments("--disable-notification")
                        .addArguments("--guest")
                        .addArguments("--inprivate");
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("translate_enabled", false);
                options.setExperimentalOption("pref", preferences);
                return new EdgeDriver(options);
            }
            default:
                throw new IllegalArgumentException("Unsupported \"browser\" value: " + browser);
        }
    }

    private static WebDriver createJenkinsDockerAgentDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--window-size=1920,1080")
                        .addArguments("--headless=new")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-notification")
                        .addArguments("--disable-gpu")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--remote-allow-origins=*");
                return new ChromeDriver(options);
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=1920")
                        .addArguments("--height=1080")
                        .addArguments("-headless");
                return new FirefoxDriver(options);
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--window-size=1920,1080")
                        .addArguments("--guest")
                        .addArguments("--headless=new")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-notification")
                        .addArguments("--disable-gpu")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--remote-allow-origins=*");
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("translate_enabled", false);
                options.setExperimentalOption("pref", preferences);
                return new EdgeDriver(options);
            }
            default:
                throw new IllegalArgumentException("Unsupported \"browser\" value: " + browser);
        }
    }
}
