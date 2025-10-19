package ua.delivery.parser;


import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CustomerFileParser {
    private static final Logger logger = Logger.getLogger(CustomerFileParser.class.getName());

    /**
     * Reads customer from CSV file format: firstName,lastName,address
     * Example: "Mark,Tsukenberg,St. Center 1"
     */
    public static List<Customer> readCustomers(String filepath) throws InvalidDataException {
        List<Customer> customers = new ArrayList<>();

        try{
            logger.log(Level.INFO, "Starting to parse groups from file: {0}", filepath);

            List<String> lines = Files.readAllLines(Path.of(filepath));

            for(int lineNumber = 0; lineNumber < lines.size(); lineNumber++){
                String line = lines.get(lineNumber).trim();

                if(line.isEmpty() || line.startsWith("#"))
                    continue;

                try{
                    Customer customer = parseCustomerFromLine(line, lineNumber + 1);
                    customers.add(customer);
                    logger.log(Level.FINE, "Successfully parsed customer from line{0}: {1}",
                            new Object[]{lineNumber + 1, customer.getFirstName() + " " + customer.firstName()});
                }
                catch(InvalidDataException e){
                    logger.log(Level.WARNING, "Failed to parse Customer from line{0}: {1}",
                            new Object[]{lineNumber + 1, e.getMessage()});
                }
            }

            logger.log(Level.INFO, "Successfully parsed {0} customers from file", customers.size());
            return customers;
        }
        catch (FileNotFoundException e) {
            String errorMessage = "File not found: " + filepath;
            logger.log(Level.SEVERE, errorMessage, e);
            throw new InvalidDataException(errorMessage, e);
        }
        catch (IOException e){
            String errorMessage = "Error reading file: " + filepath;
            logger.log(Level.SEVERE, errorMessage, e);
            throw new InvalidDataException(errorMessage, e);
        }
        catch (SecurityException e){
            String errorMessage = "Access denied to file: " + filepath;
            logger.log(Level.SEVERE, errorMessage, e);
            throw new InvalidDataException(errorMessage, e);
        }
    }

    public static Customer parseCustomerFromLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");

        if(parts.length != 3){
            throw new InvalidDataException("Line " + lineNumber +
                    ". Expected format 'firstName, lastName, address' but got: " + line);
        }
        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        String address = parts[2].trim();

        return new Customer(firstName, lastName, address);
    }
}
