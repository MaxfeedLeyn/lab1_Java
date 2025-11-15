package ua.delivery.model;

import ua.delivery.util.CustomerUtils;
import ua.delivery.util.PersonUtils;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.ValidationHelper;
import jakarta.validation.constraints.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import ua.delivery.util.ValidationUtils;

import java.util.Objects;

public record Customer(
        @NotBlank(message = "First name cannot be null or blank")
        @Pattern(
                regexp = "^[a-zA-Z\\s\\-']{2,50}$",
                message = "First name must be 2-50 character long and contain only letters, hyphens, or apostrophes"
        )
        String firstName,

        @NotBlank(message = "Last name cannot be null or blank")
        @Pattern(
                regexp = "^[a-zA-Z\\s\\-']{2,50}$",
                message = "Last name must be 2-50 character long and contain only letters, hyphens, or apostrophes"
        )
        String lastName,

        @NotBlank(message = "Address cannot be null or blank")
        @Pattern(
                regexp = "^(?i)St\\.?\\s+[\\p{L}0-9.'\\-\\s]+\\s+\\d+[A-Za-z0-9\\/-]*$",
                message = "Address must match pattern St. NameOfStreet number"
        )
        String address) implements Comparable<Customer>{

    public Customer(){
        this("Jane", "Doe", "St. Central 1");
    }

        private static final Logger logger = Logger.getLogger(Customer.class.getName());

    public Customer(String firstName, String lastName, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        if (address != null)
            this.address  = PersonUtils.formatAddress(address);
        else
            this.address  = "";
        ValidationUtils.validate(this);
        logger.log(Level.INFO, "Customer has been created: {0} {1}, Address = {2}",
                new Object[]{firstName, lastName, address});
    }

    public static Customer createCustomer(String firstName, String lastName, String address) {
        Customer customer = new Customer(firstName, lastName, address);
        ValidationUtils.validate(customer);
        return customer;
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

    public String getFullName() {
            return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Customer o) {
            return this.getFullName().compareTo(o.getFullName());
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
