package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerUtilsTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<CustomerUtils> constructor = CustomerUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            CustomerUtils instance = constructor.newInstance();
            assertNotNull(instance);
        }

        @Test
        @DisplayName("Constructor should be private")
        void testConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<CustomerUtils> constructor = CustomerUtils.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                    "Constructor should be private but was" + Modifier.toString(constructor.getModifiers()));
        }
    }
}
