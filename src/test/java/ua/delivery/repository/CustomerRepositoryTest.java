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

    @BeforeAll
    void setUpTestData(){
        customer1 = new Customer("Jane", "Doe", "St. Central 1");
        customer2 = new Customer("Robert", "Polson", "St. Central 2");
        customer3 = new Customer("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        customerRepository = new CustomerRepository();
        customerRepository.add(customer2);
        customerRepository.add(customer1);
        customerRepository.add(customer3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        List<Customer> customers = new ArrayList<>();
        customers = customerRepository.getAll();
        Collections.sort(customers);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer1.getFullName());
        softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer2.getFullName());
        softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer3.getFullName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("CustomerRepository Tests")
    class CustomerRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            List<Customer> customers = customerRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            List<Customer> customers = customerRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            List<Customer> customers = customerRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByAddressAsc")
        void sortByAddressAscTest(){
            List<Customer> customers = customerRepository.sortByAddressAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByAddressDesc")
        void sortByAddressDescTest(){
            List<Customer> customers = customerRepository.sortByAddressDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertThat(customers.get(1).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customers.get(2).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            CustomerRepository oneCustomerRepo = new CustomerRepository();
            oneCustomerRepo.add(customer1);
            List<Customer> customers = oneCustomerRepo.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer1.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("findByFirstName")
        void findByFirstNameTest(){
            List<Customer> customers = customerRepository.findByFirstName("Steven");
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer3.getFullName());
            softly.assertAll();
        }

        @Test
        @DisplayName("findByLastName")
        void findByLastNameTest(){
            List<Customer> customers = customerRepository.findByLastName("Polson");
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertAll();
        }
    }
}
