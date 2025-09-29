package ua.delivery.model;

import ua.delivery.util.DeliveryUtils;

import java.util.Date;
import java.util.Objects;

public record Delivery(Order order, Staff deliverer, Date deliveryTime) {

    public Delivery{
        boolean hasCriticalErrors = false;
        if (order == null) {
            hasCriticalErrors = true;
            System.out.println("Error when create Delivery: Order can`t be null");
        }
        if (deliverer == null) {
            hasCriticalErrors = true;
            System.out.println("Error when create Delivery: Staff can`t be null");
        }
        if(!DeliveryUtils.isValidDate(deliveryTime)) {
            hasCriticalErrors = true;
            System.out.println("Error when create Delivery: Invalid delivery time");
        }
        if (hasCriticalErrors) {
            System.out.println("Subject was created with errors!");
        }
    }

    public Delivery(){
        this(null, null, null);
    }

    public Order getOrder() {
        return order;
    }

    public Person getDeliverer() {
        return deliverer;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public static Delivery createDelivery(Order order, Staff deliverer, Date deliveryTime) {
        if (order != null && deliveryTime != null && deliverer != null) {
            return new Delivery(order, deliverer, deliveryTime);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "Order=" + order.toString() + '\'' +
                ", deliveryTime=" + deliveryTime.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(delivery.order, order) &&
                Objects.equals(delivery.deliverer, deliverer) &&
                Objects.equals(delivery.deliveryTime, deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, deliverer, deliveryTime);
    }
}
