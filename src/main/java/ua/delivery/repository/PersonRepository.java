package ua.delivery.repository;


import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Order;
import ua.delivery.model.Person;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PersonRepository {

    private static final Logger logger = Logger.getLogger(PersonRepository.class.getName());

    private static List<Person> items;

    public PersonRepository(List<Person> items) {
        if (items == null) {
            throw new InvalidDataException("Can not be null");
        }
        PersonRepository.items = items;
        logger.info("Created PersonRepository with " + items.size() + " items");
    }

    public void sortByNameDesc(){
        items.sort((p1, p2) -> p2.getFirstName().compareTo(p1.getFirstName()));
        logger.info("Sorted PersonRepository by name descending :" + PersonRepository.items.size() + " items");
    }

    public void sortByLastNameAsc(){
        items.sort((c1, c2) -> c1.getLastName().compareTo(c2.getLastName()));
        logger.info("Sorted PersonRepository by name ascending :" + PersonRepository.items.size() + " items");
    }

    public void sortByLastNameDesc(){
        items.sort((p1, p2) -> p2.getLastName().compareTo(p1.getLastName()));
        logger.info("Sorted PersonRepository by name descending :" + PersonRepository.items.size() + " items");
    }

    List<Person> getItemsForTesting() {
        return items;
    }
}
