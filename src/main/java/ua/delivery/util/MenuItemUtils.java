package ua.delivery.util;

public class MenuItemUtils {

    private MenuItemUtils() {
    }

    public static String capitalizeText(String str) {
        return PersonUtils.capitalizeText(str);
    }

    public static boolean isValidName(String name) {
        return ValidationHelper.isStringLengthBetween(name, 1, 30);
    }

    public static boolean isValidCategory(String category) {
        return ValidationHelper.isStringLengthBetween(category, 3, 30);
    }

    public static boolean isValidFloat(float value) {
        return value > 0;
    }
}
