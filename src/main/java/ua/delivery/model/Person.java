package ua.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.PersonUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.*;
import ua.delivery.util.ValidationUtils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Comparable<Person> {
    @NotBlank(message = "First name cannot be null or blank")
    @Pattern(
            regexp = "^[a-zA-Z\\s\\-']{2,50}$",
            message = "First name must be 2-50 character long and contain only letters, hyphens, or apostrophes"
    )
    protected String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Pattern(
            regexp = "^[a-zA-Z\\s\\-']{2,50}$",
            message = "Last name must be 2-50 character long and contain only letters, hyphens, or apostrophes"
    )
    protected String lastName;

    @NotBlank(message = "Address cannot be null or blank")
    @Pattern(
            regexp = "^(?i)St\\.?\\s+[\\p{L}0-9.'\\-\\s]+\\s+\\d+[A-Za-z0-9\\/-]*$",
            message = "Address must match pattern St. NameOfStreet number"
    )
    protected String address;

    private static final Logger logger = Logger.getLogger(Person.class.getName());


    public Person(){
        this.firstName = "Jane";
        this.lastName = "Doe";
        this.address = "St. Central 1";
    }

    public Person(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") String email) {
        this.firstName = PersonUtils.capitalizeText(firstName);
        this.lastName = PersonUtils.capitalizeText(lastName);
        this.address = PersonUtils.formatAddress(email);
        ValidationUtils.validate(this);
    }

    public Person(Person person){
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.address = person.address;
    }

    public String getFullName() {
        return PersonUtils.formatName(firstName, lastName);
    }

    public void setFirstName(String firstName) {
        if (firstName != null)
            firstName = PersonUtils.capitalizeText(firstName);

        String oldValue = this.firstName;
        this.firstName = firstName;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change First name to " + firstName);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid First name: " + firstName);
            this.firstName = oldValue;
            throw e;
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null)
            lastName = PersonUtils.capitalizeText(lastName);

        String oldValue = this.lastName;
        this.lastName = lastName;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change Last name to " + lastName);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid Last name: " + lastName);
            this.lastName = oldValue;
            throw e;
        }
    }

    public void setAddress(String address) {
        if (address != null)
            address = PersonUtils.formatAddress(address);

        String oldValue = this.address;
        this.address = address;

        try {
            ValidationUtils.validate(this);
            logger.log(Level.INFO, "Change Last name to " + lastName);
        }
        catch (InvalidDataException e) {
            logger.log(Level.WARNING, "Invalid Last name: " + lastName);
            this.lastName = oldValue;
            throw e;
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
        Person person = new Person(firstName, lastName, addressName);
        ValidationUtils.validate(person);
        return person;
    }

    @Override
    public int compareTo(Person o) {

        return this.getFullName().compareTo(o.getFullName());
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
