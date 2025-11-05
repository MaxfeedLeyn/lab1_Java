package ua.delivery;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.delivery.config.AppConfig;
import ua.delivery.persistence.PersistenceManager;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.repository.GenericRepository;
import ua.delivery.repository.MenuItemRepository;
import ua.delivery.serializer.DataSerializer;
import ua.delivery.serializer.JsonDataSerializer;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.parser.*;
import ua.delivery.service.*;
import ua.delivery.model.*;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    public static void main(String[] args){
        try {
            AppConfig appConfig = new AppConfig();
            PersistenceManager manager = new PersistenceManager(appConfig);

            System.out.println("\n" + "=".repeat(70) + "\n");

            List<Person> personRepo = new ArrayList<>();

            personRepo.add(new Person("Mark", "Tsukerberg", "St. Central 1"));
            personRepo.add(new Person("Sau", "Paulo", "St. Central 2"));
            personRepo.add(new Person("Robert", "Polson", "St. Central 3"));

            manager.save(personRepo, "persons", Person.class, "JSON");
            System.out.println("Saved to: " + appConfig.getJsonFilePath("persons"));

            manager.save(personRepo, "persons", Person.class, "YAML");
            System.out.println("Saved to: " + appConfig.getYamlFilePath("persons"));

            GenericRepository<Person> loadedFromJson = new GenericRepository<>(
                    person -> person.getFirstName(),
                    "Person"
            );
            List<Person> personList = manager.load("persons", Person.class, "JSON");
            List<Person> personList2 = manager.load("persons", Person.class, "YAML");
            for(Person person : personList){
                System.out.println(person);
            }

            for(Person person : personList2){
                System.out.println(person);
            }


            MenuItem menuItem1 = new MenuItem("Pasta", 10.0f, "Italian");
            MenuItem menuItem2 = new MenuItem("Filet-O-Fish", 20.0f, "English");
            MenuItem menuItem3 = new MenuItem("burrito", 25.0f, "Mexican");
            MenuItemRepository menuItemRepository = new MenuItemRepository();
            menuItemRepository.add(menuItem1);
            menuItemRepository.add(menuItem2);
            menuItemRepository.add(menuItem3);
            manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "YAML");
            List<MenuItem> list = manager.load("menuitems", MenuItem.class, "YAML");
        }
        catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
        catch (DataSerializationException e){
            System.out.println(e.getMessage());
        }
    }
}
