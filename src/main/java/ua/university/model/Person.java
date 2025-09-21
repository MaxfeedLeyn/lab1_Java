package ua.university.model;

import ua.university.util.PersonUtils;

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

    protected String getFullName() {
        return PersonUtils.formatName(firstName, lastName);
    }

    public void setFirstName(String firstName) {
        if (PersonUtils.isValidName(firstName)) {
            this.firstName = PersonUtils.capitalizeText(firstName);
        }
    }

    public void setLastName(String lastName) {
        if(PersonUtils.isValidName(lastName)) {
            this.lastName = PersonUtils.capitalizeText(lastName);
        }
    }

    public void setAddress(String address) {
        if(address != null) {
            address = PersonUtils.formatAddress(address);
            if(PersonUtils.isValidAddress(address)) {
                this.address = address;
            }
        }
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
        return null;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName=" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", address=" + address +
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
