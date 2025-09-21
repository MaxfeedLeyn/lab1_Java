package ua.university.model;

import ua.university.util.PersonUtils;
import ua.university.util.StaffUtils;

import java.util.Objects;

public class Staff  extends Person{
    private float rankOfDelivery;

    public Staff(){
        super();
    }

    public Staff(String firstName, String lastName, String address) {
        super(firstName, lastName, address);
    }

    @Override
    public String getFullName(){
        return PersonUtils.formatName(firstName, lastName) + ", Rank of delivery: " +  rankOfDelivery;
    }

    public static Staff create(String firstName, String lastName, String address){
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address)){
            return new Staff(firstName, lastName, address);
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
                "firstName=" + firstName + '\'' +
                ", lastName" +  lastName + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(firstName, staff.firstName) &&
                Objects.equals(lastName, staff.lastName) &&
                Objects.equals(address, staff.address);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
