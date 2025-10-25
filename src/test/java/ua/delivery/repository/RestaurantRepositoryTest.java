package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import ua.delivery.model.Person;
import ua.delivery.model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class RestaurantRepositoryTest {

    private RestaurantRepository restaurantRepository;
    private Restaurant restaurant1, restaurant2, restaurant3;

    @BeforeAll
    void setUpTestData() {
        restaurant1 = new Restaurant("Punjab Plate", "INDIAN", "St. Central 1");
        restaurant2 = new Restaurant("Curry Karma", "MEXICAN", "St. Central 2");
        restaurant3 = new Restaurant("La Felicita", "ITALIAN", "St. Central 3");
    }

    @BeforeEach
    void setUp() {
        restaurantRepository = new RestaurantRepository();
        restaurantRepository.add(restaurant1);
        restaurantRepository.add(restaurant2);
        restaurantRepository.add(restaurant3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        List<Restaurant> restaurants = restaurantRepository.getAll();
        Collections.sort(restaurants);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(restaurants.get(0).getName()).isEqualTo(restaurant2.getName());
        softly.assertThat(restaurants.get(1).getName()).isEqualTo(restaurant3.getName());
        softly.assertThat(restaurants.get(2).getName()).isEqualTo(restaurant1.getName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("RestaurantRepository Tests")
    class RestaurantRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            List<Restaurant> restaurants = restaurantRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurants.get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertThat(restaurants.get(1).getName()).isEqualTo(restaurant3.getName());
            softly.assertThat(restaurants.get(2).getName()).isEqualTo(restaurant2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLocationAsc Test")
        void  sortByLocationAscTest(){
            List<Restaurant> restaurants = restaurantRepository.sortByLocationAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurants.get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertThat(restaurants.get(1).getName()).isEqualTo(restaurant2.getName());
            softly.assertThat(restaurants.get(2).getName()).isEqualTo(restaurant3.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLocationDesc Test")
        void  sortByLocationDescTest(){
            List<Restaurant> restaurants = restaurantRepository.sortByLocationDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurants.get(0).getName()).isEqualTo(restaurant3.getName());
            softly.assertThat(restaurants.get(1).getName()).isEqualTo(restaurant2.getName());
            softly.assertThat(restaurants.get(2).getName()).isEqualTo(restaurant1.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort one element Test")
        void sortOneElementTest(){
            RestaurantRepository oneElement = new RestaurantRepository();
            oneElement.add(restaurant1);
            SoftAssertions softly = new SoftAssertions();
            List<Restaurant> oneRestaurant = oneElement.sortByLocationAsc();
            softly.assertThat(oneRestaurant.get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertAll();
        }
    }
}
