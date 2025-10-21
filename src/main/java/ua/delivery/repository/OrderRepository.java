package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;
import ua.delivery.model.Order;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class OrderRepository {

    private static final Logger logger = Logger.getLogger(CustomerRepository.class.getName());

    private static List<Order> items;

    public OrderRepository(List<Order> items) {
        if (items == null) {
            throw new InvalidDataException("Can not be null");
        }
        OrderRepository.items = items;
        logger.info("Created OrderRepository with " + items.size() + " items");
    }

    public void sortByCustomerNameAsc(){
        items.sort((o1, o2) ->
                o1.getCustomer().getFullName().compareTo(o2.getCustomer().getFullName()));
        logger.info("Sorted OrderRepository by name ascending :" + OrderRepository.items.size() + " items");
    }

    public void sortByCustomerNameDesc(){
        items.sort((o1, o2) ->
                o2.getCustomer().getFullName().compareTo(o1.getCustomer().getFullName()));
        logger.info("Sorted OrderRepository by name descending :" + OrderRepository.items.size() + " items");
    }

    public void sortByDateDesc(){
        items.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        logger.info("Sorted OrderRepository by date descending :" + OrderRepository.items.size() + " items");
    }

    List<Order> getItemsForTesting() {
        return items;
    }
}
