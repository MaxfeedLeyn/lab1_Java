package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.*;
import ua.delivery.model.Order;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class DeliveryRepositoryTest {

    private Delivery delivery1, delivery2, delivery3;
    private Customer customer1, customer2, customer3;
    private MenuItem menuItem1, menuItem2, menuItem3;
    private Order order1, order2, order3;
    private Staff staff1, staff2, staff3;
    private DeliveryRepository deliveryRepository;

    @BeforeAll
    public void setUpTestData() {
        Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance(), cal3 = Calendar.getInstance();
        cal1.set(2025, Calendar.NOVEMBER, 10);
        cal2.set(2025, Calendar.NOVEMBER, 11);
        cal3.set(2025, Calendar.NOVEMBER, 12);
        Date orderDate1 = cal1.getTime(), orderDate2 = cal2.getTime(),  orderDate3 = cal3.getTime();
        customer1 = new Customer("Jane", "Doe", "St. Central 1");
        customer2 = new Customer("Robert", "Polson", "St. Central 2");
        customer3 = new Customer("Steven", "King", "St. Central 3");
        menuItem1 = new MenuItem("Pasta", 15.5f, "Italian");
        menuItem2 = new MenuItem("Burger", 21.5f, "American");
        menuItem3 = new MenuItem("Taco", 10f, "Mexican");

        order1 = new Order(customer1, new MenuItem[]{menuItem1}, orderDate1);
        order2 = new Order(customer2, new MenuItem[]{menuItem2}, orderDate2);
        order3 = new Order(customer3, new MenuItem[]{menuItem3}, orderDate3);

        staff1 = new Staff("Jane", "Doe", "St. Central 1");
        staff2 = new Staff("Robert", "Polson", "St. Central 2");
        staff3 = new Staff("Steven", "King", "St. Central 3");

        delivery1 = new Delivery(order1, staff1, orderDate1);
        delivery2 = new Delivery(order2, staff2, orderDate2);
        delivery3 = new Delivery(order3, staff3, orderDate3);
    }

    @BeforeEach
    public void setUp() {
        deliveryRepository = new DeliveryRepository();
        deliveryRepository.add(delivery1);
        deliveryRepository.add(delivery2);
        deliveryRepository.add(delivery3);
    }

    @Test
    @DisplayName("Default Sort Test")
    public void sortDefaultTest() {
        List<Delivery> deliveries = deliveryRepository.getAll();
        Collections.sort(deliveries);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(deliveries.get(0).getOrder()).isEqualTo(order1);
        softly.assertThat(deliveries.get(1).getOrder()).isEqualTo(order2);
        softly.assertThat(deliveries.get(2).getOrder()).isEqualTo(order3);
        softly.assertAll();
    }

    @Nested
    @DisplayName("DeliveryRepository Tests")
    class DeliveryRepositoryTests {

        @Test
        @DisplayName("sortByCustomerNameAsc Test")
        void sortByCustomerNameAscTest() {
            List<Delivery> deliveries = deliveryRepository.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(deliveries.get(0)).isEqualTo(delivery1);
            softly.assertThat(deliveries.get(1)).isEqualTo(delivery2);
            softly.assertThat(deliveries.get(2)).isEqualTo(delivery3);
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByCustomerNameDesc Test")
        void sortByCustomerNameDescTest() {
            List<Delivery> deliveries = deliveryRepository.sortByCustomerNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(deliveries.get(0)).isEqualTo(delivery3);
            softly.assertThat(deliveries.get(1)).isEqualTo(delivery2);
            softly.assertThat(deliveries.get(2)).isEqualTo(delivery1);
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByDateDesc Test")
        void sortByDateDescTest(){
            List<Delivery> deliveries = deliveryRepository.sortByDateDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(deliveries.get(0)).isEqualTo(delivery3);
            softly.assertThat(deliveries.get(1)).isEqualTo(delivery2);
            softly.assertThat(deliveries.get(2)).isEqualTo(delivery1);
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByStaffAsc Test")
        void sortByStaffAscTest() {
            List<Delivery> deliveries = deliveryRepository.sortByStaffAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(deliveries.get(0)).isEqualTo(delivery1);
            softly.assertThat(deliveries.get(1)).isEqualTo(delivery2);
            softly.assertThat(deliveries.get(2)).isEqualTo(delivery3);
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByStaffDesc Test")
        void sortByStaffDescTest() {
            List<Delivery> deliveries = deliveryRepository.sortByStaffDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(deliveries.get(0)).isEqualTo(delivery3);
            softly.assertThat(deliveries.get(1)).isEqualTo(delivery2);
            softly.assertThat(deliveries.get(2)).isEqualTo(delivery1);
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort only one element Test")
        void sortOnlyOneElementTest() {
            DeliveryRepository deliveryRepository1 = new DeliveryRepository();
            deliveryRepository1.add(delivery1);
            List<Delivery> oneElement = deliveryRepository1.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneElement.get(0)).isEqualTo(delivery1);
            softly.assertAll();
        }
    }
}
