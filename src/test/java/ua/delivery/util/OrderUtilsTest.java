package ua.delivery.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

public class OrderUtilsTest {

    @Nested
    @DisplayName("isValidCustomer Tests")
    class ValidCustomerTests {

        @Test
        @DisplayName("Should return false if customer is null")
        void isValidCustomerNull(){
            assertFalse(OrderUtils.isValidCustomer(null));
        }
    }

    @Nested
    @DisplayName("isValidMenu Tests")
    class ValidMenuTests{

        @Test
        @DisplayName("Should return false when MenuItem is null")
        void isValidMenuNull(){
            assertFalse(OrderUtils.isValidMenu(null));
        }
    }
}
