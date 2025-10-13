package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantUtilsTest {

    @Nested
    @DisplayName("formatRestaurantName Tests")
    class FormatRestaurantNameTests {

        @ParameterizedTest
        @CsvSource({
                "MAiDo, Maido",
                "GeRAniUM, Geranium",
                "DISfRUtAr, Disfrutar"
        })
        @DisplayName("Should format name when valid values")
        void formatRestaurantName(String restaurantName, String expected) {
            String actual = RestaurantUtils.formatRestaurantName(restaurantName);
            assertEquals(expected, actual,
                    ()->String.format("Restaurant name expected to be %s, but was %s", expected, actual));
        }

        @Test
        @DisplayName("Should not format when null")
        void formatRestaurantNullName() {
            assertNull(RestaurantUtils.formatRestaurantName(null),
                    ()->String.format("Restaurant name expected to be null, but was %s", RestaurantUtils.formatRestaurantName(null)));
        }
    }

    @Nested
    @DisplayName("formatRestaurantLocation Tests")
    class FormatRestaurantLocationTests {

        @Test
        @DisplayName("Should not format when null")
        void formatRestaurantNullLocation() {
            assertNull(RestaurantUtils.formatRestaurantLocation(null),
                    () -> String.format("Restaurant name expected to be null, but was %s", RestaurantUtils.formatRestaurantLocation(null)));
        }

        @ParameterizedTest
        @CsvSource({
                "st. central 1, St. Central 1",
                "ST. INDEPENDENCY 2, St. Independency 2",
                "sT. InDePeNdENcY 3, St. Independency 3"
        })
        void formatRestaurantLocation(String restaurantName, String expected) {
            String actual = RestaurantUtils.formatRestaurantLocation(restaurantName);

            assertEquals(expected, actual,
                    ()->String.format("Restaurant name expected to be %s, but was %s", expected, actual));
        }
    }

    @Nested
    @DisplayName("isValidRestaurantName Tests")
    class  IsValidRestaurantNameTests {

        @ParameterizedTest
        @ValueSource(strings = {"A", " ", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"})
        @DisplayName("Should return false when incorrect values")
        void RestaurantNameCheckWithInvalidData(String invalidNames){
            assertFalse(RestaurantUtils.isValidRestaurantName(invalidNames),
                    ()->String.format("Expected to be false for name length %d (min: 3, max: 20), but true", invalidNames.length()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"La Consta", "McDonalds", "Kentucky"})
        @DisplayName("Should return true when correct values")
        void RestaurantNameCheckWithValidData(String validNames){
            assertTrue(RestaurantUtils.isValidRestaurantName(validNames),
                    () -> String.format("Expected to be true for name %s, but false", validNames));
        }
    }

    @Nested
    @DisplayName("isValidRestaurantLocation Tests")
    class IsValidRestaurantLocationTests {

        @ParameterizedTest
        @ValueSource(strings = {"st Cen", "Center 1", "1"})
        @DisplayName("Should return false when invalid values")
        void RestaurantNameCheckWithInvalidData(String invalidNames){
            assertFalse(RestaurantUtils.isValidRestaurantLocation(invalidNames),
                    ()->String.format("Expected to be false for name %s, but true", invalidNames));
        }

        @ParameterizedTest
        @ValueSource(strings = {"St. Central 1", "St. Independancy 1"})
        @DisplayName("Should return true when valid values")
        void RestaurantNameCheckWithValidData(String validNames){
            assertTrue(RestaurantUtils.isValidRestaurantLocation(validNames),
                    ()->String.format("Expected to be true for name %s, but false", validNames));
        }
    }

}
