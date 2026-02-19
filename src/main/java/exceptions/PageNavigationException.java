package exceptions;

public class PageNavigationException extends RuntimeException {
    public PageNavigationException(String pageName, String regex, String currentUrl) {
        super(String.format("Unable to navigate to %s. " +
                "Url had to match with regex: %s . Current url: %s",
                pageName, regex, currentUrl));
    }
}
