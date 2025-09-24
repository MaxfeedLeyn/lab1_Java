package ua.delivery.util;

import java.util.Date;

public class DeliveryUtils {

    private DeliveryUtils() {
    }

    public static boolean isValidDate(Date date){
        return ValidationHelper.isValidDate(date);
    }
}
