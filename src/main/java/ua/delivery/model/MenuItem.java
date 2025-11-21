package ua.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.delivery.util.MenuItemUtils;
import ua.delivery.exception.InvalidDataException;

import jakarta.validation.constraints.*;
import ua.delivery.util.ValidationUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuItem implements Comparable<MenuItem> {
    @NotBlank(message = "Name of the dish cannot be null or blank")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']{2,50}$",
            message = "Name of the dish must be 2-50 characters long and contain only letters, spaces, hyphens, or apostrophes"
    )
    private String name;

    @PositiveOrZero(message = "Price must be positive")
    private float price;

    @NotBlank(message = "Category cannot be null or blank")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']{2,50}$",
            message = "Category must be 2-50 characters long and contain only letters, spaces, hyphens, or apostrophes"
    )
    private String category;

    private static final Logger logger = Logger.getLogger(MenuItem.class.getName());

    public MenuItem(){
        this("####", 0f, "Abracadabra");
    }

    public MenuItem(String name, float price, String category) {
        this.name = MenuItemUtils.capitalizeText(name);
        this.price = price;
        this.category = MenuItemUtils.capitalizeText(category);
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
        if(name != null){
            name = MenuItemUtils.capitalizeText(name);
        }

        String oldValue = this.name;
        this.name = name;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change MenuItem name to " + name);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid name for MenuItem: " + name);
            this.name = oldValue;
            throw e;
        }
    }

    public void setCategory(String category) {
        if(category != null){
            category = MenuItemUtils.capitalizeText(category);
        }

        String oldValue = this.category;
        this.category = category;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change MenuItem category to " + category);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid category for MenuItem: " + category);
            this.category = oldValue;
            throw e;
        }
    }

    public void setPrice(float price) {

        float oldValue = this.price;
        this.price = price;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change MenuItem price to " + price);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid price for MenuItem: " + price);
            this.price = oldValue;
            throw e;
        }
    }

    public boolean isComplete() {
        return !name.equals("####") && price != 0 && !category.equals("Abracadabra");
    }

    public static MenuItem createMenuItem(String name, float price, String category) {
        MenuItem menuItem = new MenuItem(name,price,category);
        ValidationUtils.validate(menuItem);
        return menuItem;
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
