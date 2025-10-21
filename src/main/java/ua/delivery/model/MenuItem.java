package ua.delivery.model;

import ua.delivery.util.MenuItemUtils;
import ua.delivery.exception.InvalidDataException;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Objects;

public class MenuItem implements Comparable<MenuItem> {
    private String name;
    private float price;
    private String category;

    private static final Logger logger = Logger.getLogger(MenuItem.class.getName());

    public MenuItem(){
    }

    public MenuItem(String name, float price, String category) {
        setName(name);
        setPrice(price);
        setCategory(category);
        logger.log(Level.INFO, "Create MenuItem");
    }

    public MenuItem(MenuItem menuItem){
        this.name = menuItem.name;
        this.price = menuItem.price;
        this.category = menuItem.category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        if(MenuItemUtils.isValidName(name)) {
            this.name = MenuItemUtils.capitalizeText(name);
        }
        else
            logger.log(Level.WARNING, "Invalid name for MenuItem: " + name);
    }

    public void setCategory(String category) {
        if(MenuItemUtils.isValidCategory(category)) {
            this.category = MenuItemUtils.capitalizeText(category);
        }
        else
            logger.log(Level.WARNING, "Invalid category for MenuItem: " + category);
    }

    public void setPrice(float price) {
        if (MenuItemUtils.isValidFloat(price)) {
            this.price = price;
        }
        else
            logger.log(Level.WARNING, "Invalid price for MenuItem: " + price);
    }

    public boolean isComplete() {
        return name != null && price != 0 && category != null;
    }

    public static MenuItem createMenuItem(String name, float price, String category) {
        if(MenuItemUtils.isValidName(name) &&
                MenuItemUtils.isValidFloat(price) &&
                MenuItemUtils.isValidCategory(category)) {
            logger.log(Level.INFO, "Successfully created MenuItem");
            return new MenuItem(name, price, category);
        }
        throw new InvalidDataException("The attempt to create a MenuItem has failed, check name, price and category!");
    }

    @Override
    public int compareTo(MenuItem o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "Name='" + name + '\'' +
                ", Price='" + price + '\'' +
                ", Category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Float.compare(menuItem.price, price) == 0 &&
                Objects.equals(name, menuItem.name) &&
                Objects.equals(category, menuItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }
}
