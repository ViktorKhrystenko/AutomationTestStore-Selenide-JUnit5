package exceptions;

public class PropertiesFileNotFoundException extends RuntimeException {
    public PropertiesFileNotFoundException(String propertiesFilePath) {
        super(String.format("Unable to locate .properties file by path: %s",
                propertiesFilePath));
    }
}
