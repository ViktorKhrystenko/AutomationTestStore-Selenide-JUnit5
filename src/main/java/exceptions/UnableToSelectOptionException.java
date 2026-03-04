package exceptions;

public class UnableToSelectOptionException extends RuntimeException {
    public UnableToSelectOptionException(String optionVisibleText, String locator) {
        super(String.format("Unable to select option with visible text \"%s\" " +
                "in select located by %s selector",
                optionVisibleText, locator));
    }
}
