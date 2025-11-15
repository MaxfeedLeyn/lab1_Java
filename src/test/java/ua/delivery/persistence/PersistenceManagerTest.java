package ua.delivery.persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import ua.delivery.config.AppConfig;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;
import ua.delivery.model.Person;
import ua.delivery.repository.MenuItemRepository;
import ua.delivery.repository.PersonRepository;
import ua.delivery.exception.DataSerializationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("PersistenceManager Tests")
public class PersistenceManagerTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Serialization Tests")
    class SerializationTests {

        private PersonRepository personRepository;
        private Person person1, person2, person3;
        private AppConfig appConfig;
        private PersistenceManager manager;

        @BeforeAll
        void setUpTestData(){
            person1 = new Person("Jane", "Doe", "St. Central 1");
            person2 = new Person("Robert", "Polson", "St. Central 2");
            person3 = new Person("Sao", "Paulo", "St. Central 3");
        }

        @BeforeEach
        void setUp(){
            personRepository = new PersonRepository();
            personRepository.add(person1);
            personRepository.add(person2);
            personRepository.add(person3);
        }

        @Test
        @DisplayName("Serialization with no filePath provided Test")
        void testSerializationWithNoFilePathTest(){
            assertDoesNotThrow(() -> {
                appConfig = new AppConfig();
                PersistenceManager manager = new PersistenceManager(appConfig);
                manager.save(personRepository.getAll(), "persons", Person.class, "JSON");
            });
            assertDoesNotThrow(() -> {
               appConfig = new AppConfig();
               PersistenceManager manager = new PersistenceManager(appConfig);
               manager.save(personRepository.getAll(), "persons", Person.class, "YAML");
            });
        }

        @Test
        @DisplayName("Serialization with filePath provided Test")
        void testSerializationWithFilePathTest(){
            assertDoesNotThrow(() -> {
                appConfig = new AppConfig("config.properties");
                PersistenceManager manager = new PersistenceManager(appConfig);
                manager.save(personRepository.getAll(), "persons", Person.class, "YAML");
            });
            assertDoesNotThrow(() -> {
               appConfig = new AppConfig("config.properties");
               PersistenceManager manager = new PersistenceManager(appConfig);
               manager.save(personRepository.getAll(), "persons", Person.class, "JSON");
            });
        }

        @Test
        @DisplayName("Serialization with non existed filePath Test")
        void testSerializationWithNonExistedFilePathTest(){
            InvalidDataException io = assertThrows(InvalidDataException.class, () -> {
                appConfig = new AppConfig("config.txt");
            });
            assertTrue(io.getMessage().contains("Unable to find configuration file"));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Deserialization Tests")
    class DeserializationTests {

        private MenuItemRepository menuItemRepository;
        private MenuItem menuItem1, menuItem2, menuItem3;

        private AppConfig appConfig;
        private PersistenceManager manager;

        @BeforeAll
        void setUpTestData(){
            menuItem1 = new MenuItem("Pasta", 10.0f, "Italian");
            menuItem2 = new MenuItem("Filet-O-Fish", 20.0f, "English");
            menuItem3 = new MenuItem("burrito", 25.0f, "Mexican");
        }

        @BeforeEach
        void setUp(){
            menuItemRepository = new MenuItemRepository();
            menuItemRepository.add(menuItem1);
            menuItemRepository.add(menuItem2);
            menuItemRepository.add(menuItem3);
        }

        @Test
        @DisplayName("Success deserialization Test Person")
        void testDeserializationSuccessTest(){
            assertDoesNotThrow(() -> {
                appConfig = new AppConfig();
                PersistenceManager manager = new PersistenceManager(appConfig);
                manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "YAML");
            });
            assertDoesNotThrow(() -> {
                appConfig = new AppConfig();
                PersistenceManager manager = new PersistenceManager(appConfig);
                manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "JSON");
            });
        }

// TODO
//        @Test
//        @DisplayName("Success deserialization Test MenuItem")
//        void testDeserializationSuccessTest2(){
//            assertDoesNotThrow(() -> {
//                appConfig = new AppConfig();
//                PersistenceManager manager = new PersistenceManager(appConfig);
//                manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "YAML");
//                List<MenuItem> list = manager.load("menuItems", MenuItem.class, "YAML");
//            });
//
//            assertDoesNotThrow(() -> {
//               appConfig = new AppConfig();
//               PersistenceManager manager = new PersistenceManager(appConfig);
//               manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "JSON");
//               List<MenuItem> list = manager.load("menuItems", MenuItem.class, "JSON");
//            });
//        }

        @Test
        @DisplayName("Try to load another format from serializers")
        void testDeserializationWithAnotherFormatTest(){
            assertThrows( DataSerializationException.class, () -> {
                appConfig = new AppConfig();
                PersistenceManager manager = new PersistenceManager(appConfig);
                manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "YAML");
                manager.load("menuItems", MenuItem.class, "TXT");
            });
        }
// TODO
//        @Test
//        @DisplayName("Compare equality of elements after deserialization")
//        void testCompareEquality() throws DataSerializationException {
//            SoftAssertions softly = new SoftAssertions();
//            appConfig = new AppConfig();
//            PersistenceManager manager = new PersistenceManager(appConfig);
//            manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "JSON");
//            List<MenuItem> list = manager.load("menuItems", MenuItem.class, "JSON");
//            softly.assertThat(list).hasSize(3);
//            softly.assertThat(list.get(0)).isEqualTo(menuItem1);
//            softly.assertThat(list.get(1)).isEqualTo(menuItem2);
//            softly.assertThat(list.get(2)).isEqualTo(menuItem3);
//            softly.assertAll();
//        }
    }
}
