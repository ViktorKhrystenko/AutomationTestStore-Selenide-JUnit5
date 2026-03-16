package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatNumberRounder {
    public static double round(double numberToRound, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(numberToRound);
        return bigDecimal.setScale(precision, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
