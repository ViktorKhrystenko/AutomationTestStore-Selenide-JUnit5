package utils;

import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatHelper {
    public static boolean doesStringMatchRegex(String checkedString, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher matcher = regexPattern.matcher(checkedString);
        return matcher.matches();
    }

    public static String trimCloseAlertCross(String stringToTrim) {
        // we substring error message, because first 2 characters are "×\n" from close alert cross
        return stringToTrim.substring(2);
    }

    public static String addSymbolsToField(String field, String symbolsToAdd, int maxFieldLength) {
        if (symbolsToAdd.length() > maxFieldLength) {
            throw new IllegalArgumentException("Length of symbolsToAdd argument is bigger than maxFieldLength");
        }
        if (field.length() + symbolsToAdd.length() >= maxFieldLength) {
            if (symbolsToAdd.length() >= field.length()) {
                field = symbolsToAdd;
            }
            else {
                field = symbolsToAdd + field.substring(symbolsToAdd.length());
            }
        }
        else {
            field = symbolsToAdd + field;
        }
        return field;
    }
}
