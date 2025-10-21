package ua.delivery.repository;

import ua.delivery.model.Person;
import ua.delivery.model.Staff;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class StaffRepository {

    private static final Logger logger = Logger.getLogger(StaffRepository.class.getName());

    private static List<Staff> items;

    public StaffRepository(List<Staff> items) {
        StaffRepository.items = items;
        logger.info("Created StaffRepository with :" + items.size() + " items");
    }

    public void sortByNameDesc() {
        items.sort((s1, s2) -> s2.getFullName().compareTo(s1.getFullName()));
        logger.info("Sorted StaffRepository by name descending :" + items.size() + " items");
    }

    public void sortByLastNameAsc() {
        items.sort((s1, s2) -> s1.getLastName().compareTo(s2.getLastName()));
        logger.info("Sorted StaffRepository by last name ascending :" + items.size() + " items");
    }

    public void sortByLastNameDesc() {
        items.sort((s1, s2) -> s2.getLastName().compareTo(s1.getLastName()));
        logger.info("Sorted StaffRepository by last name descending :" + items.size() + " items");
    }

    List<Staff> getItemsForTesting() {
        return items;
    }
}
