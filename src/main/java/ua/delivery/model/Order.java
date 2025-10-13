package ua.delivery.model;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.util.OrderUtils;

import java.util.Date;
import java.util.Arrays;
import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Order {
    private Customer customer;
    private MenuItem[] menuItems;
    private Date orderDate;

    private static final SimpleDateFormat FIXED_DATE_FORMAT;
    static {
        FIXED_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        FIXED_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Kyiv")); // ensures EEST for Oct 9
    }

    private static final Logger logger = Logger.getLogger(Order.class.getName());

    public Order(){
    }

    public Order(Customer customer, MenuItem[] menuItems, Date orderDate) {
        setCustomer(customer);
        setMenuItems(menuItems);
        setOrderDate(orderDate);
        logger.log(Level.INFO, "Order Created");
    }

    public Order(Order order){
        this.customer = new Customer(order.customer.getFirstName(), order.customer.getLastName(), order.customer.getAddress());
        int size = order.menuItems.length;
        this.menuItems = new MenuItem[size];
        System.arraycopy(order.menuItems, 0, this.menuItems, 0, size);
        this.orderDate = order.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        if (OrderUtils.isValidDate(orderDate)) {
            this.orderDate = orderDate;
        }
        else
            logger.log(Level.WARNING, "Invalid order date");
    }

    public void setCustomer(Customer customer) {
        if (OrderUtils.isValidCustomer(customer)) {
            this.customer = customer;
        }
        else
            logger.log(Level.WARNING, "Invalid customer");
    }

    public void setMenuItems(MenuItem[] menuItems) {
        if (OrderUtils.isValidMenu(menuItems)) {
            this.menuItems = menuItems.clone();
        }
        else
            logger.log(Level.WARNING, "Invalid menu items");
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem[] getMenuItems() {
        if(this.menuItems == null) return null;
        return menuItems.clone();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public static Order createOrder(Customer customer, MenuItem[] menuItems, Date orderDate) {
        if (OrderUtils.isValidCustomer(customer) && OrderUtils.isValidMenu(menuItems) && OrderUtils.isValidDate(orderDate)) {
            return new Order(customer, menuItems, orderDate);
        }
        throw new InvalidDataException("The attempt to create a Order has failed, check customer, menu items and order date!");
    }

    @Override
    public String toString() {
        String dateStr = orderDate == null ? "null" : FIXED_DATE_FORMAT.format(orderDate);
        return "Order{" +
                "Customer='" + customer.toString() + '\'' +
                ", MenuItems='" + Arrays.toString(menuItems) + '\'' +
                ", Date='" + orderDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) &&
                Arrays.equals(menuItems, order.menuItems) &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() { // same as with toString method
        int result = Objects.hash(customer, orderDate);
        result = 31 * result + Arrays.hashCode(menuItems);
        return result;
    }
}
