package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import java.util.*;
import java.util.logging.Logger;

public class CustomerRepository{

    private static final Logger logger = Logger.getLogger(CustomerRepository.class.getName());

    private static List<Customer> items;

    public CustomerRepository(List<Customer> items) {
        if (items == null) {
            throw new InvalidDataException("Can not be null");
        }
        CustomerRepository.items = new ArrayList<>(items);
        logger.info("Created CustomerRepository with: " + CustomerRepository.items.size() + " items");
    }

    public void sortByNameDesc() {
        items.sort((it1, it2) ->
            it2.getFirstName().compareTo(it1.getFirstName())
        );
        logger.info("Sorted CustomerRepository by name descending :" + CustomerRepository.items.size() + " items");
    }

    public void sortByLastNameAsc() {
        items.sort((it1, it2) ->
            it1.getLastName().compareTo(it2.getLastName())
        );
        logger.info("Sorted CustomerRepository by last name ascending :" + CustomerRepository.items.size() + " items");
    }

    List<Customer> getItemsForTesting() {
        return items;
    }

    public void sortByLastNameDesc() {
        items.sort((it1, it2) ->
                it2.getLastName().compareTo(it1.getLastName())
        );
        logger.info("Sorted CustomerRepository by last name descending :" + CustomerRepository.items.size() + " items");
    }

    public void sortByAddressAsc() {
        items.sort((c1, c2) -> c1.getAddress().compareTo(c2.getAddress()));
        logger.info("Sorted CustomerRepository by address ascending :" + CustomerRepository.items.size() + " items");
    }

    public void sortByAddressDesc() {
        items.sort((c1, c2) -> c2.getAddress().compareTo(c1.getAddress()));
        logger.info("Sorted CustomerRepository by address descending :" + CustomerRepository.items.size() + " items");
    }
}
