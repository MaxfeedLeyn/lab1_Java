package ua.delivery.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.delivery.model.Customer;
import ua.delivery.model.MenuItem;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericRepositoryThreadSafetyTest {

    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CustomerRepository();
    }

    @Test
    void addAll_ShouldAddMultipleItems() {
        List<Customer> menuItemList = List.of(
                new Customer("Jone", "Doe", "St. Central 1"),
                new Customer("Jone", "Dumbass", "St. Central 2"),
                new Customer("Jone", "Lol", "St. Central 3")
        );

        int added = repository.addAll(menuItemList);

        assertEquals(3, added);
        assertEquals(3, repository.size());
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }


    @Test
    void addAll_WithDuplicates_ShouldSkipDuplicates() {
        repository.add(new Customer("Sushi", "LOL", "St. Central 1"));

        List<Customer> customers = List.of(
                new Customer("Sushi", "LOL", "St. Central 1"), // дублікат
                new Customer("New", "Customer",  "St. Central 2")
        );

        int added = repository.addAll(customers);

        assertEquals(1, added); // тільки Математика
        assertEquals(2, repository.size());
    }
}