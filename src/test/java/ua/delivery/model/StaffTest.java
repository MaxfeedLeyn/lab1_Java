package ua.delivery.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import ua.delivery.exception.InvalidDataException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create person with default fields")
        void testConstructor() {
            Staff person = new Staff();

            assertEquals("Jane", person.getFirstName());
            assertEquals("Doe", person.getLastName());
            assertEquals("St. Central 1", person.getAddress());
        }

        @Test
        @DisplayName("Constructor should not set invalid fields")
        void testConstructorWithInvalidData() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                Staff person = new Staff("", "validName", "invalidAddress");
            });

            assertTrue(exception.getMessage().contains("First name cannot be null or blank"));
            assertTrue(exception.getMessage().contains("Address must match pattern St. NameOfStreet number"));
        }

        @Test
        @DisplayName("Constructor should be public")
        void testConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<Staff> constructor = Staff.class.getDeclaredConstructor();
            assertTrue(Modifier.isPublic(constructor.getModifiers()),
                    "Constructor should be public but was" + Modifier.toString(constructor.getModifiers()));
        }

        @Test
        @DisplayName("Constructor should create an instance with valid data")
        void ConstructorWithValidData() {
            Staff staff = new Staff("Robert", "Polson", "St. Central 1");
            assertNotNull(staff,
                    () -> String.format("Constructor should create an object with valid data but it wasn't "));
        }

        @Test
        @DisplayName("Constructor should not set invalid data")
        void ConstructorWithInvalidData() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                Staff staff = new Staff(null, "P",  "Central");
            });

            assertTrue(exception.getMessage().contains("First name cannot be null or blank"));
            assertTrue(exception.getMessage().contains("Last name must be 2-50 character long and contain only letters, hyphens, or apostrophes"));
            assertTrue(exception.getMessage().contains("Address must match pattern St. NameOfStreet number"));
        }

    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {

        @Test
        @DisplayName("rank should be private")
        void testFieldsArePrivate() throws NoSuchFieldException {
            var field = Staff.class.getDeclaredField("rankOfDelivery");
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    ()-> String.format("Expected field rankOfDelivery to be private, but was: %s",
                            Modifier.toString(field.getModifiers())));
        }
    }

    @Nested
    @DisplayName("getFullName Tests")
    class GetFullNameTests {

        @ParameterizedTest
        @CsvSource({
                "Robert, Polson, 3, Robert Polson; Rank of delivery: 3.0",
                "Tom, Holand, 5, Tom Holand; Rank of delivery: 5.0",
                "Bruce, Wayne, 4, Bruce Wayne; Rank of delivery: 4.0"
        })
        @DisplayName("getFullName should format with valid data")
        void testGetFullName(String firstName, String lastName, float rankOfDelivery, String expectedFullName) {
            Staff staff = new Staff(firstName, lastName, "St. Central 1", rankOfDelivery);
            assertEquals(expectedFullName, staff.getFullName(),
                    ()->String.format("getFullName should format with valid data, expected : %s, but was: %s",
                            expectedFullName, staff.getFullName()));
        }

//        @Test
//        @DisplayName("getFullName should not format when invalid lastName")
//        void testGetFullNameWithInvalidLast() {
//            Staff staff = new Staff("Robert", null, "St. Central 1");
//            assertNull(staff.getFullName(),"getFullName should not format with valid data");
//        }
//
//        @Test
//        @DisplayName("getFullName should not format when invalid firstName")
//        void testGetFullNameWithInvalidFirst() {
//            Staff staff = new Staff(null, "Polson", "St. Central 1");
//            assertNull(staff.getFullName(),"getFullName should not format with valid data");
//        }
    }

    @Nested
    @DisplayName("FirstName Tests")
    class FirstNameTests {

        @ParameterizedTest
        @CsvSource({
                "john, John",
                "JANE, Jane",
                "bOb, Bob",
                "'  alice  ', Alice"
        })
        @DisplayName("Should set and capitalize valid first names")
        void testValidFirstNames(String input, String expected) {
            Staff person = new Staff();
            person.setFirstName(input);

            assertEquals(expected, person.getFirstName(),
                    ()->String.format("Expected first name to be '%s' but was '%s'", expected, person.getFirstName()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        @DisplayName("Should not set invalid first names - empty and whitespace")
        void testInvalidFirstNames(String invalidName) {
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setFirstName(invalidName);
            });

            assertTrue(exception.getMessage().contains("First name cannot be null or blank"));
        }

        @Test
        @DisplayName("Should not set first name that exceeds maximum length")
        void testSetFirstNameTooLong(){
            String tooLongString = "A".repeat(51);
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setFirstName(tooLongString);
            });

            assertTrue(exception.getMessage().contains("First name must be 2-50 character long and contain only letters, hyphens, or apostrophes"));
        }

        @Test
        @DisplayName("Should not set null first name")
        void testSetNullFirstName() {
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setFirstName(null);
            });

            assertTrue(exception.getMessage().contains("First name cannot be null or blank"));
        }
    }

    @Nested
    @DisplayName("LastName tests")
    class LastNameTests {

        @ParameterizedTest
        @CsvSource({
                "smith, Smith",
                "JOHNSON, Johnson",
                "WILSoN, Wilson",
                "'  brown  ', Brown"
        })
        @DisplayName("Should set and capitalize valid last names")
        void testValidLastNames(String input, String expected) {
            Staff person = new Staff();
            person.setLastName(input);

            assertEquals(expected, person.getLastName(),
                    ()->String.format("Expected last name to be '%s' but was '%s'", input, person.getLastName()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @DisplayName("Should not set invalid last names - empty and whitespace")
        void testSetInvalidLastNames(String invalidLastName) {
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setLastName(invalidLastName);
            });

            assertTrue(exception.getMessage().contains("Last name cannot be null or blank"));
        }

        @Test
        @DisplayName("Should not set last name that exceeds maximum length")
        void testSetLastNameTooLong() {
            String tooLongString = "A".repeat(51);
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setLastName(tooLongString);
            });

            assertTrue(exception.getMessage().contains("Last name must be 2-50 character long and contain only letters, hyphens, or apostrophes"));
        }

        @Test
        @DisplayName("Should not set null last name")
        void testSetNullLastName() {
            Staff person = new Staff();

            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setLastName(null);
            });

            assertTrue(exception.getMessage().contains("Last name cannot be null or blank"));
        }
    }

    @Nested
    @DisplayName("Address Tests")
    class AddressTests{

        @ParameterizedTest
        @CsvSource({
                "ST. Central 1, St. Central 1",
        })
        @DisplayName("Should set and format valid addresses")
        void testValidAddresses(String input, String expected) {
            Staff person = new Staff();
            person.setAddress(input);

            assertEquals(expected, person.getAddress(),
                    ()->String.format("Expected address to be '%s' but was '%s'", expected, person.getAddress()));
        }

        @Test
        @DisplayName("Should trim and format address with whitespace")
        void testTrimAddressWithWhitespace() {
            String addressWithSpaces = "     St. Central 1   ";
            String expectedAddress = "St. Central 1";

            Staff person = new Staff();
            person.setAddress(addressWithSpaces);

            assertEquals(expectedAddress, person.getAddress(),
                    ()->String.format("Expected email to be formatted to '%s', but was '%s'",
                            expectedAddress, person.getAddress()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid", "St.Central", "St.Central 1", "Central 1"})
        @DisplayName("Should not set invalid addresses")
        void testSetInvalidAddress(String invalidAddress) {
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setAddress(invalidAddress);
            });

            assertTrue(exception.getMessage().contains("Address must match pattern St. NameOfStreet number"));
        }

        @Test
        @DisplayName("Should not set null email")
        void testSetNullAddress() {
            Staff person = new Staff();
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                person.setAddress(null);
            });

            assertTrue(exception.getMessage().contains("Address cannot be null or blank"));
        }
    }

    @Nested
    @DisplayName("RankOfDelivery Tests")
    class RankOfDeliveryTests{

        @ParameterizedTest
        @ValueSource(floats = {-1.0f, -3.0f, -10.0f})
        @DisplayName("Rank should not be set with invalid value")
        void testInvalidRank(float invalidRank) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                Staff staff = new Staff("firstName", "lastName", "St. Email 1", invalidRank);
            });

            assertTrue(exception.getMessage().contains("Rank must be at least 0"));
        }

        @ParameterizedTest
        @ValueSource(floats = {1.0f, 1.1f, 4.9f, 5f})
        @DisplayName("Rank should be set when valid values")
        void testValidRank(float validRank) {
            Staff staff = new Staff("firstName", "lastName", "St. Email 1", validRank);
            assertEquals(validRank, staff.getRankOfDelivery(),
                    () -> String.format("Suppose to be the same, as valid values provided: %s, but was: %s",
                            validRank, staff.getRankOfDelivery()));
        }
    }

    @Nested
    @DisplayName("createStaff Static Method Tests")
    class CreateStaffTests {

        @ParameterizedTest
        @CsvSource({
                "john, doe, John, Doe, St. Central 1",
                "jane, smith, Jane, Smith, St. Central 2",
                "bob, wilson, Bob, Wilson, St. Central 3"
        })
        @DisplayName("Should create staff with valid names")
        void testCreatStaffWithValidNames(String inputFirst, String inputLast,
                                           String expectedFist, String expectedLast, String expectedAddress) {
            Staff person = Staff.create(inputFirst, inputLast, expectedAddress);

            assertNotNull(person,
                    () -> String.format("Expected Staff to be created with names '%s' and '%s'", inputFirst, inputLast));
            assertEquals(expectedFist, person.getFirstName(),
                    () -> String.format("Expected firstName to be '%s' but was '%s'", expectedFist, person.getFirstName()));
            assertEquals(expectedLast, person.getLastName(),
                    () -> String.format("Expected lastName to be '%s' but was '%s'", expectedLast, person.getLastName()));
            assertEquals(expectedAddress, person.getAddress(),
                    () -> String.format("Expected address to be '%s' but was '%s'", expectedAddress, person.getAddress()));
        }

        @ParameterizedTest
        @CsvSource({
                "john, doe, John, Doe, St. Central 1, 4.0",
                "jane, smith, Jane, Smith, St. Central 2, 2.7",
                "bob, wilson, Bob, Wilson, St. Central 3, 3.0"
        })
        @DisplayName("Should create staff with valid names")
        void testCreatStaffWithValidNames(String inputFirst, String inputLast,
                                           String expectedFist, String expectedLast, String expectedAddress, float rankOfDelivery) {
            Staff person = Staff.create(inputFirst, inputLast, expectedAddress,  rankOfDelivery);

            assertNotNull(person,
                    () -> String.format("Expected Staff to be created with names '%s' and '%s'", inputFirst, inputLast));
            assertEquals(expectedFist, person.getFirstName(),
                    () -> String.format("Expected firstName to be '%s' but was '%s'", expectedFist, person.getFirstName()));
            assertEquals(expectedLast, person.getLastName(),
                    () -> String.format("Expected lastName to be '%s' but was '%s'", expectedLast, person.getLastName()));
            assertEquals(expectedAddress, person.getAddress(),
                    () -> String.format("Expected address to be '%s' but was '%s'", expectedAddress, person.getAddress()));
            assertEquals(rankOfDelivery, person.getRankOfDelivery(),
                    () -> String.format("Expected rank of delivery to be %d, but was: %s", rankOfDelivery,  person.getRankOfDelivery()));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Staff person = new Staff("John", "Doe", "St. Central 1", 1);
            String expectedString = "Staff{firstName='John', lastName='Doe', address='St. Central 1', rankOfDelivery='1.0'}";

            assertEquals(expectedString, person.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, person.toString()));
        }

        @Test
        @DisplayName("Should format toString correctly with default fields")
        void testToStringWithNullFields() {
            Staff person = new Staff();
            String expectedString = "Staff{firstName='Jane', lastName='Doe', address='St. Central 1', rankOfDelivery='0.0'}";

            assertEquals(expectedString, person.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, person));
        }
    }

    @Nested
    @DisplayName("equals and hashcode Tests")
    class EqualsAndHashCodeTests {
        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Staff person = new Staff("John", "Doe", "St. Central 1", 1);

            assertTrue(person.equals(person), "Staff should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to staff with same data")
        void testEqualsSymmetric() {
            Staff person1 = new Staff("John", "Doe", "St. Central 1", 1);
            Staff person2 = new Staff("John", "Doe", "St. Central 1", 1);

            assertTrue(person1.equals(person2),
                    "Staff with same data should be equal");
            assertTrue(person2.equals(person1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Staff person = new Staff("John", "Doe", "St. Central 1", 1);

            assertFalse(person.equals(null), "Staff should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Staff person = new Staff("John", "Doe", "St. Central 1", 1);
            String notAStaff = "Not a staff";

            assertFalse(person.equals(notAStaff), "Staff should not be equal to different class");
        }

        @Test
        @DisplayName("Equal persons should have same hashCode")
        void testHashCodeConsistency() {
            Staff person1 = new Staff("John", "Doe", "St. Central 1", 1);
            Staff person2 = new Staff("John", "Doe", "St. Central 1", 1);

            assertTrue(person1.equals(person2), "Persons should be equal");
            assertEquals(person1.hashCode(), person2.hashCode(),
                    "Equal persons should have same hashCode");
        }
    }
}
