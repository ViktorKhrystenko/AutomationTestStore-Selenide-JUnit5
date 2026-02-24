package config;

import utils.PropertiesLoader;

import java.util.Properties;

import static constants.ResourcesPaths.CONFIG_PATH;

public class ConfigReader {
    private static final Properties LOADED_CONFIG;


    static {
        LOADED_CONFIG = PropertiesLoader.loadProperties(CONFIG_PATH + "/config.properties");
    }


    public static String readConfigProperty(String key, String defaultValue) {
        String property = System.getProperty(key);
        if (property != null) {
            return property;
        }
        return LOADED_CONFIG.getProperty(key, defaultValue);
    }
}
