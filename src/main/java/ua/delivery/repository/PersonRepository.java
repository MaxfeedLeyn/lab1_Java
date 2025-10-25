package ua.delivery.repository;


import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Order;
import ua.delivery.model.Person;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PersonRepository extends GenericRepository<Person> {

    private static final Logger logger = Logger.getLogger(PersonRepository.class.getName());

    public PersonRepository() {
        super(Person::getFullName, "Person");
    }

    public List<Person> sortByNameDesc(){
        List<Person> persons = getAll();
        persons.sort((p1, p2) -> p2.getFirstName().compareTo(p1.getFirstName()));
        logger.info("Sorted PersonRepository by name descending :" + persons.size() + " items");
        return persons;
    }

    public List<Person> sortByLastNameAsc(){
        List<Person> persons = getAll();
        persons.sort((c1, c2) -> c1.getLastName().compareTo(c2.getLastName()));
        logger.info("Sorted PersonRepository by name ascending :" + persons.size() + " items");
        return persons;
    }

    public List<Person> sortByLastNameDesc(){
        List<Person> persons = getAll();
        persons.sort((p1, p2) -> p2.getLastName().compareTo(p1.getLastName()));
        logger.info("Sorted PersonRepository by name descending :" + persons.size() + " items");
        return persons;
    }
}
