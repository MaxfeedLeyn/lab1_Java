package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class PersonUtilsTest {

    @Nested
    @DisplayName("capitalizeText Tests")
    class capitalizeTextTest {

        @ParameterizedTest
        @CsvSource({
                "nEwWindow, Newwindow",
                "endPoint, Endpoint",
                "   algotester     , Algotester"
        })
        @DisplayName("Should return words with capitalized first letter")
        void capitalizeFirstLetterTest(String validString, String exepectedString){
            assertEquals(PersonUtils.capitalizeText(validString), exepectedString,
                    ()-> String.format("Should be '%s', but was '%s'", exepectedString, PersonUtils.capitalizeText(validString)));
        }

        @Test
        @DisplayName("Should remain null when name is null")
        void capitalizeNullTest(){
            assertNull(PersonUtils.capitalizeText(null));
        }

        @ParameterizedTest
        @CsvSource({
                "  ,  ",
                "   ,   "
        })
        @DisplayName("Strings should remains emptiness")
        void capitalizeEmptyTest(String emptyString, String exepectedString){
            assertEquals(exepectedString, PersonUtils.capitalizeText(emptyString),
                    ()->String.format("Strings should be empty, but was '%s'", PersonUtils.capitalizeText(emptyString)));
        }
    }

    @Nested
    @DisplayName("formatName Tests")
    class formatNameTest {

        @ParameterizedTest
        @CsvSource({
                "first, name, first name",
                "last, name, last name",
                "Robert, Polson, Robert Polson"
        })
        @DisplayName("Should return formatted name with valid data")
        void formatNameTest(String validFirstName, String validLastName, String expectedOutput){
            String actual =  PersonUtils.formatName(validFirstName, validLastName);
            assertEquals(expectedOutput, actual,
                    ()->String.format("Should be '%s', but was '%s'", expectedOutput, actual));
        }

        @Test
        @DisplayName("Should return nothing when firstName is null")
        void formatFirstNameNullTest(){
            assertEquals("", PersonUtils.formatName(null, "ValidData"),
                    ()->String.format("FormatName should return empty but was %s", PersonUtils.formatName(null, "ValidData")));
        }

        @Test
        @DisplayName("Should return nothing when lastName is null")
        void formatLastNameNullTest(){
            assertEquals("", PersonUtils.formatName("ValidDate", null),
                    ()->String.format("FormatName should return empty but was %s", PersonUtils.formatName("ValidDate", null)));
        }
    }

    @Nested
    @DisplayName("formatAddress Tests")
    class formatAddressTest {

        @Test
        @DisplayName("Should not format when null")
        void formatRestaurantNullLocation() {
            assertNull(PersonUtils.formatAddress(null),
                    () -> String.format("Restaurant name expected to be null, but was %s", RestaurantUtils.formatRestaurantLocation(null)));
        }

        @ParameterizedTest
        @CsvSource({
                "st. central 1, St. Central 1",
                "ST. INDEPENDENCY 2, St. Independency 2",
                "sT. InDePeNdENcY 3, St. Independency 3"
        })
        void formatRestaurantLocation(String address, String expected) {
            String actual = PersonUtils.formatAddress(address);
            assertEquals(expected, actual,
                    ()->String.format("Restaurant name expected to be %s, but was %s", expected, actual));
        }
    }

    @Nested
    @DisplayName("isValidAddress Tests")
    class ValidAddressTest {

        @ParameterizedTest
        @ValueSource(strings = {"St. Central 1", "St. Independency 3"})
        @DisplayName("Should return true when valid address")
        void ValidAddressTests(String validAddress) {
            assertTrue(PersonUtils.isValidAddress(validAddress));
        }

        @Test
        @DisplayName("Should return false when address is invalid")
        void ValidAddressNullTests() {
            assertFalse(PersonUtils.isValidAddress(null));
        }
    }

    @Nested
    @DisplayName("createAddressfromName Tests")
    class AddressFromNameTests {

        @ParameterizedTest
        @CsvSource({
                "Central, St. Central",
                "Independency, St. Independency"
        })
        @DisplayName("Should return name of the street from the name")
        void AddressFromNameTests(String street, String expected) {
            assertEquals(expected, PersonUtils.createAddressfromName(street).substring(0, street.length() + 4),
                    ()->String.format("AddressFromName should return '%s', but was '%s'", expected, expected));
        }

        @Test
        @DisplayName("Should remain null during creating address")
        void AddressFromNameNullTests() {
            assertNull(PersonUtils.createAddressfromName(null));
        }
    }
}
