package ua.delivery.model;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.RestaurantUtils;

import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurant implements Comparable<Restaurant> {
    private String name;
    private String cuisine;
    private String location;

    private static final Logger logger = Logger.getLogger(Restaurant.class.getName());

    public Restaurant(){
    }

    public Restaurant(String name, String cuisine, String location) {
        setName(name);
        setCuisine(cuisine);
        setLocation(location);
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
        if(RestaurantUtils.isValidRestaurantName(name)) {
            this.name = RestaurantUtils.formatRestaurantName(name);
        }
        else
            logger.log(Level.WARNING, "Invalid restaurant name");
    }

    public void setCuisine(String cuisine) {
        if(RestaurantUtils.isValidRestaurantCuisine(cuisine)) {
            this.cuisine = RestaurantUtils.formatRestaurantCuisine(cuisine);
        }
        else
            logger.log(Level.WARNING, "Invalid restaurant cuisine");
    }

    public void setLocation(String location) {
        location = RestaurantUtils.formatRestaurantLocation(location);
        if(RestaurantUtils.isValidRestaurantLocation(location)) {
            this.location = location;
        }
        else
            logger.log(Level.WARNING, "Invalid restaurant location");
    }

    public static Restaurant createRestaurant(String name, String cuisine, String location) {
        if(RestaurantUtils.isValidRestaurantName(name) &&
                RestaurantUtils.isValidRestaurantCuisine(cuisine) &&
                RestaurantUtils.isValidRestaurantLocation(location)) {
            String restaurantName = RestaurantUtils.formatRestaurantName(name);
            String restaurantCuisine = RestaurantUtils.formatRestaurantCuisine(cuisine);
            logger.log(Level.INFO, "Restaurant name: " + restaurantName);
            return new Restaurant(restaurantName, restaurantCuisine, location);
        }
        throw new InvalidDataException("The attempt to create a Restaurant has failed, check name, cuisine and location!");
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
