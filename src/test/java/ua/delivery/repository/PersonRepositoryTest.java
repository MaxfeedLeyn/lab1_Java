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
        personRepository = new PersonRepository();
        personRepository.add(person2);
        personRepository.add(person1);
        personRepository.add(person3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        List<Person> people = personRepository.getAll();
        Collections.sort(people);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(people.get(0).getFirstName()).isEqualTo(person1.getFirstName());
        softly.assertThat(people.get(1).getFirstName()).isEqualTo(person2.getFirstName());
        softly.assertThat(people.get(2).getFirstName()).isEqualTo(person3.getFirstName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("CustomerRepository Tests")
    class CustomerRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            List<Person> people = personRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(people.get(0).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(people.get(1).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertThat(people.get(2).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            List<Person> people = personRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(people.get(0).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertThat(people.get(1).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(people.get(2).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            List<Person> people = personRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(people.get(0).getFirstName()).isEqualTo(person2.getFirstName());
            softly.assertThat(people.get(1).getFirstName()).isEqualTo(person3.getFirstName());
            softly.assertThat(people.get(2).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            PersonRepository oneElement = new PersonRepository();
            oneElement.add(person1);
            List<Person> people = oneElement.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(people.get(0).getFirstName()).isEqualTo(person1.getFirstName());
            softly.assertAll();
        }
    }
}
