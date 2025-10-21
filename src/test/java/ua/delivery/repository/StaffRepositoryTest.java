package ua.delivery.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class StaffRepositoryTest {

    private StaffRepository staffRepository;
    private Staff staff1, staff2, staff3;
    private List<Staff> staffList;

    @BeforeAll
    void setUpTestData(){
        staff1 = new Staff("Jane", "Doe", "St. Central 1");
        staff2 = new Staff("Robert", "Polson", "St. Central 2");
        staff3 = new Staff("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        staffList = new ArrayList<>();
        staffList.add(staff2);
        staffList.add(staff1);
        staffList.add(staff3);
        staffRepository = new StaffRepository(staffList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        Collections.sort(staffList);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(staffList.get(0).getFirstName()).isEqualTo(staff1.getFirstName());
        softly.assertThat(staffList.get(1).getFirstName()).isEqualTo(staff2.getFirstName());
        softly.assertThat(staffList.get(2).getFirstName()).isEqualTo(staff3.getFirstName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("CustomerRepository Tests")
    class CustomerRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            staffRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            staffRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            staffRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffRepository.getItemsForTesting().get(0).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(1).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffRepository.getItemsForTesting().get(2).getFirstName()).isEqualTo(staff1.getFirstName());
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
            List<Staff> oneCustomer = new ArrayList<>();
            oneCustomer.add(staff1);
            StaffRepository oneCustomerRepo = new StaffRepository(oneCustomer);
            oneCustomerRepo.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneCustomerRepo.getItemsForTesting().get(0).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertAll();
        }
    }
}
