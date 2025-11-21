package ua.delivery.model;

import ua.delivery.util.PersonUtils;
import ua.delivery.util.StaffUtils;
import jakarta.validation.constraints.*;
import ua.delivery.util.ValidationUtils;

import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Staff extends Person {

    @Min(value = 0, message = "Rank must be at least 0")
    @Max(value = 5, message = "Max rank can be only 5")
    private float rankOfDelivery = 0;

    private static final Logger logger = Logger.getLogger(Staff.class.getName());

    public Staff(String firstName, String lastName, String address) {
        super(firstName, lastName, address);
        logger.log(Level.INFO, "Staff created");
    }

    public Staff(String firstName, String lastName, String address, float rankOfDelivery) {
        super(firstName, lastName, address);
        this.rankOfDelivery = rankOfDelivery;
        ValidationUtils.validate(this);
        logger.log(Level.INFO, "Staff created");
    }

    public Staff(){
        super();
    }

    public Staff(Staff staff){
        super(staff);
        this.rankOfDelivery = staff.rankOfDelivery;
    }

    @Override
    public String getFullName(){
        if (firstName == null || lastName == null)
            return null;
        return PersonUtils.formatName(firstName, lastName) + "; Rank of delivery: " +  rankOfDelivery;
    }

    public static Staff create(String firstName, String lastName, String address){
        Staff staff = new Staff(firstName, lastName, address);
        ValidationUtils.validate(staff);
        logger.log(Level.INFO, "Staff created");
        return staff;
    }

    public static Staff create(String firstName, String lastName, String address, float rankOfDelivery){
        Staff staff = new Staff(firstName, lastName, address, rankOfDelivery);
        ValidationUtils.validate(staff);
        logger.log(Level.INFO, "Staff created");
        return staff;
    }

    public float getRankOfDelivery() {
        return rankOfDelivery;
    }

    public void setRankOfDelivery(float rankOfDelivery) {
        if (StaffUtils.isValidRanking(rankOfDelivery)) {
            this.rankOfDelivery = rankOfDelivery;
        }
    }

    @Override
    public String toString() {
        return "Staff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" +  lastName + '\'' +
                ", address='" + address + '\'' +
                ", rankOfDelivery='" + rankOfDelivery + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(firstName, staff.firstName) &&
                Objects.equals(lastName, staff.lastName) &&
                Objects.equals(address, staff.address) &&
                Objects.equals(rankOfDelivery, staff.rankOfDelivery);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
