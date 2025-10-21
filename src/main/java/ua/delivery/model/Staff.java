package ua.delivery.model;

import ua.delivery.util.PersonUtils;
import ua.delivery.util.StaffUtils;

import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Staff extends Person {
    private float rankOfDelivery = 0;

    private static final Logger logger = Logger.getLogger(Staff.class.getName());

    public Staff(String firstName, String lastName, String address) {
        super(firstName, lastName, address);
        logger.log(Level.INFO, "Staff created");
    }

    public Staff(String firstName, String lastName, String address, float rankOfDelivery) {
        super(firstName, lastName, address);
        setRankOfDelivery(rankOfDelivery);
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
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address)){
            logger.log(Level.INFO, "Staff created");
            return new Staff(firstName, lastName, address);
        }
        return null;
    }

    public static Staff create(String firstName, String lastName, String address, float rankOfDelivery){
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address) && StaffUtils.isValidRanking(rankOfDelivery)){
            logger.log(Level.INFO, "Staff created");
            return new Staff(firstName, lastName, address, rankOfDelivery);
        }
        return null;
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
