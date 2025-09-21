package ua.university.util;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;

public class DeliveryUtils {

    private DeliveryUtils() {
    }

    public static boolean isValidDate(Date date){
        return ValidationHelper.isValidDate(date);
    }
}
