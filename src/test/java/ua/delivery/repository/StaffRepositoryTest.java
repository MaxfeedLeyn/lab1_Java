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

    @BeforeAll
    void setUpTestData(){
        staff1 = new Staff("Jane", "Doe", "St. Central 1");
        staff2 = new Staff("Robert", "Polson", "St. Central 2");
        staff3 = new Staff("Steven", "King", "St. Central 3");
    }

    @BeforeEach
    void setUp(){
        staffRepository = new StaffRepository();
        staffRepository.add(staff2);
        staffRepository.add(staff1);
        staffRepository.add(staff3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        List<Staff> staffList = staffRepository.getAll();
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
            List<Staff> staffList = staffRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffList.get(0).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffList.get(1).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertThat(staffList.get(2).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameAsc")
        void sortByLastNameAscTest(){
            List<Staff> staffList = staffRepository.sortByLastNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffList.get(0).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertThat(staffList.get(1).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffList.get(2).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLastNameDesc")
        void sortByLastNameDescTest(){
            List<Staff> staffList = staffRepository.sortByLastNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(staffList.get(0).getFirstName()).isEqualTo(staff2.getFirstName());
            softly.assertThat(staffList.get(1).getFirstName()).isEqualTo(staff3.getFirstName());
            softly.assertThat(staffList.get(2).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            StaffRepository oneStaff = new StaffRepository();
            oneStaff.add(staff1);
            List<Staff> oneElement = oneStaff.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneElement.get(0).getFirstName()).isEqualTo(staff1.getFirstName());
            softly.assertAll();
        }
    }
}
