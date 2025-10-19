package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemUtilsTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<MenuItemUtils> constructor = MenuItemUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            MenuItemUtils instance = constructor.newInstance();
            assertNotNull(instance);
        }

        @Test
        @DisplayName("Constructor should be private")
        void testConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<MenuItemUtils> constructor = MenuItemUtils.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                    "Constructor should be private but was" + Modifier.toString(constructor.getModifiers()));
        }
    }

    @Nested
    @DisplayName("isValidCategory Tests")
    class ValidCategoryTests {

        @ParameterizedTest
        @ValueSource(strings = {"AAAAA", "AAAAAAAAAA", "Valid"})
        @DisplayName("Should return true when valid data")
        void isValidCategory(String category) {
            assertTrue(MenuItemUtils.isValidCategory(category),
                    ()->String.format("Category %s is not valid", category));
        }

        @ParameterizedTest
        @ValueSource(strings = {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "AA", ""})
        @DisplayName("Should return false when invalid data")
        void isInvalidCategory(String category) {
            assertFalse(MenuItemUtils.isValidCategory(category),
                    ()->String.format("Category %s is valid", category));
        }
    }

    @Nested
    @DisplayName("ValidFloat Tests")
    class ValidFloatTests {

        @ParameterizedTest
        @ValueSource(ints = {-1, -10, 0})
        @DisplayName("Should return false when invalid data")
        void ValidFloat(int value) {
            assertFalse(MenuItemUtils.isValidFloat(value),
                    ()->String.format("Float value %s is valid", value));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 10})
        @DisplayName("Should return true when valid data")
        void NotValidFloat(int value) {
            assertTrue(MenuItemUtils.isValidFloat(value),
                    ()->String.format("Float value %s is not valid", value));
        }
    }
}
