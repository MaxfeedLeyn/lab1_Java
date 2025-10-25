package ua.delivery.repository;

import ua.delivery.model.Person;
import ua.delivery.model.Staff;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class StaffRepository extends GenericRepository<Staff> {

    private static final Logger logger = Logger.getLogger(StaffRepository.class.getName());

    public StaffRepository() {
        super(Staff::getFullName, "Staff");
    }

    public List<Staff> sortByNameDesc() {
        List<Staff> staffs = getAll();
        staffs.sort((s1, s2) -> s2.getFullName().compareTo(s1.getFullName()));
        logger.info("Sorted StaffRepository by name descending :" + staffs.size() + " items");
        return staffs;
    }

    public List<Staff> sortByLastNameAsc() {
        List<Staff> staffs = getAll();
        staffs.sort((s1, s2) -> s1.getLastName().compareTo(s2.getLastName()));
        logger.info("Sorted StaffRepository by last name ascending :" + staffs.size() + " items");
        return staffs;
    }

    public List<Staff> sortByLastNameDesc() {
        List<Staff> staffs = getAll();
        staffs.sort((s1, s2) -> s2.getLastName().compareTo(s1.getLastName()));
        logger.info("Sorted StaffRepository by last name descending :" + staffs.size() + " items");
        return staffs;
    }

}
