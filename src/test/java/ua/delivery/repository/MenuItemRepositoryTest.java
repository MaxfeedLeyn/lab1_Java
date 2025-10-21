package ua.delivery.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.assertj.core.api.SoftAssertions;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository Tests")
public class MenuItemRepositoryTest {

    private MenuItemRepository menuItemRepository;
    private MenuItem menuItem1, menuItem2, menuItem3;
    private List<MenuItem> menuItemList;

    @BeforeAll
    void setUpTestData() {
        menuItem1 = new MenuItem("Pasta", 15.5f, "Italian");
        menuItem2 = new MenuItem("Burger", 21.5f, "American");
        menuItem3 = new MenuItem("Taco", 10f, "Mexican");
    }

    @BeforeEach
    void setUp() {
        menuItemList = new ArrayList<>();
        menuItemList.add(menuItem1);
        menuItemList.add(menuItem2);
        menuItemList.add(menuItem3);
        menuItemRepository = new MenuItemRepository(menuItemList);
    }

    @Test
    @DisplayName("Default sort Test")
    void defaultSortTest() {
        Collections.sort(menuItemList);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(menuItemList.get(0).getName()).isEqualTo(menuItem2.getName());
        softly.assertThat(menuItemList.get(1).getName()).isEqualTo(menuItem1.getName());
        softly.assertThat(menuItemList.get(2).getName()).isEqualTo(menuItem3.getName());
        softly.assertAll();
    }

    @Nested
    @DisplayName("MenuItemRepository Tests")
    class MenuItemRepositoryTests {

        @Test
        @DisplayName("SortByNameDesc Test")
        void sortByNameDescTest() {
            menuItemRepository.sortByNameDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItemRepository.getItemsForTesting().get(0).getName()).isEqualTo(menuItem3.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(2).getName()).isEqualTo(menuItem2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("SortByPriceAsc")
        void sortByPriceAscTest() {
            menuItemRepository.sortByPriceAsc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItemRepository.getItemsForTesting().get(0).getName()).isEqualTo(menuItem3.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(2).getName()).isEqualTo(menuItem2.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("SortByPriceDesc")
        void sortByPriceDescTest() {
            menuItemRepository.sortByPriceDesc();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(menuItemRepository.getItemsForTesting().get(0).getName()).isEqualTo(menuItem2.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(1).getName()).isEqualTo(menuItem1.getName());
            softly.assertThat(menuItemRepository.getItemsForTesting().get(2).getName()).isEqualTo(menuItem3.getName());
            softly.assertAll();
        }

        @Test
        @DisplayName("CheckForNull Test")
        void checkForNullTest() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
               MenuItemRepository menuItemRepository = new MenuItemRepository(null);
            });
            assertTrue(exception.getMessage().contains("Can not be null"));
        }

        @Test
        @DisplayName("CheckOneElementSort Test")
        void checkOneElementSortTest() {
            List<MenuItem> tmp = new ArrayList<>();
            tmp.add(menuItem1);
            SoftAssertions softly = new SoftAssertions();
            MenuItemRepository testRepository = new MenuItemRepository(tmp);
            testRepository.sortByPriceAsc();
            softly.assertThat(testRepository.getItemsForTesting().get(0).getName()).isEqualTo(menuItem1.getName());
        }
    }
}
