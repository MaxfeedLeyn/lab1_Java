package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Person;
import ua.delivery.model.Restaurant;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RestaurantRepository extends GenericRepository<Restaurant> {

    private static final Logger logger = Logger.getLogger(RestaurantRepository.class.getName());

    public RestaurantRepository() {
        super(Restaurant::getName, "Restaurant");
    }

    public List<Restaurant> sortByNameDesc() {
        List<Restaurant> restaurants = getAll();
        restaurants.sort((r1, r2) -> r2.getName().compareTo(r1.getName()));
        logger.info("Sorted RestaurantRepository by name descending :" + restaurants.size() + " items");
        return restaurants;
    }

    public List<Restaurant> sortByLocationAsc() {
        List<Restaurant> restaurants = getAll();
        restaurants.sort((r1, r2) -> r1.getLocation().compareTo(r2.getLocation()));
        logger.info("Sorted RestaurantRepository ascending :" + restaurants.size() + " items");
        return restaurants;
    }

    public List<Restaurant> sortByLocationDesc() {
        List<Restaurant> restaurants = getAll();
        restaurants.sort((r1, r2) -> r2.getLocation().compareTo(r1.getLocation()));
        logger.info("Sorted RestaurantRepository descending :" + restaurants.size() + " items");
        return restaurants;
    }

}
