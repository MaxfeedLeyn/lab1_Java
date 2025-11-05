package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationHelperTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor should be private")
        void testConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<ValidationHelper> constructor = ValidationHelper.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                    "Constructor should be private but was" + Modifier.toString(constructor.getModifiers()));
        }

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<ValidationHelper> constructor = ValidationHelper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ValidationHelper instance = constructor.newInstance();
            assertNotNull(instance);
        }
    }

    @Nested
    @DisplayName("Method Access Modifiers Tests")
    class MethodAccessModifiersTests {

        @Test
        @DisplayName("isStringMatchPattern should be package-private static")
        void testIsStringMatchPatternBePackagePrivateStatic() throws NoSuchMethodException {
            Method method = ValidationHelper.class.getDeclaredMethod("isStringMatchPattern", String.class, String.class);
            assertTrue(Modifier.isStatic(method.getModifiers()));
            assertFalse(Modifier.isPrivate(method.getModifiers()));
            assertFalse(Modifier.isProtected(method.getModifiers()));
            assertFalse(Modifier.isPublic(method.getModifiers()));
        }

        @Test
        @DisplayName("isNumberBetween should be package-private static")
        void testIsNumberBetweenPackagePrivateStatic() throws NoSuchMethodException {
            Method method = ValidationHelper.class.getDeclaredMethod("isNumberBetween", int.class, int.class, int.class);
            assertTrue(Modifier.isStatic(method.getModifiers()));
            assertFalse(Modifier.isPrivate(method.getModifiers()));
            assertFalse(Modifier.isProtected(method.getModifiers()));
            assertFalse(Modifier.isPublic(method.getModifiers()));
        }

        @Test
        @DisplayName("isStringLengthBetween should be package-private static")
        void testIsStringLengthBetweenAccessModifier() throws NoSuchMethodException {
            Method method = ValidationHelper.class.getDeclaredMethod("isStringLengthBetween", String.class, int.class, int.class);
            assertTrue(Modifier.isStatic(method.getModifiers()));
            assertFalse(Modifier.isPrivate(method.getModifiers()));
            assertFalse(Modifier.isProtected(method.getModifiers()));
            assertFalse(Modifier.isPublic(method.getModifiers()));
        }
    }

    @Nested
    @DisplayName("isStringMatchPattern Tests")
    class StringMatchPatternTests {

        @Test
        @DisplayName("Should return false when text is null")
        void textNullPattern(){
            assertFalse(ValidationHelper.isStringMatchPattern(null, "."));
        }

        @Test
        @DisplayName("Should return false when pattern is null")
        void testPatternNull(){
            assertFalse(ValidationHelper.isStringMatchPattern("text", null));
        }

        @Test
        @DisplayName("Should return false when both text and pattern are null")
        void testBothNull(){
            assertFalse(ValidationHelper.isStringMatchPattern("text", null));
        }

        @ParameterizedTest
        @CsvSource({
                "test123, \\w+, true",
                "abc, [a-z]+, true",
                "123, \\d+, true",
                "'', '', true"
        })
        @DisplayName("Should return true for valid pattern matches")
        void testValidPatternMatch(String text, String pattern, boolean expectedResult) {
            boolean actualResult = ValidationHelper.isStringMatchPattern(text, pattern);
            assertEquals(expectedResult, actualResult,
                    ()->String.format("Expected isStringMatchPattern('%s', '%s') to return %s but was %s",
                            text, pattern, expectedResult, actualResult));
        }

        @ParameterizedTest
        @CsvSource({
                "test123, \\d+, false",
                "ABC, [a-z]+, false",
                "abc, \\d+, false",
                "'', \\w+, false"
        })
        @DisplayName("Should return false for invalid pattern matches")
        void testInvalidPatternMatch(String text, String pattern, boolean expected) {
            boolean actual = ValidationHelper.isStringMatchPattern(text, pattern);
            assertEquals(expected, actual,
                    () -> String.format("Expected isStringMatchPattern('%s', '%s') to return %s but was %s",
                            text, pattern, expected, actual));
        }
    }

    @Nested
    @DisplayName("isNumberBetween Tests")
    class NumberBetweenTests {

        @ParameterizedTest
        @CsvSource({
                "5, 1, 10, true",
                "1, 1, 10, true",
                "10, 1, 10, true",
                "0, 0, 0, true",
                "-5, -10, -1, true"
        })
        @DisplayName("Should return true when number is within range")
        void testNumberInRange(int number, int min, int max, boolean expected) {
            boolean actual = ValidationHelper.isNumberBetween(number, min, max);
            assertEquals(expected, actual,
                    () -> String.format("Expected isNumberBetween(%d, %d, %d) to return %s but was %s",
                            number, min, max, expected, actual));
        }

        @ParameterizedTest
        @CsvSource({
                "0, 1, 10, false",
                "-5, 1, 10, false",
                "11, 1, 10, false",
                "15, 1, 10, false",
                "-11, -10, -1, false",
                "0, -10, -1, false",
                "4, 5, 5, false",
                "6, 5, 5, false"
        })
        @DisplayName("Should return false when number is outside range")
        void testNumberOutsideRange(int number, int min, int max, boolean expected) {
            boolean actual = ValidationHelper.isNumberBetween(number, min, max);
            assertEquals(expected, actual,
                    () -> String.format("Expected isNumberBetween(%d, %d, %d) to return %s but was %s",
                            number, min, max, expected, actual));
        }
    }

    @Nested
    @DisplayName("isStringLengthBetween Tests")
    class StringLengthBetweenTests {

        @Test
        @DisplayName("Should return false when text is null")
        void testNullText() {
            assertFalse(ValidationHelper.isStringLengthBetween(null, 1, 10));
        }

        @ParameterizedTest
        @CsvSource({
                "hello, 1, 10, true",
                "a, 1, 10, true",
                "1234567890, 1, 10, true",
                "'  hello  ', 1, 10, true",
                "'   a   ', 1, 10, true",
                "hello, 5, 5, true",
                "'', 0, 10, true",
                "'   ', 0, 10, true"
        })
        @DisplayName("Should return true when trimmed length is within range")
        void testLengthInRange(String text, int min, int max, boolean expected) {
            assertEquals(expected, ValidationHelper.isStringLengthBetween(text, min, max));
        }

        @ParameterizedTest
        @CsvSource({
                "'', 1, 10, false",
                "'   ', 1, 10, false",
                "12345678901, 1, 10, false",
                "'very long string', 1, 10, false",
                "hi, 5, 5, false",
                "'hello world', 5, 5, false"
        })
        @DisplayName("Should return false when trimmed length is outside range")
        void testLengthOutsideRange(String text, int min, int max, boolean expected) {
            assertEquals(expected, ValidationHelper.isStringLengthBetween(text, min, max));
        }
    }

    @Nested
    @DisplayName("isValidDate Tests")
    class DateTests {

        @Test
        @DisplayName("Should return true with valid date")
        void testValidDate() {
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.NOVEMBER, 29);
            Date orderDate = cal.getTime();

            assertTrue(ValidationHelper.isValidDate(orderDate));
        }

        @Test
        @DisplayName("Should return false when invalid date")
        void testInvalidDate() {
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.AUGUST, 29);
            Date orderDate = cal.getTime();

            assertFalse(ValidationHelper.isValidDate(orderDate));
        }
    }
}
