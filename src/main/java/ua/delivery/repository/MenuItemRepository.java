package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Delivery;
import ua.delivery.model.MenuItem;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MenuItemRepository {

    private static final Logger logger = Logger.getLogger(MenuItemRepository.class.getName());

    private static List<MenuItem> menuItems;

    public MenuItemRepository(List<MenuItem> menuItems) {
        if (menuItems == null) {
            throw new InvalidDataException("Can not be null");
        }
        MenuItemRepository.menuItems = menuItems;
        logger.info("Created MenuItemRepository with: " + menuItems.size() + " items");
    }

    public void sortByNameDesc() {
        menuItems.sort((m1, m2) -> m2.getName().compareTo(m1.getName()));
        logger.info("Sorted MenuItemRepository by name descending :" + menuItems.size() + " items");
    }

    public void sortByPriceAsc() {
        menuItems.sort((m1, m2) -> (int) (m1.getPrice() - m2.getPrice()));
        logger.info("Sorted MenuItemRepository by price ascending :" + menuItems.size() + " items");
    }

    public void sortByPriceDesc() {
        menuItems.sort((m1, m2) -> (int) (m2.getPrice() - m1.getPrice()));
        logger.info("Sorted MenuItemRepository by price descending :" + menuItems.size() + " items");
    }

    List<MenuItem> getItemsForTesting() {
        return menuItems;
    }
}
