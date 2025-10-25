package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;
import ua.delivery.model.Order;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class OrderRepository extends GenericRepository<Order>{

    private static final Logger logger = Logger.getLogger(CustomerRepository.class.getName());

    public OrderRepository() {
        super(order -> String.valueOf(order.getOrderDate()), "order");
    }

    public List<Order> sortByCustomerNameAsc(){
        List<Order> orders = getAll();
        orders.sort((o1, o2) ->
                o1.getCustomer().getFullName().compareTo(o2.getCustomer().getFullName()));
        logger.info("Sorted OrderRepository by name ascending :" + orders.size() + " items");
        return orders;
    }

    public List<Order> sortByCustomerNameDesc(){
        List<Order> orders = getAll();
        orders.sort((o1, o2) ->
                o2.getCustomer().getFullName().compareTo(o1.getCustomer().getFullName()));
        logger.info("Sorted OrderRepository by name descending :" + orders.size() + " items");
        return orders;
    }

    public List<Order> sortByDateDesc(){
        List<Order> orders = getAll();
        orders.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        logger.info("Sorted OrderRepository by date descending :" + orders.size() + " items");
        return orders;
    }

}
