package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class MenuItemRepositoryTest {

    private MenuItemRepository menuItemRepository;
    private MenuItem menuItem1, menuItem2, menuItem3;

    @BeforeAll
    void setUpTestData() {
        menuItem1 = new MenuItem("Pasta", 15.5f, "Italian");
        menuItem2 = new MenuItem("Burger", 21.5f, "American");
        menuItem3 = new MenuItem("Taco", 10f, "Mexican");
    }

    @BeforeEach
    void setUp() {
        menuItemRepository = new MenuItemRepository();
        menuItemRepository.add(menuItem1);
        menuItemRepository.add(menuItem2);
        menuItemRepository.add(menuItem3);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest() {
        List<MenuItem> menuItems = menuItemRepository.getAll();
        Collections.sort(menuItems);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem2.getName());
        softly.assertThat(menuItems.get(1).getName()).isEqualTo(menuItem1.getName());
        softly.assertThat(menuItems.get(2).getName()).isEqualTo(menuItem3.getName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("MenuItemRepository Tests")
    class MenuItemRepositoryTests {

        @Test
        @DisplayName("SortByNameDesc Test")
        void sortByNameDescTest() {
            List<MenuItem> menuItems = menuItemRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem3.getName());
            softly.assertThat(menuItems.get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItems.get(2).getName()).isEqualTo(menuItem2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("SortByPriceAsc")
        void sortByPriceAscTest() {
            List<MenuItem> menuItems = menuItemRepository.sortByPriceAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem3.getName());
            softly.assertThat(menuItems.get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItems.get(2).getName()).isEqualTo(menuItem2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("SortByPriceDesc")
        void sortByPriceDescTest() {
            List<MenuItem> menuItems = menuItemRepository.sortByPriceDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem2.getName());
            softly.assertThat(menuItems.get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItems.get(2).getName()).isEqualTo(menuItem3.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("CheckOneElementSort Test")
        void checkOneElementSortTest() {
            MenuItemRepository oneElementMenuItemRepository = new MenuItemRepository();
            oneElementMenuItemRepository.add(menuItem1);
            SoftAssertions softly = new SoftAssertions();
            List<MenuItem> oneItem = oneElementMenuItemRepository.sortByPriceAsc();
            softly.assertThat(oneItem.get(0).getName()).isEqualTo(menuItem1.getName());
        }

        @Test
        @DisplayName("findByNameTest")
        void findByNameTest() {
            List<MenuItem> menuItems = menuItemRepository.findByName("Taco");
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem3.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("increasePriceDueInflation")
        void increasePriceDueInflationTest() {
            List<MenuItem> menuItems = menuItemRepository.increasePriceDueInflation(0.15f);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getPrice()).isBetween(17.82499f, 17.825f);
            softly.assertAll();
        }

        @Test
        @DisplayName("findByPriceInRange")
        void findByPriceInRangeTest() {
            List<MenuItem> menuItems = menuItemRepository.findByPriceInRange(15f, 22f);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItems.get(0).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItems.get(1).getName()).isEqualTo(menuItem2.getName());
            softly.assertThat(menuItems).hasSize(2);
            softly.assertAll();
        }
    }
}
