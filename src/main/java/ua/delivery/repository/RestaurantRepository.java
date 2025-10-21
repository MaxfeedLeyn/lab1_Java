package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Person;
import ua.delivery.model.Restaurant;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RestaurantRepository {

    private static final Logger logger = Logger.getLogger(RestaurantRepository.class.getName());

    private static List<Restaurant> items;

    public RestaurantRepository(List<Restaurant> items) {
        if (items == null) {
            throw new InvalidDataException("Can not be null");
        }
        RestaurantRepository.items = items;
        logger.info("Created RestaurantRepository with: " + items.size() + " items");
    }

    public void sortByNameDesc() {
        items.sort((r1, r2) -> r2.getName().compareTo(r1.getName()));
        logger.info("Sorted RestaurantRepository by name descending :" + items.size() + " items");
    }

    public void sortByLocationAsc() {
        items.sort((r1, r2) -> r1.getLocation().compareTo(r2.getLocation()));
        logger.info("Sorted RestaurantRepository ascending :" + items.size() + " items");
    }

    public void sortByLocationDesc() {
        items.sort((r1, r2) -> r2.getLocation().compareTo(r1.getLocation()));
        logger.info("Sorted RestaurantRepository descending :" + items.size() + " items");
    }

    List<Restaurant> getItemsForTesting() {
        return items;
    }
}
