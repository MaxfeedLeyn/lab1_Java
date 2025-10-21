package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class CustomerRepositoryTest {

    private CustomerRepository customerRepository;
    private Customer customer1, customer2, customer3;
    private List<Customer> customerList;

    @BeforeAll
    void setUpTestData(){
        customer1 = new Customer("Jane", "Doe", "St. Central 1");
        customer2 = new Customer("Robert", "Polson", "St. Central 2");
        customer3 = new Customer("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        customerList = new ArrayList<>();
        customerList.add(customer2);
        customerList.add(customer1);
        customerList.add(customer3);
        customerRepository = new CustomerRepository(customerList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        Collections.sort(customerList);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(customerList.get(0).getFullName()).isEqualTo(customer1.getFullName());
        softly.assertThat(customerList.get(1).getFullName()).isEqualTo(customer2.getFullName());
        softly.assertThat(customerList.get(2).getFullName()).isEqualTo(customer3.getFullName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("CustomerRepository Tests")
    class CustomerRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            customerRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customerRepository.getItemsForTesting().get(0).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            customerRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customerRepository.getItemsForTesting().get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(1).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(2).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            customerRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customerRepository.getItemsForTesting().get(0).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(1).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByAddressAsc")
        void sortByAddressAscTest(){
            customerRepository.sortByAddressAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customerRepository.getItemsForTesting().get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(2).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByAddressDesc")
        void sortByAddressDescTest(){
            customerRepository.sortByAddressDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customerRepository.getItemsForTesting().get(0).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customerRepository.getItemsForTesting().get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sort null check")
        void sortNullCheckTest(){
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                CustomerRepository nullCustomerRepository = new CustomerRepository(null);
            });
            assertTrue(exception.getMessage().contains("Can not be null"));
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            List<Customer> oneCustomer = new ArrayList<>();
            oneCustomer.add(customer1);
            CustomerRepository oneCustomerRepo = new CustomerRepository(oneCustomer);
            oneCustomerRepo.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneCustomerRepo.getItemsForTesting().get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }
    }
}
