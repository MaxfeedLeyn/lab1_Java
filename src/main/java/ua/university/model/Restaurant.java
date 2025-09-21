package ua.university.model;

import ua.university.util.RestaurantUtils;

import java.util.Objects;

public class Restaurant {
    private String name;
    private String cuisine;
    private String location;

    public Restaurant(){
    }

    public Restaurant(String name, String cuisine, String location) {
        setName(name);
        setCuisine(cuisine);
        setLocation(location);
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
    }

    public void setCuisine(String cuisine) {
        if(RestaurantUtils.isValidRestaurantCuisine(cuisine)) {
            this.cuisine = RestaurantUtils.formatRestaurantCuisine(cuisine);
        }
    }

    public void setLocation(String location) {
        location = RestaurantUtils.formatRestaurantLocation(location);
        if(RestaurantUtils.isValidRestaurantLocation(location)) {
            this.location = location;
        }
    }

    public static Restaurant createRestaurant(String name, String cuisine, String location) {
        if(RestaurantUtils.isValidRestaurantName(name) &&
                RestaurantUtils.isValidRestaurantCuisine(cuisine) &&
                RestaurantUtils.isValidRestaurantLocation(location)) {
            String restaurantName = RestaurantUtils.formatRestaurantName(name);
            String restaurantCuisine = RestaurantUtils.formatRestaurantCuisine(cuisine);
            return new Restaurant(restaurantName, restaurantCuisine, location);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name=" + name + '\'' +
                ", cuisine=" +  cuisine + '\'' +
                ", location=" + location +
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
