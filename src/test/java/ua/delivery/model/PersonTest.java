package ua.delivery.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create person with null fields")
        void testConstructor() {
            Person person = new Person();

            assertNull(person.getFirstName(), "Expected first name to be null after default constructor");
            assertNull(person.getLastName(), "Expected last name to be null after default constructor");
            assertNull(person.getAddress(), "Expected address to be null after default constructor");
        }

        @Test
        @DisplayName("Constructor should not set invalid fields")
        void testConstructorWithInvalidData() {
            Person person = new Person("", "validName", "invalidAddress");

            assertNull(person.getFirstName(), "Expected first name to be null when invalid name provided");
            assertEquals("Validname", person.getLastName(), "Expected last name to be set when valid");
            assertNull(person.getAddress(), "Expected address to be null when invalid address provided");
        }
    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {

        @Test
        @DisplayName("getFullName method should be protected")
        void testGetFullNameProtected() throws NoSuchMethodException {
            Method method = Person.class.getDeclaredMethod("getFullName");
            assertTrue(Modifier.isProtected(method.getModifiers()),
                    "Expected fullName to be protected, but was: " + Modifier.toString(method.getModifiers()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"firstName", "lastName", "address"})
        @DisplayName("Fields should be protected")
        void testFieldsAreProtected(String fieldName) throws NoSuchFieldException {
            var field = Person.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isProtected(field.getModifiers()),
                    () -> String.format("Expected field %s to be protected, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }
    }

    @Nested
    @DisplayName("getFullName Tests")
    class GetFullNameTests {

        @Test
        @DisplayName("Should format full name with both name set")
        void testGetFullNameWithBothNames() throws Exception {
            Person person = new Person();
            person.setFirstName("John");
            person.setLastName("Doe");

            Method getFullNameMethod = Person.class.getDeclaredMethod("getFullName");
            getFullNameMethod.setAccessible(true);
            String actualFullName = (String) getFullNameMethod.invoke(person);

            assertEquals("John Doe", actualFullName,
                    () -> String.format("Expected full name to be '%s' but was '%s'", "John Doe", actualFullName));
        }

        @Test
        @DisplayName("Should format full name when firstName is null")
        void testGetFullNameWithNullFirstName() throws Exception {
            Person person = new Person();
            person.setLastName("Wilson");

            Method getFullNameMethod = Person.class.getDeclaredMethod("getFullName");
            getFullNameMethod.setAccessible(true);
            String actualFullName = (String) getFullNameMethod.invoke(person);

            assertEquals("", actualFullName,
                    ()->String.format("Expected full name to be empty when firstName is null, but was %s", actualFullName));
        }

        @Test
        @DisplayName("Should format full name when lastName is null")
        void testGetFullNameWithNullLastName() throws Exception {
            Person person = new Person();
            person.setFirstName("John");

            Method getFullNameMethod = Person.class.getDeclaredMethod("getFullName");
            getFullNameMethod.setAccessible(true);
            String actualFullName = (String) getFullNameMethod.invoke(person);

            assertEquals("", actualFullName,
                    ()->String.format("Expected full name to be empty when lastName is null, but was %s", actualFullName));
        }
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
            Person person = new Person();
            person.setFirstName(input);

            assertEquals(expected, person.getFirstName(),
                    ()->String.format("Expected first name to be '%s' but was '%s'", expected, person.getFirstName()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        @DisplayName("Should not set invalid first names - empty and whitespace")
        void testInvalidFirstNames(String invalidName) {
            Person person = new Person();
            person.setFirstName(invalidName);

            assertNull(person.getFirstName(),
                    ()->String.format("Expected first name to remain null for invalid input '%s', but was '%s'",
                            invalidName, person.getFirstName()));
        }

        @Test
        @DisplayName("Should not set first name that exceeds maximum length")
        void testSetFirstNameTooLong(){
            String tooLongString = "A".repeat(51);
            Person person = new Person();
            person.setFirstName(tooLongString);

            assertNull(person.getFirstName(),
                    ()->String.format("Expected to remain null for name length %d (max: 50), but was '%s'",
                            tooLongString.length(), person.getFirstName()));
        }

        @Test
        @DisplayName("Should not set null first name")
        void testSetNullFirstName() {
            Person person = new Person();
            person.setFirstName(null);

            assertNull(person.getFirstName(), "Expected first name to remain null, when null is provided");
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
            Person person = new Person();
            person.setLastName(input);

            assertEquals(expected, person.getLastName(),
                    ()->String.format("Expected last name to be '%s' but was '%s'", input, person.getLastName()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @DisplayName("Should not set invalid last names - empty and whitespace")
        void testSetInvalidLastNames(String invalidLastName) {
            Person person = new Person();
            person.setLastName(invalidLastName);

            assertNull(person.getLastName(),
                    ()->String.format("Expected last name to remain null for invalid input '%s', but was '%s'",
                            invalidLastName, person.getLastName()));
        }

        @Test
        @DisplayName("Should not set last name that exceeds maximum length")
        void testSetLastNameTooLong() {
            String tooLongString = "A".repeat(51);
            Person person = new Person();
            person.setLastName(tooLongString);

            assertNull(person.getLastName(),
                    ()->String.format("Expected lastName to remain null length %d (max: 50), but was '%s'",
                            tooLongString.length(), person.getLastName()));
        }

        @Test
        @DisplayName("Should not set null last name")
        void testSetNullLastName() {
            Person person = new Person();
            person.setLastName(null);

            assertNull(person.getLastName(), "Expected last name to remain null, when null is provided");
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
            Person person = new Person();
            person.setAddress(input);

            assertEquals(expected, person.getAddress(),
                    ()->String.format("Expected address to be '%s' but was '%s'", expected, person.getAddress()));
        }

        @Test
        @DisplayName("Should trim and format address with whitespace")
        void testTrimAddressWithWhitespace() {
            String addressWithSpaces = "     St. Central 1   ";
            String expectedAddress = "St. Central 1";

            Person person = new Person();
            person.setAddress(addressWithSpaces);

            assertEquals(expectedAddress, person.getAddress(),
                    ()->String.format("Expected email to be formatted to '%s', but was '%s'",
                            expectedAddress, person.getAddress()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "invalid", "St.Central", "St.Central 1", "Central 1"})
        @DisplayName("Should not set invalid addresses")
        void testSetInvalidAddress(String invalidAddress) {
            Person person = new Person();
            person.setAddress(invalidAddress);

            assertNull(person.getAddress(),
                    ()->String.format("Expected email to remail null for invalid input '%s' but was '%s'",
                            invalidAddress, person.getAddress()));
        }

        @Test
        @DisplayName("Should not set null email")
        void testSetNullAddress() {
            Person person = new Person();
            person.setAddress(null);

            assertNull(person.getAddress(), "Expected address to remail null, when null is provided");
        }
    }

    @Nested
    @DisplayName("createPerson Static Method Tests")
    class CreatePersonTests {

        @ParameterizedTest
        @CsvSource({
                "john, doe, John, Doe, St. Central 1",
                "jane, smith, Jane, Smith, St. Central 2",
                "bob, wilson, Bob, Wilson, St. Central 3"
        })
        @DisplayName("Should create person with valid names")
        void testCreatPersonWithValidNames(String inputFirst, String inputLast,
                                           String expectedFist, String expectedLast, String expectedAddress) {
            Person person = Person.createPerson(inputFirst, inputLast, expectedAddress);

            assertNotNull(person,
                    () -> String.format("Expected person to be created with names '%s' and '%s'", inputFirst, inputLast));
            assertEquals(expectedFist, person.getFirstName(),
                    () -> String.format("Expected firstName to be '%s' but was '%s'", expectedFist, person.getFirstName()));
            assertEquals(expectedLast, person.getLastName(),
                    () -> String.format("Expected lastName to be '%s' but was '%s'", expectedLast, person.getLastName()));
            assertEquals(expectedAddress, person.getAddress(),
                    () -> String.format("Expected address to be '%s' but was '%s'", expectedAddress, person.getAddress()));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Person person = new Person("John", "Doe", "St. Central 1");
            String expectedString = "Person{firstName='John', lastName='Doe', address='St. Central 1'}";

            assertEquals(expectedString, person.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, person.toString()));
        }

        @Test
        @DisplayName("Should format toString correctly with null fields")
        void testToStringWithNullFields() {
            Person person = new Person();
            String expectedString = "Person{firstName='null', lastName='null', address='null'}";

            assertEquals(expectedString, person.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, person.toString()));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Person person = new Person("John", "Doe", "St. Central 1");

            assertTrue(person.equals(person), "Person should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to person with same data")
        void testEqualsSymmetric() {
            Person person1 = new Person("John", "Doe", "St. Central 1");
            Person person2 = new Person("John", "Doe", "St. Central 1");

            assertTrue(person1.equals(person2),
                    "Persons with same data should be equal");
            assertTrue(person2.equals(person1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Person person = new Person("John", "Doe", "St. Central 1");

            assertFalse(person.equals(null), "Person should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Person person = new Person("John", "Doe", "St. Central 1");
            String notAPerson = "Not a person";

            assertFalse(person.equals(notAPerson), "Person should not be equal to different class");
        }

        @Test
        @DisplayName("Equal persons should have same hashCode")
        void testHashCodeConsistency() {
            Person person1 = new Person("John", "Doe", "St. Central 1");
            Person person2 = new Person("John", "Doe", "St. Central 1");

            assertTrue(person1.equals(person2), "Persons should be equal");
            assertEquals(person1.hashCode(), person2.hashCode(),
                    "Equal persons should have same hashCode");
        }
    }
}
