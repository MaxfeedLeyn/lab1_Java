package ua.university.model;

import ua.university.util.PersonUtils;
import ua.university.util.CustomerUtils;

import java.util.Objects;

public class Customer extends Person {

    public Customer(){
        super();
    }

    public Customer(String firstName, String lastName, String address) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
    }

    public static Customer createCustomer(String firstName, String lastName, String address) {
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address)) {
            return new Customer(firstName, lastName, address);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName=" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", address=" + address  +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
