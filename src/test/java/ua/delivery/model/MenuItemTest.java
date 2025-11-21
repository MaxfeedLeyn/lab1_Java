package ua.delivery.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import ua.delivery.exception.InvalidDataException;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create MenuItem with null fields")
        void testConstructor() {
            MenuItem menuItem = new MenuItem();

            assertNotNull(menuItem.getName(), "Expected name to be null after default constructor");
            assertNotNull(menuItem.getCategory(), "Expected category to be null after default constructor");
            assertEquals(0f, menuItem.getPrice(), "Expected price to be null after default constructor");
        }

    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {

        @ParameterizedTest
        @ValueSource(strings = {"name", "price", "category"})
        @DisplayName("Fields should be private")
        void testFieldsAreProtected(String fieldName) throws NoSuchFieldException {
            var field = MenuItem.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    () -> String.format("Expected field %s to be protected, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }
    }

    @Nested
    @DisplayName("FirstName Tests")
    class FirstNameTests {

        @ParameterizedTest
        @CsvSource({
                "sUsHi, Sushi",
                "TacOS, Tacos",
                "LaSAGnA, Lasagna",
                "'  curry  ', Curry"
        })
        @DisplayName("Should set and capitalize valid names")
        void testValidFirstNames(String input, String expected) {
            MenuItem menuItem = new MenuItem("Random", 15f, "American");
            menuItem.setName(input);

            assertEquals(expected, menuItem.getName(),
                    () -> String.format("Expected name to be '%s' but was '%s'", expected, menuItem.getName()));
        }

//        @Test
//        @DisplayName("Should not set null name")
//        void testSetNullFirstName() {
//            MenuItem menuItem = new MenuItem();
//            InvalidDataException test = assertThrows(InvalidDataException.class, () -> menuItem.setName(null));
//            assertTrue(test.getMessage().contains("Category cannot"));
//        }
    }

    @Nested
    @DisplayName("createMenuItem Static Method Tests")
    class CreateMenuItemsTests {

        @ParameterizedTest
        @CsvSource({
                "Sushi, 24.0, Japan",
                "Tacos, 15.0, Spanish",
                "Lasagna, 20.0, American"
        })
        @DisplayName("Should create MenuItem with valid values")
        void  testValidMenuItems(String name, float price, String category) {
            MenuItem menuItem = MenuItem.createMenuItem(name, price, category);
            assertEquals(name, menuItem.getName(),
                    ()->String.format("Expected name to be %s, but was: %s", name, menuItem.getName()));
            assertEquals(price, menuItem.getPrice(),
                    ()->String.format("Expected price to be %f, but was: %f", price, menuItem.getPrice()));
            assertEquals(category, menuItem.getCategory(),
                    ()->String.format("Expected category to be %s, but was: %s", category, menuItem.getCategory()));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            MenuItem menuItem = MenuItem.createMenuItem("Sushi", 24.0f, "Japan");
            String expectString = "Cuisine{Name='Sushi', Price='24.0', Category='Japan'}";
            assertEquals(expectString, menuItem.toString(),
                    ()->String.format("Expected toString %s, but was %s",  expectString, menuItem.toString()));
        }

        @Test
        @DisplayName("Should format toString correctly with null fields")
        void testToStringWithNullFields() {
            MenuItem menuItem = new MenuItem();
            String expectedString = "Cuisine{Name='####', Price='0.0', Category='Abracadabra'}";

            assertEquals(expectedString, menuItem.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, menuItem.toString()));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            MenuItem menuItem = new MenuItem("Sushi", 24.0f, "Japan");

            assertTrue(menuItem.equals(menuItem), "MenuItem should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to MenuItem with same data")
        void testEqualsSymmetric() {
            MenuItem menuItem1 = new MenuItem("Sushi", 24.0f, "Japan");
            MenuItem menuItem2 = new MenuItem("Sushi", 24.0f, "Japan");

            assertTrue(menuItem1.equals(menuItem2),
                    "MenuItems with same data should be equal");
            assertTrue(menuItem2.equals(menuItem1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            MenuItem menuItem = new MenuItem("Sushi", 24.0f, "Japan");

            assertFalse(menuItem.equals(null), "MenuItem should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            MenuItem menuItem = new MenuItem("Sushi", 24.0f, "Japan");
            String notAMenuItem = "Not a MenuItem";

            assertFalse(menuItem.equals(notAMenuItem), "MenuItem should not be equal to different class");
        }

        @Test
        @DisplayName("Equal MenuItems should have same hashCode")
        void testHashCodeConsistency() {
            MenuItem menuItem1 = new MenuItem("Sushi", 24.0f, "Japan");
            MenuItem menuItem2 = new MenuItem("Sushi", 24.0f, "Japan");

            assertTrue(menuItem1.equals(menuItem2), "MenuItems should be equal");
            assertEquals(menuItem1.hashCode(), menuItem2.hashCode(),
                    "Equal MenuItems should have same hashCode");
        }
    }
}
