package ua.delivery.model;

import ua.delivery.util.PersonUtils;

import java.util.Objects;

public record Customer(String firstName, String lastName, String address) {

    public Customer(){
        this(null, null, null);
    }

        public Customer{
        boolean hasCriticalErrors = false;
        if(!PersonUtils.isValidName(firstName)){
            hasCriticalErrors = true;
            System.out.println("Error when create Customer: First name is invalid");
        }
        if(!PersonUtils.isValidName(lastName)){
            hasCriticalErrors = true;
            System.out.println("Error when create Customer: Last name is invalid");
        }

        if (address != null)
            address = PersonUtils.formatAddress(address);

        if(!PersonUtils.isValidAddress(address)){
            hasCriticalErrors = true;
            System.out.println("Error when create Customer: Address is invalid " + address + "(must be: St. NameofTheStreet 1-1000)");
        }

        if (hasCriticalErrors) System.out.println("Customer was created with error!");
    }

    public static Customer createCustomer(String firstName, String lastName, String address) {
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(address)) {
            return new Customer(firstName, lastName, address);
        }
        return null;
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
