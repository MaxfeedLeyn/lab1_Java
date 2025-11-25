package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.AlreadyExistsException;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GenericRepository Tests")
public class GenericRepositoryOldTest {

    private GenericRepositoryOld<Customer> customerRepository;
    private Customer customer1, customer2, customer3;

    @BeforeAll
    void setUpTestData(){
        customer1 = new Customer("Jane", "Doe", "St. Central 1");
        customer2 = new Customer("Robert", "Polson", "St. Central 2");
        customer3 = new Customer("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        customerRepository = new GenericRepositoryOld<>(Customer::getFullName, "Customer");
        customerRepository.getItemsForTesting().add(customer1);
    }

    static Stream<Arguments> studentIdsProvider(){
        return Stream.of(
                Arguments.of("Jane Doe", true, "Valid customer fullName"),
                Arguments.of("Robert Polson", true, "Another valid fullName"),
                Arguments.of("Janny Stevenson", false, "Non-existing customer"),
                Arguments.of("Invalid", false, "Invalid format fullName"),
                Arguments.of("", false, "Empty string"),
                Arguments.of(null, false, "Null string")
        );
    }

    static Stream<Arguments> invalidDataProvider(){
        return Stream.of(
                Arguments.of(null, "null customer object"),
                Arguments.of("", "empty string"),
                Arguments.of("  ", "whitespace string")
        );
    }

    @DisplayName("Test adding valid customers")
    @Test
    void testAddValidCustomers(){
        SoftAssertions softly = new SoftAssertions();

        int initialSize = customerRepository.size();

        boolean added = customerRepository.add(customer2);

        softly.assertThat(added)
                .as("Should successfully add valid customers %s", customer2.getFullName())
                .isTrue();
        softly.assertThat(customerRepository.size())
                .as("Repository size should increase by 1")
                .isEqualTo(initialSize + 1);
        softly.assertAll();
    }

    @DisplayName("Test getting existing customer customer1")
    @Test
    void testFoundCustomer(){
        SoftAssertions softly = new SoftAssertions();

        String fullName = customer1.getFullName();

        Optional<Customer> found = customerRepository.findByIdentity(fullName);
        softly.assertThat(found.isPresent())
                .as("Should found added customer %s", fullName)
                .isTrue();

        if (found.isPresent()){
            Customer foundCustomer = found.get();
            softly.assertThat(foundCustomer.getFirstName())
                    .as("Customer should equal first name")
                    .isEqualTo(customer1.getFirstName());
            softly.assertThat(foundCustomer.getLastName())
                    .as("Customer should equal last name")
                    .isEqualTo(customer1.getLastName());
            softly.assertThat(foundCustomer.getAddress())
                    .as("Customer address should equal address")
                    .isEqualTo(customer1.getAddress());
        }

        softly.assertAll();
    }

    @Test
    @DisplayName("Test duplicate prevention")
    void testDuplicatePrevention(){
        SoftAssertions softly = new SoftAssertions();

        Customer tmpCustomer = new Customer("Jane", "Doe", "St. Central 1");
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            boolean added = customerRepository.add(tmpCustomer);
        });

        assertTrue(exception.getMessage().contains("already exists"));

        softly.assertThat(customerRepository.size())
                .as("Repository size should increase by 1")
                .isEqualTo(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("Test null adding prevention")
    void testNullPrevention(){
        SoftAssertions softly = new SoftAssertions();

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            boolean added = customerRepository.add(null);
        });

        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @ParameterizedTest
    @MethodSource("invalidDataProvider")
    @DisplayName("Test adding invalid data")
    void testAddInvalidData(String fullNamed){}

    @ParameterizedTest(name = "Find by fullName: {0} (should find: {1} - {2})")
    @MethodSource("studentIdsProvider")
    @DisplayName("Test finding customers by fullName")
    void testFindByFullName(String fullName, boolean resultOption, String description){
        SoftAssertions softly = new SoftAssertions();

        customerRepository.getItemsForTesting().add(customer2);

        Optional<Customer> result = customerRepository.findByIdentity(fullName);

        softly.assertThat(result.isPresent())
                .as("Find result for %s should be %s", description, resultOption ? "present" : "absent")
                .isEqualTo(resultOption);
        if (resultOption && result.isPresent()){
            softly.assertThat(result.get().getFullName())
                    .as("Customer should have correct fullName")
                    .isEqualTo(fullName);
        }

        softly.assertAll();
    }

