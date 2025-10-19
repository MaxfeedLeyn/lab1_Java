package ua.delivery.model;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.PersonUtils;

import java.util.Objects;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String address;

    public Person(){
    }

    public Person(String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(email);
    }

    public Person(Person person){
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.address = person.address;
    }

    protected String getFullName() {
        return PersonUtils.formatName(firstName, lastName);
    }

    public void setFirstName(String firstName) {
//        if (firstName == null || firstName.isEmpty())
//            throw new InvalidDataException("First name cannot be empty");
        firstName = PersonUtils.capitalizeText(firstName);
        if (PersonUtils.isValidName(firstName)) {
            this.firstName = PersonUtils.capitalizeText(firstName);
        }
    }

    public void setLastName(String lastName) {
//        if (lastName == null || lastName.isEmpty())
//            throw new InvalidDataException("Last name cannot be empty");
        lastName = PersonUtils.capitalizeText(lastName);
        if(PersonUtils.isValidName(lastName)) {
            this.lastName = PersonUtils.capitalizeText(lastName);
        }
    }

    public void setAddress(String address) {
        if(address != null  && !address.isEmpty()) {
            address = PersonUtils.formatAddress(address);
            if(PersonUtils.isValidAddress(address)) {
                this.address = address;
            }
        }
//        else
//            throw new InvalidDataException("Address cannot be empty");
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

    public static Person createPerson(String firstName, String lastName, String addressName) {
        if(PersonUtils.isValidName(firstName) &&
                PersonUtils.isValidName(lastName) &&
                PersonUtils.isValidAddress(addressName)) {
            return new Person(firstName, lastName, addressName);
        }
        throw new InvalidDataException("The attempt to create a Client has failed, check firstName and lastName and address(St. NameofTheStreet 1-1000)!");
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }
}
