package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.FloatNumberRounder.round;

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

    public static String getTextAfterColon(String string) {
        return string.substring(string.indexOf(':') + 1).trim();
    }

    public static double parsePriceStringToDouble(String priceString) {
        // substring to remove first '$' symbol
        return round(Double.parseDouble(
                priceString.substring(1)
                        .replace(",", "")),
                2);
    }

    public static String leftOnlyCharactersInRange(String stringToEdit, char lowerBoundChar, char upperBoundChar) {
        StringBuilder editedString = new StringBuilder(stringToEdit);
        for (int i = editedString.length() - 1; i >= 0; i--) {
            if (editedString.charAt(i) < lowerBoundChar
                    || editedString.charAt(i) > upperBoundChar) {
                editedString.deleteCharAt(i);
            }
        }
        return editedString.toString();
    }

    public static String replaceByRegex(String targetString, String regexPattern, String newString) {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(targetString);
        return matcher.replaceAll(newString);
    }
}
