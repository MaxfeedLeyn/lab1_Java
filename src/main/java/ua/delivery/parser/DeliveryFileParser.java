package ua.delivery.parser;


import ua.delivery.Main;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DeliveryFileParser {
    public static final Logger logger = Logger.getLogger(DeliveryFileParser.class.getName());

    private static int depth = 0;

    public static List<Delivery> deliveries = new ArrayList<>();
    private static Delivery delivery;
    private static Person tmpPerson = null, person = null;
    private static List<MenuItem> menuItems = new ArrayList<>();
    private static MenuItem tmpMenuItem = null;
    private static Order tmpOrder = null, order = null;
    private static Staff tmpStaff = null, staff = null;

    private static Object resultJSON = null;
    private static Date tmpOrderDate = null;

    /**
     * Reads subjects from JSON file format
     */
    public static List<Delivery> readDeliveries(String filePath) throws InvalidDataException {
        try{
            logger.log(Level.INFO, "Start to parse deliveries from file: {0}", filePath);

            InputStream is = Main.class.getResourceAsStream(filePath);
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + filePath);
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            resultJSON = parseJson(json);
            return deliveries;
        }
        catch (FileNotFoundException e){
            String errorMassage = "File not found" + filePath;
            logger.log(Level.SEVERE, errorMassage, e);
            throw new InvalidDataException(errorMassage, e);
        }
        catch (IOException e){
            String errorMassage = "Error reading file " + filePath;
            logger.log(Level.SEVERE, errorMassage, e);
            throw new InvalidDataException(errorMassage, e);
        }
        catch (SecurityException e){
            String errorMassage = "Access denied to file:" + filePath;
            logger.log(Level.SEVERE, errorMassage, e);
            throw new InvalidDataException(errorMassage, e);
        }
    }

    // For file that not in resources
    public static String readFileAsString(String filePath) throws InvalidDataException {
        logger.log(Level.INFO, "Start to parse deliveries from file: {0}", filePath);
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        catch (IOException e){
            String errorMassage = "Error reading file: " + filePath;
            logger.log(Level.SEVERE, errorMassage, e);
            throw new InvalidDataException(errorMassage, e);
        }
        return content.toString();
    }

    public static Object parseJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseJsonObject(json);
        } else if (json.startsWith("[")) {
            return parseJsonArray(json);
        } else if (json.startsWith("\"") && json.endsWith("\"")) {
            return json.substring(1, json.length() - 1);
        } else if (json.equalsIgnoreCase("true") || json.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(json);
        } else if (json.matches("-?\\d+(\\.\\d+)?")) {
            return json.contains(".") ? Double.parseDouble(json) : Integer.parseInt(json);
        } else {
            logger.log(Level.WARNING, "Invalid json object: {0}", json);
            throw new IllegalArgumentException("Invalid JSON value: " + json);
        }
    }

    public static Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        json = json.substring(1, json.length() - 1).trim(); // Remove curly braces
        String[] entries = splitJsonEntries(json);

        if(depth == 1){
            addToDelivery();
        }

        ++depth;
        for (String entry : entries) {
            String[] keyValue = entry.split(":", 2);
            String key = parseJson(keyValue[0]).toString();

            initializeUsingKey(key);

            Object value = parseJson(keyValue[1]);

            InitializeKey(key, value);

            map.put(key, value);
        }
        --depth;
        if(depth == 0){
            addToDelivery();
        }
        return map;
    }

    private static void initializeUsingKey(Object key) throws InvalidDataException{
        if(depth == 1){
            if(!key.equals("delivery")){
                throw new InvalidDataException("Invalid key used in JSON object: " + key);
            }
        }
        else if(depth == 2){
            switch (key.toString()) {
                case "order" -> {
                    if (tmpStaff != null) {
                        staff = tmpStaff;
                        logger.log(Level.INFO, "Successfully parse staff : {0}", staff);
                    }
                    tmpOrder = new Order();
                    logger.log(Level.INFO, "Start parsing order");
                    tmpStaff = null;
                }
                case "staff" -> {
                    if (tmpOrder != null) {
                        if(tmpOrderDate == null) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(2025, Calendar.SEPTEMBER, 29);
                            Date orderDate = cal.getTime();
                            order = new Order(new Customer(person.getFirstName(), person.getLastName(), person.getAddress()),
                                    menuItems.toArray(new MenuItem[0]), orderDate);
                        }
                        else
                            order = new Order(new Customer(person.getFirstName(), person.getLastName(), person.getAddress()),
                                    menuItems.toArray(new MenuItem[0]), tmpOrderDate);
                        logger.log(Level.INFO, "Successfully parse order: {0}", order);
                    }
                    tmpOrder = null;
                    tmpStaff = new Staff();
                    logger.log(Level.INFO, "Start parsing staff");
                }
                case "date" -> {
                    if (tmpStaff != null) {
                        staff = tmpStaff;
                        logger.log(Level.INFO, "Successfully parse staff : {0}", staff);
                    }
                    if (tmpOrder != null) {
                        if(tmpOrderDate == null) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(2025, Calendar.SEPTEMBER, 29);
                            Date orderDate = cal.getTime();

                            order = new Order(new Customer(person.getFirstName(), person.getLastName(), person.getAddress()),
                                    menuItems.toArray(new MenuItem[0]), orderDate);
                        }
                        else{
                            order = new Order(new Customer(person.getFirstName(), person.getLastName(), person.getAddress()),
                                    menuItems.toArray(new MenuItem[0]), tmpOrderDate);
                            tmpOrderDate = null;
                        }
                        logger.log(Level.INFO, "Successfully parse order: {0}", order);
                    }
                    tmpOrder = null;
                    tmpStaff = null;
                }
                default -> throw new InvalidDataException("Invalid key used in delivery JSON object: " + key);
            }
        }
        else if(depth == 3){
            if(tmpOrder != null){
                switch (key.toString()) {
                    case "customer" -> {
                        tmpPerson = new Person();
                        logger.log(Level.INFO, "Starting parse person");
                        tmpMenuItem = null;
                    }
                    case "menuItem" -> {
                        if (tmpPerson != null) {
                            person = tmpPerson;
                            logger.log(Level.INFO, "Successfully parse customer in order: {0}", person );
                        }
                        tmpMenuItem = new MenuItem();
                        logger.log(Level.INFO, "Starting parse menuItem in order");
                        tmpPerson = null;
                    }
                    case "date" -> {
                        if (tmpPerson != null) {
                            person = tmpPerson;
                            logger.log(Level.INFO, "Successfully parse person: {0}", person);
                        }

                        tmpPerson = null;
                        tmpMenuItem = null;
                    }
                    default -> throw new InvalidDataException("Invalid key used in order JSON object: " + key);
                }
            }
        }
    }

    private static void InitializeKey(Object key, Object value){
        if(depth == 2){
            if(key.equals("date")){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                try{
                    tmpOrderDate = formatter.parse(value.toString());
                }
                catch (ParseException e){
                    throw new InvalidDataException("Invalid date in Order: " + value);
                }
            }
        }
        else if(depth == 3){
            if(tmpStaff != null){
                switch (key.toString()) {
                    case "firstName" -> tmpStaff.setFirstName(value.toString());
                    case "lastName" -> tmpStaff.setLastName(value.toString());
                    case "address" -> tmpStaff.setAddress(value.toString());
                    default -> throw new InvalidDataException("Invalid key used in order: " + key);
                }
            }
            else if(tmpPerson == null && tmpMenuItem == null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                try{
                    tmpOrderDate = formatter.parse(value.toString());
                }
                catch (ParseException e){
                    throw new InvalidDataException("Invalid date in order: " + value);
                }
            }
        }
        else if(depth == 4){
            if(tmpPerson != null){
                switch (key.toString()) {
                    case "firstName" -> tmpPerson.setFirstName(value.toString());
                    case "lastName" -> tmpPerson.setLastName(value.toString());
                    case "address" -> tmpPerson.setAddress(value.toString());
                    default -> throw new InvalidDataException("Invalid key person in order: " + key);
                }
            }
            else if(tmpMenuItem != null){
                switch (key.toString()) {
                    case "name" -> tmpMenuItem.setName(value.toString());
                    case "price" -> tmpMenuItem.setPrice(Float.parseFloat(value.toString()));
                    case "category" -> tmpMenuItem.setCategory(value.toString());
                    default -> throw new InvalidDataException("Invalid key person in order:" + key);
                }

                if(tmpMenuItem.isComplete()) {
                    menuItems.add(tmpMenuItem);
                    logger.log(Level.INFO, "Successfully parse menuItem in order: {0}", tmpMenuItem);
                    tmpMenuItem = new MenuItem();
                }
            }
            else if(tmpMenuItem == null && tmpPerson == null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                try{
                    tmpOrderDate = formatter.parse(value.toString());
                }
                catch (ParseException e){
                    throw new InvalidDataException("Invalid date in Order: " + value);
                }
            }
            else throw new InvalidDataException("Invalid key used in order: " + key);
        }
    }

    private static void addToDelivery(){
        if(staff != null && order != null) {
            if(tmpOrderDate == null){
                Calendar cal = Calendar.getInstance();
                cal.set(2025, Calendar.SEPTEMBER, 29);
                Date orderDate = cal.getTime();
                deliveries.add(new Delivery(order, staff, orderDate));
            }
            else{
                deliveries.add(new Delivery(order, staff, tmpOrderDate));
            }
            logger.log(Level.FINE, "Delivery successfully added: " + deliveries);
            staff = null; order = null; menuItems.clear(); tmpOrderDate = null;
        }
        else
            logger.log(Level.WARNING, "Delivery failed adding: " + staff + ", " + order + ", " + tmpOrderDate);
    }

    private static String[] splitJsonEntries(String json) {
        List<String> entries = new ArrayList<>();
        int bracketCount = 0, braceCount = 0;
        boolean inQuotes = false;
        StringBuilder entry = new StringBuilder();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                }
            }

            if (c == ',' && !inQuotes && braceCount == 0 && bracketCount == 0) {
                entries.add(entry.toString().trim());
                entry.setLength(0);
            } else {
                entry.append(c);
            }
        }

        if (entry.length() > 0) {
            entries.add(entry.toString().trim());
        }

        return entries.toArray(new String[0]);
    }

    public static List<Object> parseJsonArray(String json) {
        List<Object> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim(); // Remove square brackets
        String[] items = splitJsonEntries(json);
        for (String item : items) {
            list.add(parseJson(item));
        }
        return list;
    }

    public static void printJson(Object json, String indent) {
        if (json instanceof Map<?, ?> map) {
            System.out.println(indent + "{");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.print(indent + "  \"" + entry.getKey() + "\": ");
                printJson(entry.getValue(), indent + "  ");
            }
            System.out.println(indent + "}");
        } else if (json instanceof List<?> list) {
            System.out.println(indent + "[");
            for (Object item : list) {
                printJson(item, indent + "  ");
            }
            System.out.println(indent + "]");
        } else {
            System.out.println(indent + json);
        }
    }
}
