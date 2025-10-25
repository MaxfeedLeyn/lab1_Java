package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import java.util.*;
import java.util.logging.Logger;

public class CustomerRepository extends GenericRepository<Customer>{

    private static final Logger logger = Logger.getLogger(CustomerRepository.class.getName());


    public CustomerRepository() {
        super(Customer::getFullName, "Customer");
    }

    public List<Customer> sortByNameDesc() {
        List<Customer> customers = getAll();
        customers.sort((it1, it2) ->
            it2.getFirstName().compareTo(it1.getFirstName())
        );
        logger.info("Sorted CustomerRepository by name descending :" + customers.size() + " items");
        return customers;
    }

    public List<Customer> sortByLastNameAsc() {
        List<Customer> customers = getAll();
        customers.sort((it1, it2) ->
            it1.getLastName().compareTo(it2.getLastName())
        );
        logger.info("Sorted CustomerRepository by last name ascending :" + customers.size() + " items");
        return customers;
    }

    public List<Customer> sortByLastNameDesc() {
        List<Customer> customers = getAll();
        customers.sort((it1, it2) ->
                it2.getLastName().compareTo(it1.getLastName())
        );
        logger.info("Sorted CustomerRepository by last name descending :" + customers.size() + " items");
        return  customers;
    }

    public List<Customer> sortByAddressAsc() {
        List<Customer> customers = getAll();
        customers.sort((c1, c2) -> c1.getAddress().compareTo(c2.getAddress()));
        logger.info("Sorted CustomerRepository by address ascending :" + customers.size() + " items");
        return customers;
    }

    public List<Customer> sortByAddressDesc() {
        List<Customer> customers = getAll();
        customers.sort((c1, c2) -> c2.getAddress().compareTo(c1.getAddress()));
        logger.info("Sorted CustomerRepository by address descending :" + customers.size() + " items");
        return customers;
    }
}
