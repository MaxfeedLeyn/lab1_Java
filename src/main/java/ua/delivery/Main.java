package ua.delivery;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.parser.*;
import ua.delivery.service.*;
import ua.delivery.model.*;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static double calculate(double value) {
        return Math.sqrt(Math.sin(Math.log(value)));
    }


    public static void main(String[] args){
        try {
            MenuItem[] menuItems = new MenuItem[2];
            menuItems[0] = new MenuItem("Pizza", 10, "Italic");
            menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
            System.out.println(menuItems[1]);

            // Customer success
            Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
            System.out.println(customer.getAddress());

            // Order success
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.OCTOBER, 29);
            Date orderDate = cal.getTime();
            Order order = new Order(customer, menuItems, orderDate);
            System.out.println(order);

            // Delivery success
            Staff staff = new Staff("Henry", "Griffin", "St. Independence 11");
            System.out.println(staff.getFullName());
            Delivery delivery1 = new Delivery(order, staff, orderDate);
            Delivery delivery2 = new Delivery(order, staff, orderDate);
            System.out.println(delivery1.equals(delivery1));

            List<Delivery> deliveries = new ArrayList<>();

            deliveries = DeliveryFileParser.readDeliveries("/new.json");
            for (Delivery delivery : deliveries) {
                System.out.println("\n" + delivery.toString());
            }

            OrderStatus status = OrderStatus.CONFIRMED;

            OrderDelivery orderDelivery = new OrderDelivery(status, customer, delivery1);

            CuisineType cuisineType = CuisineType.ITALIAN;
            Restaurant restaurant = new Restaurant("La felicita", cuisineType.toString(), "St. Central 1");

            //Compare stream and parallel stream
            List<Double> items;
            Random rand = new Random();
            items = rand.doubles(1000).mapToObj(i -> i)
                    .collect(Collectors.toList());
            long startTime = System.nanoTime();
            double sumStream = items.stream()
                    .map(Main::calculate)
                    .reduce(0d, Double::sum);
            System.out.println("1000 elements:");
            long stopTime = System.nanoTime();
            System.out.print("Steam time: ");
            System.out.println(stopTime - startTime);
            long startTime1 = System.nanoTime();
            double sumParallel = items.parallelStream()
                    .map(Main::calculate)
                    .reduce(0d, Double::sum);
            long stopTime1 = System.nanoTime();
            System.out.print("Parallel time: ");
            System.out.println(stopTime1 - startTime1);

            List<Double> items2;
            Random rand2 = new Random();
            items = rand2.doubles(100000).mapToObj(i -> i)
                    .collect(Collectors.toList());

            System.out.println("\n100000 elements: ");
            long startTime2 = System.nanoTime();
            double sumStream2 = items.stream()
                    .map(Main::calculate)
                    .reduce(0d, Double::sum);
            long stopTime2 = System.nanoTime();
            System.out.print("Steam time: ");
            System.out.println(stopTime2 - startTime2);
            long startTime3 = System.nanoTime();
            double sumParallel3 = items.parallelStream()
                    .map(Main::calculate)
                    .reduce(0d, Double::sum);
            long stopTime3 = System.nanoTime();
            System.out.print("Parallel time: ");
            System.out.println(stopTime3 - startTime3);
        }
        catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
