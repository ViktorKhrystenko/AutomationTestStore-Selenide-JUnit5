package utils;

import exceptions.PropertiesFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties(String propertiesPath) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getResourceAsStream(propertiesPath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesFileNotFoundException(propertiesPath);
        }
        return properties;
    }
}
