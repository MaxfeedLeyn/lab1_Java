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
        orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderRepository = new OrderRepository(orderList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest() {
        Collections.sort(orderList);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orderList.get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
        softly.assertThat(orderList.get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
        softly.assertThat(orderList.get(2).getOrderDate()).isEqualTo(order3.getOrderDate());
        softly.assertAll();
    }

    @Nested
    @DisplayName("OrderRepository Tests")
    class orderRepositoryTests {

        @Test
        @DisplayName("sortByCustomerNameAsc Test")
        void sortByCustomerNameAscTest() {
            orderRepository.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orderRepository.getItemsForTesting().get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(2).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByCustomerNameDesc Test")
        void sortByCustomerNameDescTest() {
            orderRepository.sortByCustomerNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orderRepository.getItemsForTesting().get(0).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(2).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByDateDesc Test")
        void sortByDateDescTest() {
            orderRepository.sortByDateDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(orderRepository.getItemsForTesting().get(0).getOrderDate()).isEqualTo(order3.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(1).getOrderDate()).isEqualTo(order2.getOrderDate());
            softly.assertThat(orderRepository.getItemsForTesting().get(2).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortNull Test")
        void sortNullTest() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                OrderRepository nullOrderRepository = new OrderRepository(null);
            });
            assertTrue(exception.getMessage().contains("Can not be null"));
        }

        @Test
        @DisplayName("Sort only one check")
        void sortOnlyOneCheckTest(){
            List<Order> oneOrder = new ArrayList<>();
            oneOrder.add(order1);
            OrderRepository oneOrderRepo = new OrderRepository(oneOrder);
            oneOrderRepo.sortByCustomerNameAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(oneOrderRepo.getItemsForTesting().get(0).getOrderDate()).isEqualTo(order1.getOrderDate());
            softly.assertAll();
        }
    }
}
