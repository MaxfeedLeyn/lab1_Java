package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import ua.delivery.model.Order;
import ua.delivery.model.MenuItem;
import ua.delivery.model.Restaurant;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private Order order1, order2, order3;
    private Customer customer1, customer2, customer3;
    private MenuItem menuItem1, menuItem2, menuItem3;
    private List<Order> orderList;

    @BeforeAll
    public void setupTestData() {
        Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance(), cal3 = Calendar.getInstance();
        cal1.set(2025, Calendar.OCTOBER, 10);
        cal2.set(2025, Calendar.OCTOBER, 11);
        cal3.set(2025, Calendar.OCTOBER, 12);
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
    }

    @BeforeEach
    public void setup() {
        orderRepository = new OrderRepository();
        orderRepository.add(order1);
        orderRepository.add(order2);
        orderRepository.add(order3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest() {
        List<Order> orders = orderRepository.getAll();
        Collections.sort(orders);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orders.get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
        softly.assertThat(orders.get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
        softly.assertThat(orders.get(2).getOrderDate()).isEqualTo(order3.getOrderDate());
        softly.assertAll();
    }

    @Nested
    @DisplayName("OrderRepository Tests")
    class orderRepositoryTests {

        @Test
        @DisplayName("sortByCustomerNameAsc Test")
        void sortByCustomerNameAscTest() {
            List<Order> orders = orderRepository.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orders.get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertThat(orders.get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orders.get(2).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByCustomerNameDesc Test")
        void sortByCustomerNameDescTest() {
            List<Order> orders = orderRepository.sortByCustomerNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orders.get(0).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertThat(orders.get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orders.get(2).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByDateDesc Test")
        void sortByDateDescTest() {
            List<Order> orders = orderRepository.sortByDateDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orders.get(0).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertThat(orders.get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orders.get(2).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            OrderRepository oneElement = new OrderRepository();
            oneElement.add(order1);
            List<Order> orders = oneElement.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orders.get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("findCustomerOrderedSumBiggerThan")
        void findCustomerOrderedSumBiggerThanTest(){
            List<Customer> customers = orderRepository.findCustomerOrderedSumBiggerThan(16f);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(customers.get(0).getFullName()).isEqualTo(customer2.getFullName());
            softly.assertThat(customers).hasSize(1);
            softly.assertAll();
        }
    }
}
