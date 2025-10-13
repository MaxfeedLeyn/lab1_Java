package ua.delivery.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class StaffUtilsTest {

    @Nested
    @DisplayName("isValidRanking Tests")
    class ValidRankingTests {

        @ParameterizedTest
        @CsvSource({
                "0, true",
                "1, true",
                "2, true",
                "3, true",
                "4, true",
                "5, true"
        })
        void ValidRankingTest(int ranking, boolean expected){
            assertTrue(StaffUtils.isValidRanking(ranking));
        }

        @ParameterizedTest
        @CsvSource({
                "-1, false",
                "-2, false",
                "6, true",
                "10, true"
        })
        void notValidRankingTest(int ranking, boolean expected){
            assertFalse(StaffUtils.isValidRanking(ranking));
        }
    }
}
