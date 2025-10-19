package ua.delivery.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create person with null fields")
        void testConstructor() {
            Order order = new Order();

            assertNull(order.getCustomer(), "Expected Customer to be null after default constructor");
            assertNull(order.getMenuItems(), "Expected menuItems to be null after default constructor");
            assertNull(order.getOrderDate(), "Expected address to be null after default constructor");
        }

        @Test
        @DisplayName("Constructor should not set invalid fields")
        void testConstructorWithInvalidData() {
            Customer customer = new Customer("Robert", "Polson", "St. Central 1");
            Order order = new Order(customer, null, null);

            assertNull(order.getMenuItems(), "Expected menuItems to be null when invalid name provided");
            assertEquals(customer, order.getCustomer(), "Expected customer to be set when valid");
            assertNull(order.getOrderDate(), "Expected date to be null when invalid address provided");
        }
    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {

        @ParameterizedTest
        @ValueSource(strings = {"customer", "menuItems", "orderDate"})
        @DisplayName("Fields should be private")
        void testFieldsAreProtected(String fieldName) throws NoSuchFieldException {
            var field = Order.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    () -> String.format("Expected field %s to be private, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }
    }

    @Nested
    @DisplayName("createOrder Static Method Tests")
    class CreateOrderTests {

        @Test
        @DisplayName("Should create order with valid values")
        void testCreateOrderWithValidValues() {
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.SEPTEMBER, 29);
            Date orderDate = cal.getTime();
            Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
            MenuItem[] menuItems = new MenuItem[2];
            menuItems[0] = new MenuItem("Pizza", 10, "Italic");
            menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
            Order order = Order.createOrder(customer, menuItems, orderDate);
            assertEquals(customer, order.getCustomer(),
                    ()->String.format("Expected customer to be set when valid: %s, but was: %s",
                            customer, order.getCustomer()));
            assertEquals(menuItems[1], order.getMenuItems()[1],
                    ()->String.format("Expected menuItems to be set when valid: %s, but was: %s",
                            menuItems[1], order.getMenuItems()[1]));
            assertEquals(orderDate, order.getOrderDate(),
                    ()->String.format("Expected orderDate to be set when valid: %s, but was: %s",
                            orderDate, order.getOrderDate()));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.SEPTEMBER, 29);
            Date orderDate = cal.getTime();
            Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
            MenuItem[] menuItems = new MenuItem[2];
            menuItems[0] = new MenuItem("Pizza", 10, "Italic");
            menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
            Order order = Order.createOrder(customer, menuItems, orderDate);

            String exepecteString = "Order{Customer='Customer{firstName='Alex', lastName='Vasilenko', address='St. Center 13}', MenuItems='[Cuisine{Name='Pizza', Price='10.0', Category='Italic'}, Cuisine{Name='Burger', Price='20.0', Category='American'}]', Date='Mon Sep 29}";

            assertEquals(exepecteString, order.toString().substring(0, exepecteString.length()-1) + '}',
                    ()->String.format("Expected toString correctly: %s, but was %s",
                            exepecteString, order.toString().substring(0, exepecteString.length()-1) + '}'));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        private Date orderDate;
        private Customer customer;
        private MenuItem[] menuItems;

        private EqualsAndHashCodeTests(){
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.SEPTEMBER, 29);
            orderDate = cal.getTime();
            customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
            menuItems = new MenuItem[2];
            menuItems[0] = new MenuItem("Pizza", 10, "Italic");
            menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
        }

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Order order = Order.createOrder(customer, menuItems, orderDate);

            assertTrue(order.equals(order), "Order should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to person with same data")
        void testEqualsSymmetric() {
            Order order1 = Order.createOrder(customer, menuItems, orderDate);
            Order order2 = Order.createOrder(customer, menuItems, orderDate);

            assertTrue(order1.equals(order2),
                    "Orders with same data should be equal");
            assertTrue(order2.equals(order1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Person person = new Person("John", "Doe", "St. Central 1");

            assertFalse(person.equals(null), "Order should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Order order = Order.createOrder(customer, menuItems, orderDate);
            String notAPerson = "Not a Order";

            assertFalse(order.equals(notAPerson), "Order should not be equal to different class");
        }

        @Test
        @DisplayName("Equal persons should have same hashCode")
        void testHashCodeConsistency() {
            Order order1 = Order.createOrder(customer, menuItems, orderDate);
            Order order2 = Order.createOrder(customer, menuItems, orderDate);

            assertTrue(order1.equals(order2), "Orders should be equal");
            assertEquals(order1.hashCode(), order2.hashCode(),
                    "Equal Orders should have same hashCode");
        }
    }
}
