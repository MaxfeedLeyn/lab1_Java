package ua.university.model;

import ua.university.util.OrderUtils;

import java.util.Date;
import java.util.Arrays;
import java.util.Objects;

public class Order {
    private Customer customer;
    MenuItem[] menuItems;
    Date orderDate;

    public Order(){
    }

    public Order(Customer customer, MenuItem[] menuItems, Date orderDate) {
        setCustomer(customer);
        setMenuItems(menuItems);
        setOrderDate(orderDate);
    }

    public void setOrderDate(Date orderDate) {
        if (OrderUtils.isValidDate(orderDate)) {
            this.orderDate = orderDate;
        }
    }

    public void setCustomer(Customer customer) {
        if (OrderUtils.isValidCustomer(customer)) {
            this.customer = customer;
        }
    }

    public void setMenuItems(MenuItem[] menuItems) {
        if (OrderUtils.isValidMenu(menuItems)) {
            this.menuItems = menuItems.clone();
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem[] getMenuItems() {
        return menuItems.clone();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public static Order createOrder(Customer customer, MenuItem[] menuItems, Date orderDate) {
        if (OrderUtils.isValidCustomer(customer) && OrderUtils.isValidMenu(menuItems)) {
            return new Order(customer, menuItems, orderDate);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Customer=" + customer.toString() + '\'' +
                ", MenuItems=" + Arrays.toString(menuItems) + '\'' +
                ", Date=" + orderDate +
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
