package ua.delivery.model;

import ua.delivery.util.DeliveryUtils;
import ua.delivery.exception.InvalidDataException;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public record Delivery(Order order, Staff deliverer, Date deliveryTime) implements Comparable<Delivery> {

    private static final Logger logger = Logger.getLogger(Delivery.class.getName());

    public Delivery{
        if (order == null) {
            String errorMessage = "Order is null, must be set.";
            logger.log(Level.SEVERE, errorMessage);
            throw new InvalidDataException(errorMessage);
        }
        if (deliverer == null) {
            String errorMessage = "Deliverer is null, must be set.";
            logger.log(Level.SEVERE, errorMessage);
            throw new InvalidDataException(errorMessage);
        }
        if(!DeliveryUtils.isValidDate(deliveryTime)) {
            String errorMessage = "Invalid delivery date: " + deliveryTime.toString();
            logger.log(Level.SEVERE, errorMessage);
            throw new InvalidDataException(errorMessage);
        }

        logger.log(Level.INFO, "Delivery has been created");
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
        if (order == null && deliveryTime == null && deliverer == null) {
            throw new InvalidDataException("None of the objects (order, deliverer with deliveryTime) should be null.");
        }
        return new Delivery(order, deliverer, deliveryTime);
    }

    @Override
    public int compareTo(Delivery o){
        return deliveryTime.compareTo(o.deliveryTime);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "Order='" + order.toString() + '\'' +
                ", Staff='" + deliverer.toString() + '\'' +
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
