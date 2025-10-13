package ua.delivery.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    @Nested
    @DisplayName("Restaurant constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create restaurant with null fields")
        void testConstructor() {
            Restaurant restaurant = new Restaurant();

            assertNull(restaurant.getName(), "Expected name to be null after default constructor");
            assertNull(restaurant.getCuisine(), "Expected cuisine to be null after default constructor");
            assertNull(restaurant.getLocation(), "Expected location to be null after default constructor");
        }

        @Test
        @DisplayName("Constructor should not set invalid fields")
        void testConstructorWithInvalidData() {
            Restaurant restaurant = new Restaurant("", "Italic", "invalidAddress");

            assertNull(restaurant.getName(), "Expected name to be null when invalid name provided");
            assertEquals("Italic", restaurant.getCuisine(), "Expected cuisine to be set when valid");
            assertNull(restaurant.getLocation(), "Expected location to be null when invalid address provided");
        }

        @Test
        @DisplayName("Constructor should set valid fields")
        void testConstructorWithValidData() {
            Restaurant restaurant = new Restaurant("La Felicita", "Italic", "St. LaFelicita 2");

            assertNotNull(restaurant.getName(), "Expected name to be set after constructor with valid fields");
            assertNotNull(restaurant.getLocation(), "Expected location to be set after constructor with valid fields");
            assertNotNull(restaurant.getCuisine(), "Expected cuisine to be set after constructor with valid fields");
        }
    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {

        @ParameterizedTest
        @ValueSource(strings = {"name", "cuisine", "location"})
        @DisplayName("Fields should be private")
        void testFieldsAreProtected(String fieldName) throws NoSuchFieldException {
            var field = Restaurant.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    () -> String.format("Expected field %s to be private, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }
    }

    @Nested
    @DisplayName("Setters Tests")
    class  SettersTests {

        @ParameterizedTest
        @CsvSource({
                "SPagGeTi, Spaggeti",
                "MoZzAReLLa, Mozzarella",
                "TarTar, Tartar"
        })
        @DisplayName("Should format name of the dish with valid values")
        void SetNameWithValidNumbersTest(String name, String expectedName){
            Restaurant restaurant = new Restaurant(name, "Italic", "St. LaFelicita 2");
            assertEquals(expectedName, restaurant.getName(),
                    ()->String.format("Expected name %s, but was: %s", expectedName, restaurant.getName()));
        }

        @ParameterizedTest
        @CsvSource({
                "ITaliC, Italic",
                "AmERican, American"
        })
        @DisplayName("Should format cuisine when valid values")
        void SetCuisineWithValidTest(String cuisine, String expectedCuisine){
            Restaurant restaurant = new Restaurant("Lalala", cuisine, "St. LaFelicita");
            assertEquals(expectedCuisine, restaurant.getCuisine(),
                    ()->String.format("Expected cuisine: %s, but was: %s", expectedCuisine, restaurant.getCuisine()));
        }

        @Test
        @DisplayName("Should remain name null if set null")
        void SetNullNameTest(){
            Restaurant restaurant = new Restaurant(null, "ValidValue", "St. LaFelicita 2");
            assertNull(restaurant.getName(), "Expected name to be null after invalid value");
        }

        @Test
        @DisplayName("Should remain cuisine null if set null")
        void SetNullCuisineTest(){
            Restaurant restaurant = new Restaurant("Lalala", null, "St. LaFelicita 2");
            assertNull(restaurant.getCuisine(), "Expected cuisine to be null after invalid value");
        }
    }

    @Nested
    @DisplayName("createRestaurant Static Method Tests")
    class StaticMethodTests {

        @ParameterizedTest
        @CsvSource({
                "Spagetti, Italic, St. Central 1",
                "Tartar, Greek, St. Central 2",
                "Pizza, Italic, St. Central 1"
        })
        @DisplayName("Should create restaurant object with valid values")
        void createRestaurantStaticMethodTests(String name, String cuisine, String location) {
            Restaurant restaurant = new Restaurant(name, cuisine, location);
            assertEquals(name, restaurant.getName(),
                    () -> String.format("Expected name to be set after constructor with valid values %s, but was: %s",
                            name, cuisine ));
            assertEquals(location, restaurant.getLocation(),
                    ()->String.format("Expected location to be set after constructor with valid values %s, but was: %s",
                            location, restaurant.getLocation()));
            assertEquals(cuisine, restaurant.getCuisine(),
                    ()->String.format("Expected cuisine to be set after constructor with valid values %s,  but was: %s",
                            cuisine, restaurant.getCuisine()));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields(){
            Restaurant restaurant = new Restaurant("Spaggeti", "Italic", "St. LaFelicita 2");
            String expectedString = "Restaurant{name='Spaggeti', cuisine='Italic', location='St. Lafelicita 2'}";

            assertEquals(expectedString, restaurant.toString(),
                    ()->String.format("Expected name %s, but was: %s", expectedString, restaurant));
        }

        @Test
        @DisplayName("Should format toString correctly with null fields")
        void testToStringWithNullFields() {
            Restaurant restaurant = new Restaurant();
            String expectedString = "Restaurant{name='null', cuisine='null', location='null'}";

            assertEquals(expectedString, restaurant.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, restaurant));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Restaurant restaurant = new Restaurant("NewRest", "Italic", "St. Central 1");

            assertTrue(restaurant.equals(restaurant), "Restaurant should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to person with same data")
        void testEqualsSymmetric() {
            Restaurant restaurant1 = new Restaurant("NewRest", "Italic", "St. Central 1");
            Restaurant restaurant2 = new Restaurant("NewRest", "Italic", "St. Central 1");

            assertTrue(restaurant1.equals(restaurant2),
                    "Restaurants with same data should be equal");
            assertTrue(restaurant2.equals(restaurant1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Restaurant restaurant = new Restaurant("NewRest", "Italic", "St. Central 1");

            assertFalse(restaurant.equals(null), "Restaurant should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Person person = new Person("NewRest", "Italic", "St. Central 1");
            String notAPerson = "Not a Restaurant";

            assertFalse(person.equals(notAPerson), "Restaurant should not be equal to different class");
        }

        @Test
        @DisplayName("Equal persons should have same hashCode")
        void testHashCodeConsistency() {
            Restaurant restaurant1 = new Restaurant("NewRest", "Italic", "St. Central 1");
            Restaurant restaurant2 = new Restaurant("NewRest", "Italic", "St. Central 1");

            assertTrue(restaurant1.equals(restaurant2), "Restaurants should be equal");
            assertEquals(restaurant1.hashCode(), restaurant2.hashCode(),
                    "Equal Restaurants should have same hashCode");
        }
    }
}
