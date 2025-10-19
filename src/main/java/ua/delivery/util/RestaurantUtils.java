package ua.delivery.util;

public class RestaurantUtils {

    private  RestaurantUtils() {
    }

    public static String formatRestaurantName(String name) {
        if (name == null) {
            return null;
        }
        name = name.trim();

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static String formatRestaurantCuisine(String cuisine) {
        return formatRestaurantName(cuisine);
    }

    public static String formatRestaurantLocation(String location) {
        if (location == null || location.length() <= 5) {
            return null;
        }
        String trimmed = location.trim();
        return trimmed.substring(0, 1).toUpperCase() +
                trimmed.substring(1, 4).toLowerCase() +
                trimmed.substring(4, 5).toUpperCase() +
                trimmed.substring(5).toLowerCase();
    }

    public static boolean isValidRestaurantName(String restaurantName) {
        if (restaurantName == null) {
            return false;
        }
        return ValidationHelper.isStringLengthBetween(restaurantName, 3, 20);
    }

    public static boolean isValidRestaurantCuisine(String cuisine) {
        return isValidRestaurantName(cuisine);
    }

    public static boolean isValidRestaurantLocation(String location) {
        location = formatRestaurantLocation(location);
        return ValidationHelper.isStringMatchPattern(location, "^(?i)St\\.?\\s+[\\p{L}0-9.'\\-\\s]+\\s+\\d+[A-Za-z0-9\\/-]*$");
    }
}
