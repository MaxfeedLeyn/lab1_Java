package ua.delivery.model;

import java.util.Date;
import java.util.Objects;

public class Delivery {
    private Order order;
    private Staff deliverer;
    private Date deliveryTime;

    public Delivery(){
    }

    public Delivery(Order order, Staff deliverer, Date deliveryTime) {
        setOrder(order);
        setDeliverer(deliverer);
        setDeliveryTime(deliveryTime);
    }

    public void setOrder(Order order) {
        if(order != null) this.order = order;
    }

    public void setDeliverer(Staff deliverer) {
        if(deliverer != null) this.deliverer = deliverer;
    }

    public void setDeliveryTime(Date deliveryTime) {
        if (deliveryTime != null) this.deliveryTime = deliveryTime;
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
                ", Order='" + order.toString() + '\'' +
                ", deliveryTime='" + deliveryTime.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(order, delivery.order) &&
                Objects.equals(deliverer, delivery.deliverer) &&
                Objects.equals(deliveryTime, delivery.deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, deliverer, deliveryTime);
    }
}
