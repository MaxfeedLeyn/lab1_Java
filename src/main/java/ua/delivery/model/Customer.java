package ua.delivery.model;

import ua.delivery.util.CustomerUtils;
import ua.delivery.util.PersonUtils;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.ValidationHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Objects;

public record Customer(String firstName, String lastName, String address) {

    public Customer(){
        this("null", "null", "St. null 1");
    }

        private static final Logger logger = Logger.getLogger(Customer.class.getName());

        public Customer{
        if(!PersonUtils.isValidName(firstName)){
            String errorMassage = "First name is invalid";
            logger.log(Level.SEVERE, errorMassage);
            throw new InvalidDataException(errorMassage);
        }
        if(!PersonUtils.isValidName(lastName)){
            String errorMassage = "Last name is invalid";
            logger.log(Level.SEVERE, errorMassage);
            throw new InvalidDataException(errorMassage);
        }

        if (address != null)
            address = PersonUtils.formatAddress(address);

        if(!PersonUtils.isValidAddress(address)){
            String errorMassage = "Address is invalid " + address + "(must be: St. NameofTheStreet 1-1000)";
            logger.log(Level.SEVERE, errorMassage);
            throw new InvalidDataException(errorMassage);
        }

        logger.log(Level.INFO, "Customer has been created: {0} {1}, Address = {2}",
                new Object[]{firstName, lastName, address});
    }

    public static Customer createCustomer(String firstName, String lastName, String address) {
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address)) {
            logger.log(Level.INFO, "Customer created");
            return new Customer(firstName, lastName, address);
        }
        throw new InvalidDataException("The attempt to create a Client has failed, check firstName and lastName and address(St. NameofTheStreet 1-1000)!");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address  +
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
        return Objects.hash(firstName, lastName, address);
    }
}
