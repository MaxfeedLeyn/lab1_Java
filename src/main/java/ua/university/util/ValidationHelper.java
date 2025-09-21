package ua.university.util;

import java.util.regex.Pattern;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;

public class ValidationHelper {

    private  ValidationHelper() {
    }

    static boolean isStringMatchPattern(String text, String pattern) {
        if(text == null || pattern == null) {
            return false;
        }
        return Pattern.matches(pattern, text);
    }

    static boolean isNumberBetween(int number, int min, int max) {
        return number >= min && number <= max;
    }

    static boolean isStringLengthBetween(String text, int min, int max) {
        if(text == null) {
            return false;
        }

        int length = text.trim().length();
        return length >= min && length <= max;
    }

    static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static boolean isValidDate(Date date){
        if (date == null) {
            return false;
        }
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate monthBefore = localDate.minusMonths(1);
        LocalDate monthAfter = localDate.plusMonths(1);
        LocalDate now =  LocalDate.now();

        return !now.isBefore(monthBefore) && !now.isAfter(monthAfter);
    }
}
