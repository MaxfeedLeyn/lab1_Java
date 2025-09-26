package ua.delivery;

import java.util.Calendar;
import java.util.Date;

import ua.delivery.service.*;

import ua.delivery.model.*;

public class Main {
    public static void main(String[] args) {
        MenuItem[] menuItems = new MenuItem[2];
        menuItems[0] = new MenuItem("Pizza", 10, "Italic");
        menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");

        Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
        Customer customer1 = new Customer("Steve", "Craft", "St. center 1");

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.AUGUST, 29);
        Date orderDate = cal.getTime();
        Order order = new Order(customer, menuItems, orderDate);
        Order order1 = new Order(customer1, menuItems, orderDate);

        Staff staff = new Staff("Henry", "Griffin", "St. Independence 11");
        Delivery delivery = new Delivery(order, staff, orderDate);
        Delivery delivery1 = new Delivery(order1, staff, orderDate);

        OrderDelivery[]  orderDeliveries = {
            new OrderDelivery(OrderStatus.PENDING, customer, delivery), new OrderDelivery(OrderStatus.CONFIRMED, customer1, delivery1)
        };

        System.out.println(ReportGenerator.generateReport(orderDeliveries));
    }
}
