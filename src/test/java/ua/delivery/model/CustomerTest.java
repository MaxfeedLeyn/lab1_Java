package ua.delivery.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import ua.delivery.exception.InvalidDataException;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;
import ua.delivery.parser.CustomerFileParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @TempDir
    Path tempDir;

    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("groups.csv");
    }

    @ParameterizedTest
    @CsvSource({
            "Mark,Tsukenberg,St. Center 1",
            "Jeffrey,Bezos,St. Central 2",
            "Bill, Gates, St. Central 3"
    })
    void testValidCustomerCreation(String firstName, String lastName, String address){
        SoftAssertions softly = new SoftAssertions();

        assertDoesNotThrow(() -> {
            Customer customer = new Customer(firstName, lastName, address);
            softly.assertThat(customer.getFirstName()).isEqualTo(firstName);
            softly.assertThat(customer.getLastName()).isEqualTo(lastName);
            softly.assertThat(customer.getAddress()).isEqualTo(address);
        });
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "AA", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"})
    void testInvalidCustomerName(String name){
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
           new  Customer(name, "ValidLast", "St. Center 1");
        });
        assertTrue(exception.getMessage().contains("First name is invalid"));
    }

    private static Stream<Arguments> provideValidCSVTestData() {
        return Stream.of(
                Arguments.of("Mark,Tsukenberg,St. Center 1\nSable,Ward,St. Center 4", 2, "Mark", "St. Center 1"),
                Arguments.of("Jeffrey,Bezos,St. Central 2", 1, "Jeffrey",  "St. Central 2"),
                Arguments.of("Bill, Gates, St. Central 3", 1, "Bill", "St. Central 3")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidCSVTestData")
    void testParseValidCSVFile(String csvContent, int expectedSize, String firstName, String firstAddress) throws IOException, InvalidDataException {
        Files.writeString(testFile, csvContent);

        List<Customer> customers = CustomerFileParser.readCustomers(testFile.toString());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(customers).hasSize(expectedSize);
        if (expectedSize > 0) {
            softly.assertThat(customers.get(0).getFirstName()).isEqualTo(firstName);
            softly.assertThat(customers.get(0).getAddress()).isEqualTo(firstAddress);
        }
        softly.assertAll();
    }

    private static Stream<Arguments> provideInvalidCSVTestData() {
        return Stream.of(
                Arguments.of("Mark,Tsukenberg,St. Center 1\nInvalid Line\nSable,Ward,St. Center 4", 2),
                Arguments.of("Je,Bezos,St. Central 2\nJeffrey,Bezos,St. Central 2", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCSVTestData")
    void testParseInvalidCSVFile(String csvContent, int expectedValidCustomers) throws IOException {
        Files.writeString(testFile, csvContent);

        assertDoesNotThrow(() -> {
            List<Customer> customers = CustomerFileParser.readCustomers(testFile.toString());
            assertEquals(expectedValidCustomers, customers.size());
        });
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create person with null fields")
        void testConstructor() {
            Customer customer = new Customer();

            assertEquals("null", customer.getFirstName(), "Expected first name to be null after default constructor");
            assertEquals("null", customer.getLastName(), "Expected last name to be null after default constructor");
            assertEquals("St. Null 1", customer.getAddress(), "Expected address to be null after default constructor");
        }

        @Test
        @DisplayName("Constructor should not set invalid fields")
        void testConstructorWithInvalidData() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Customer("", "validLastName", "St. validAddress 1");
            });
            assertTrue(exception.getMessage().contains("First name is invalid"));
        }
    }

    @Test
    void testFileNotFound() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            CustomerFileParser.readCustomers("nonexistent.csv");
        });
        assertTrue(exception.getMessage().contains("Error reading file"));
    }

    @Nested
    @DisplayName("FirstName Tests")
    class FirstNameTests {

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        @DisplayName("Should not set invalid first names - empty and whitespace")
        void testInvalidFirstNames(String invalidName) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
                Customer customer = new Customer(invalidName, "ValidLastName", "St. Center 1");
            });

            assertTrue(exception.getMessage().contains("First name is invalid"));
        }

        @Test
        @DisplayName("Should not set first name that exceeds maximum length")
        void testSetFirstNameTooLong(){
            String tooLongString = "A".repeat(51);
            InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
                Customer customer = new Customer(tooLongString, "ValidLast", "St. Center 2");
            });

            assertTrue(exception.getMessage().contains("First name is invalid"));
        }

        @Test
        @DisplayName("Should not set null first name")
        void testSetNullFirstName() {
            Person person = new Person();
            person.setFirstName(null);

            assertNull(person.getFirstName(), "Expected first name to remain null, when null is provided");
        }
    }

}
