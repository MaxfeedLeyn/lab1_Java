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
    private List<Restaurant> restaurantList;

    @BeforeAll
    void setUpTestData() {
        restaurant1 = new Restaurant("Punjab Plate", "INDIAN", "St. Central 1");
        restaurant2 = new Restaurant("Curry Karma", "MEXICAN", "St. Central 2");
        restaurant3 = new Restaurant("La Felicita", "ITALIAN", "St. Central 3");
    }

    @BeforeEach
    void setUp() {
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);
        restaurantList.add(restaurant3);
        restaurantRepository = new RestaurantRepository(restaurantList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest(){
        Collections.sort(restaurantList);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(restaurantList.get(0).getName()).isEqualTo(restaurant2.getName());
        softly.assertThat(restaurantList.get(1).getName()).isEqualTo(restaurant3.getName());
        softly.assertThat(restaurantList.get(2).getName()).isEqualTo(restaurant1.getName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("RestaurantRepository Tests")
    class RestaurantRepositoryTests {

        @Test
        @DisplayName("sortByNameDesc Test")
        void sortByNameDescTest(){
            restaurantRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurantRepository.getItemsForTesting().get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(1).getName()).isEqualTo(restaurant3.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(2).getName()).isEqualTo(restaurant2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLocationAsc Test")
        void  sortByLocationAscTest(){
            restaurantRepository.sortByLocationAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurantRepository.getItemsForTesting().get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(1).getName()).isEqualTo(restaurant2.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(2).getName()).isEqualTo(restaurant3.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("sortByLocationDesc Test")
        void  sortByLocationDescTest(){
            restaurantRepository.sortByLocationDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(restaurantRepository.getItemsForTesting().get(0).getName()).isEqualTo(restaurant3.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(1).getName()).isEqualTo(restaurant2.getName());
            softly.assertThat(restaurantRepository.getItemsForTesting().get(2).getName()).isEqualTo(restaurant1.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("Sort null Test")
        void sortNullTest(){
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                RestaurantRepository nullRestaurantRepository = new RestaurantRepository(null);
            });
            assertTrue(exception.getMessage().contains("Can not be null"));
        }

        @Test
        @DisplayName("Sort one element Test")
        void sortOneElementTest(){
            List<Restaurant> tmp = new ArrayList<>();
            tmp.add(restaurant1);
            RestaurantRepository one = new RestaurantRepository(tmp);
            SoftAssertions softly = new SoftAssertions();
            one.sortByLocationAsc();
            softly.assertThat(one.getItemsForTesting().get(0).getName()).isEqualTo(restaurant1.getName());
            softly.assertAll();
        }
    }
}
