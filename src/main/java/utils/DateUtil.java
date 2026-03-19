package utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

public class DateUtil {
    public static LocalDateTime parseToDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return LocalDateTime.parse(dateString, formatter);
    }

    public static boolean areDatesEqual(Temporal firstDate, Temporal secondDate, long acceptableSecondsDelta) {
        if (firstDate == null || secondDate == null) {
            return false;
        }
        long secondsDelta = Math.abs(Duration.between(firstDate, secondDate).getSeconds());
        return secondsDelta <= acceptableSecondsDelta;
    }
}
