package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class PersonRepositoryTest {

    private PersonRepository personRepository;
    private Person person1, person2, person3;
    private List<Person> personList;

    @BeforeAll
    void setUpTestData(){
        person1 = new Person("Jane", "Doe", "St. Central 1");
        person2 = new Person("Robert", "Polson", "St. Central 2");
        person3 = new Person("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        personList = new ArrayList<>();
        personList.add(person2);
        personList.add(person1);
        personList.add(person3);
        personRepository = new PersonRepository(personList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        Collections.sort(personList);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(personList.get(0).getFirstName()).isEqualTo(person1.getFirstName());
        softly.assertThat(personList.get(1).getFirstName()).isEqualTo(person2.getFirstName());
        softly.assertThat(personList.get(2).getFirstName()).isEqualTo(person3.getFirstName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("CustomerRepository Tests")
    class CustomerRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            personRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(personRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            personRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(personRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            personRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(personRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(personRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(person1.getFirstName());
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
            List<Person> oneCustomer = new ArrayList<>();
            oneCustomer.add(person1);
            PersonRepository oneCustomerRepo = new PersonRepository(oneCustomer);
            oneCustomerRepo.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneCustomerRepo.getItemsForTesting().get(0).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertAll();
        }
    }
}
