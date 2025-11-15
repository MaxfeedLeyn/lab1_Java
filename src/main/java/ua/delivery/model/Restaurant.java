package ua.delivery.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.RestaurantUtils;
import jakarta.validation.constraints.*;
import ua.delivery.util.ValidationUtils;

import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurant implements Comparable<Restaurant> {

    @NotBlank(message = "Name cannot be null or blank")
    @Pattern(
            regexp = "^[a-zA-Z\\s\\-']{2,50}$",
            message = "Name must be 2-50 character long and contain only letters, hyphens, or apostrophes"
    )
    private String name;

    @NotBlank(message = "Cuisine cannot be null or blank")
    @Pattern(
            regexp = "^[a-zA-Z\\s\\-']{2,50}$",
            message = "Cuisine must be 2-50 character long and contain only letters, hyphens, or apostrophes"
    )
    private String cuisine;

    @NotBlank(message = "Location cannot be null or blank")
    @Pattern(
            regexp = "^(?i)St\\.?\\s+[\\p{L}0-9.'\\-\\s]+\\s+\\d+[A-Za-z0-9\\/-]*$",
            message = "Location must match pattern St. NameOfStreet number"
    )
    private String location;

    private static final Logger logger = Logger.getLogger(Restaurant.class.getName());

    public Restaurant(){
    }

    public Restaurant(String name, String cuisine, String location) {
        this.name = RestaurantUtils.isValidRestaurantName(name) ? RestaurantUtils.formatRestaurantName(name) : "";
        this.cuisine = RestaurantUtils.isValidRestaurantCuisine(cuisine) ? RestaurantUtils.formatRestaurantCuisine(cuisine) : "";
        this.location = RestaurantUtils.isValidRestaurantLocation(location) ? RestaurantUtils.formatRestaurantLocation(location) : "";
        ValidationUtils.validate(this);
        logger.log(Level.INFO, "Restaurant created");
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        if (name != null){
            name = RestaurantUtils.formatRestaurantName(name);
        }

        String oldValue = this.name;
        this.name = name;

        try{
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Restaurant Name updated");
        }
        catch (InvalidDataException e){
            logger.log(Level.WARNING, e.getMessage());
            this.name = oldValue;
            throw e;
        }
    }

    public void setCuisine(String cuisine) {
        if (cuisine != null){
            cuisine = RestaurantUtils.formatRestaurantCuisine(cuisine);
        }

        String oldValue = this.cuisine;
        this.cuisine = cuisine;

        try{
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Restaurant Cuisine updated");
        }
        catch (InvalidDataException e){
            logger.log(Level.WARNING, e.getMessage());
            this.cuisine = oldValue;
            throw e;
        }
    }

    public void setLocation(String location) {
        if (location != null){
            location = RestaurantUtils.formatRestaurantLocation(location);
        }

        String oldValue = this.location;
        this.location = location;

        try{
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Restaurant Location updated");
        }
        catch (InvalidDataException e){
            logger.log(Level.WARNING, e.getMessage());
            this.location = oldValue;
            throw e;
        }
    }

    public static Restaurant createRestaurant(String name, String cuisine, String location) {
        Restaurant restaurant = new Restaurant(name, cuisine, location);
        logger.log(Level.INFO, "Restaurant created");
        return restaurant;
    }

    @Override
    public int compareTo(Restaurant o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", cuisine='" +  cuisine + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant restaurant = (Restaurant) o;
        return Objects.equals(name, restaurant.name) &&
                Objects.equals(cuisine, restaurant.cuisine) &&
                Objects.equals(location, restaurant.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cuisine, location);
    }
}
