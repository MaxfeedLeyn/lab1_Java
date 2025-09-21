package ua.university;

import java.util.Calendar;
import java.util.Date;

import ua.university.model.*;

public class Main {
    public static void main(String[] args) {
        //Menu success
        MenuItem[] menuItems = new MenuItem[2];
        menuItems[0] = new MenuItem("Pizza", 10, "Italic");
        menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
        System.out.println(menuItems[1]);

        // Customer success
        Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
        System.out.println(customer.getAddress());

        // Order success
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.AUGUST, 29);
        Date orderDate = cal.getTime();
        Order order = new Order(customer, menuItems, orderDate);
        System.out.println(order);

        // Delivery success
        Staff staff = new Staff("Henry", "Griffin", "St. Independence 11");
        System.out.println(staff.getFullName());
        Delivery delivery = new Delivery(order, staff, orderDate);
        Delivery delivery1 = new Delivery(order, staff, orderDate);
        System.out.println(delivery.equals(delivery1));

        // Some validation
        MenuItem newDish = new MenuItem();
        System.out.println(newDish);
        newDish.setName("Dumplings");
        System.out.println(newDish);
        newDish.setCategory("Ukrainian");
        System.out.println(newDish);
        newDish.setPrice(-2);
        System.out.println(newDish);
        newDish.setPrice(10);
        System.out.println(newDish);

        cal.set(2025, Calendar.AUGUST, 1);
        orderDate =  cal.getTime();
        Order order1 = new Order(customer, menuItems, orderDate);
        System.out.println(order1.getOrderDate()); // null because this date is too late, validation check +-30 days

        // access to different members
        Person person1 = new Person();
        person1.setFirstName("Henry");
        person1.setLastName("Stickman");
        person1.getFullName(); // protected member, comment if necessary
        Restaurant restaurant = new Restaurant("Restaurant1", "Italic", "St. center 1");
        System.out.println(restaurant);
        System.out.println(restaurant.name); // private member, comment if necessary

        //unsuccess classes
        order1 = Order.createOrder(null, null, null);
        System.out.println(order1);
    }
}
