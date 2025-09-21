package ua.university.util;

import ua.university.Main;

public class PersonUtils {

    private PersonUtils() {}

    public static String capitalizeText(String str) {
        if(str == null || str.trim().isEmpty()) {
            return str;
        }

        String trimmed = str.trim();
        return trimmed.substring(0, 1).toUpperCase() +
                trimmed.substring(1).toLowerCase();
    }

    public static String formatName(String firstName, String lastName) {
        if(firstName == null || lastName == null) {
            return "";
        }
        return firstName.trim() + " " + lastName.trim();
    }

    public static String formatAddress(String address) {
        if(address == null) {
            return null;
        }
        String trimmed = address.trim();
        return trimmed.substring(0, 1).toUpperCase() +
                trimmed.substring(1, 4).toLowerCase() +
                trimmed.substring(4, 5).toUpperCase() +
                trimmed.substring(5).toLowerCase();
    }

    public static boolean isValidName(String name) {
        return ValidationHelper.isStringLengthBetween(name, 3, 30);
    }

    public static boolean isValidAddress(String address) {
        return ValidationHelper.isStringMatchPattern(address, "^(?i)St\\.?\\s+[\\p{L}0-9.'\\-\\s]+\\s+\\d+[A-Za-z0-9\\/-]*$");
    }

    public static String createAddressfromName(String addressName) {
        if (addressName == null) {
            return null;
        }
        if(!ValidationHelper.isStringLengthBetween(addressName, 3, 30)) {
            return null;
        }
        String trimmed = addressName.trim();
        return "St. "  + trimmed.substring(0, 1).toUpperCase() +
                trimmed.substring(1).toLowerCase() +
                ValidationHelper.getRandomNumber(1, 30);
    }
}
