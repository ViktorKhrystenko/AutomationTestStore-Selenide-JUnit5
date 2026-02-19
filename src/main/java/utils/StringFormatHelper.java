package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatHelper {
    public static boolean doesStringMatchRegex(String checkedString, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher matcher = regexPattern.matcher(checkedString);
        return matcher.matches();
    }
}
