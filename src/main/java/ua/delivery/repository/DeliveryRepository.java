package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import ua.delivery.model.Delivery;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DeliveryRepository {

    private static final Logger logger = Logger.getLogger(DeliveryRepository.class.getName());

    private static List<Delivery> deliveries;

    public DeliveryRepository(List<Delivery> deliveries) {
        if (deliveries == null) {
            throw new InvalidDataException("Can not be null");
        }
        DeliveryRepository.deliveries = new ArrayList<>(deliveries);
        logger.info("Created DeliveryRepository with:" + deliveries.size() + " items");
    }

    public void sortByCustomerNameAsc() {
        deliveries.sort((d1, d2) ->
                d1.getOrder().getCustomer().getFullName().compareTo(d2.getOrder().getCustomer().getFullName()));
        logger.info("Sorted DeliveryRepository by CustomerName ascending: " +  deliveries.size() + " items");
    }

    public void sortByCustomerNameDesc() {
        deliveries.sort((d1, d2) ->
                d2.getOrder().getCustomer().getFullName().compareTo(d1.getOrder().getCustomer().getFullName()));
        logger.info("Sorted DeliveryRepository by CustomerName descending: " +  deliveries.size() + " items");
    }

    public void sortByDateDesc() {
        deliveries.sort((d1, d2) ->
                d2.getDeliveryTime().compareTo(d1.getDeliveryTime()));
        logger.info("Sorted DeliveryRepository by DateDescending: " +  deliveries.size() + " items");
    }

    public void sortByStaffAsc() {
        deliveries.sort((d1, d2) ->
                d1.getDeliverer().getFirstName().compareTo(d2.getDeliverer().getFirstName()));
        logger.info("Sorted DeliveryRepository by Staff ascending: " +  deliveries.size() + " items");
    }

    void sortByStaffDesc() {
        deliveries.sort((d1, d2) ->
                d2.getDeliverer().getFirstName().compareTo(d1.getDeliverer().getFirstName()));
        logger.info("Sorted DeliveryRepository by Staff descending: " +  deliveries.size() + " items");
    }

    List<Delivery> getItemsForTesting() {
        return deliveries;
    }
}
