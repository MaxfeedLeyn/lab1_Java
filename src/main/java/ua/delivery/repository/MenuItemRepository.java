package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Delivery;
import ua.delivery.model.MenuItem;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MenuItemRepository extends GenericRepository<MenuItem> {

    private static final Logger logger = Logger.getLogger(MenuItemRepository.class.getName());

    public MenuItemRepository() {
        super(MenuItem::getName, "MenuItem");
    }

    public List<MenuItem> sortByNameDesc() {
        List<MenuItem> menuItems = getAll();
        menuItems.sort((m1, m2) -> m2.getName().compareTo(m1.getName()));
        logger.info("Sorted MenuItemRepository by name descending :" + menuItems.size() + " items");
        return menuItems;
    }

    public List<MenuItem> sortByPriceAsc() {
        List<MenuItem> menuItems = getAll();
        menuItems.sort((m1, m2) -> (int) (m1.getPrice() - m2.getPrice()));
        logger.info("Sorted MenuItemRepository by price ascending :" + menuItems.size() + " items");
        return menuItems;
    }

    public List<MenuItem> sortByPriceDesc() {
        List<MenuItem> menuItems = getAll();
        menuItems.sort((m1, m2) -> (int) (m2.getPrice() - m1.getPrice()));
        logger.info("Sorted MenuItemRepository by price descending :" + menuItems.size() + " items");
        return menuItems;
    }

}
