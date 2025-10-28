package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Delivery;
import ua.delivery.model.MenuItem;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

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

    public List<MenuItem> findByName(String name){
        if (name == null || name.isEmpty())
            return Collections.emptyList();
        String lowered = name.toLowerCase();
        List<MenuItem> menuItemList = getAll().stream()
                .filter(m -> m.getName().toLowerCase().equals(lowered))
                .collect(Collectors.toList());
        logger.info("Found MenuItems by name :" + menuItemList.size() + " items");
        return menuItemList;
    }

    public List<MenuItem> increasePriceDueInflation(float inflation) {
        if (inflation <= 0 || inflation > 1)
            return Collections.emptyList();
        List<MenuItem> menuItems = getAll();
        menuItems.forEach(m -> m.setPrice(m.getPrice() * (1 + inflation)));
        logger.info("Increased MenuItems by price :" + menuItems.size() + " items");
        return menuItems;
    }

    public List<MenuItem> findByPriceInRange(float min, float max) {
        if (min > max || min <= 0 || max <= 0)
            return Collections.emptyList();
        List<MenuItem> menuItems = getAll().stream()
                .filter(m -> m.getPrice() >= min && m.getPrice() <= max)
                .collect(Collectors.toList());
        logger.info("Found MenuItems by price in range (" + min + "," + max + ") :" + menuItems.size() + " items");
        return menuItems;
    }
}