    @Test
    @DisplayName("Test getAll operation")
    void testGetAll(){
        SoftAssertions softly = new SoftAssertions();

        GenericRepositoryOld<Customer> empty = new GenericRepositoryOld<>(Customer::getFullName, "Customer");
        List<Customer> emptyList = empty.getAll();
        softly.assertThat(emptyList)
                .as("Customer list should be empty")
                .isEmpty();

        customerRepository.getItemsForTesting().add(customer2);
        customerRepository.getItemsForTesting().add(customer3);

        List<Customer> customerList = customerRepository.getItemsForTesting();

        softly.assertThat(customerList)
                .as("Customer list should return all added students")
                .hasSize(3)
                .contains(customer1, customer2, customer3);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing customers by identity")
    void testRemoveByIdentity(){
        SoftAssertions softly = new SoftAssertions();
        int initialSize =  customerRepository.size();

        boolean removed  = customerRepository.removeByIdentity(customer1.getFullName());

        softly.assertThat(removed)
                .as("Should successfully remove customer %s", customer1.getFullName())
                .isTrue();
        softly.assertThat(customerRepository.size())
                .as("Repository size should decrease by 1")
                .isEqualTo(initialSize - 1);
        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing non-existed customer")
    void testRemoveNonExisted(){
        SoftAssertions softly = new SoftAssertions();

        int initialSize =  customerRepository.size();

        boolean removed  = customerRepository.removeByIdentity("Jennifer Lopez");

        softly.assertThat(removed)
                .as("Should not remove non-existed customer")
                .isFalse();

        softly.assertThat(customerRepository.size())
                .as("Repository size should remain unchanged")
                .isEqualTo(initialSize);
        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing with null identity")
    void testRemoveNull(){
        SoftAssertions softly = new SoftAssertions();

        int initialSize =  customerRepository.size();

        boolean removed  = customerRepository.removeByIdentity(null);

        softly.assertThat(removed)
                .as("Should not remove with null identity")
                .isFalse();

        softly.assertThat(customerRepository.size())
                .as("Repository size remain unchanged")
                .isEqualTo(initialSize);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test clear operation")
    void testClear(){
        SoftAssertions softly = new SoftAssertions();

        customerRepository.add(customer2);
        customerRepository.add(customer3);

        softly.assertThat(customerRepository.size())
                .as("Repository size have 3 customers before clearing")
                .isEqualTo(3);

        customerRepository.clear();

        softly.assertThat(customerRepository.size())
                .as("Repository size should be 0")
                .isEqualTo(0);

        softly.assertThat(customerRepository.isEmpty())
                .as("Repository should be empty after clear")
                .isTrue();
        softly.assertThat(customerRepository.getAll())
                .as("GetAll should return empty list after clear")
                .isEmpty();
        softly.assertAll();
    }

    @Test
    @DisplayName("sort Tests asc")
    void testSort(){
        customerRepository.add(customer2);
        customerRepository.add(customer3);
        customerRepository.sortByIdentity("asc");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(customerRepository.getItemsForTesting().get(0)).isEqualTo(customer1);
        softly.assertThat(customerRepository.getItemsForTesting().get(1)).isEqualTo(customer2);
        softly.assertThat(customerRepository.getItemsForTesting().get(2)).isEqualTo(customer3);
        softly.assertAll();
    }

    @Test
    @DisplayName("sort Test desc")
    void testSortDesc(){
        customerRepository.add(customer2);
        customerRepository.add(customer3);
        customerRepository.sortByIdentity("desc");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(customerRepository.getItemsForTesting().get(0)).isEqualTo(customer3);
        softly.assertThat(customerRepository.getItemsForTesting().get(1)).isEqualTo(customer2);
        softly.assertThat(customerRepository.getItemsForTesting().get(2)).isEqualTo(customer1);
        softly.assertAll();
    }
}
