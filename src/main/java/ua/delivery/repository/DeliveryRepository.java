package ua.delivery.repository;

import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import ua.delivery.model.Delivery;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DeliveryRepository extends GenericRepository<Delivery>{

    private static final Logger logger = Logger.getLogger(DeliveryRepository.class.getName());

    public DeliveryRepository() {
        super(delivery -> String.valueOf(delivery.getDeliveryTime()), "Delivery");
    }

    public List<Delivery> sortByCustomerNameAsc() {
        List<Delivery> deliveries = getAll();
        deliveries.sort((d1, d2) -> {
            if(!d1.getOrder().getCustomer().getFullName().equals(d2.getOrder().getCustomer().getFullName()))
                return d1.getOrder().getCustomer().getFullName().compareTo(d2.getOrder().getCustomer().getFullName());
            else if (!d1.getDeliverer().getFullName().equals(d2.getDeliverer().getFullName()))
                return d1.getDeliverer().getFullName().compareTo(d2.getDeliverer().getFullName());
            else
                return d1.getDeliveryTime().compareTo(d2.getDeliveryTime());
        });
        logger.info("Sorted DeliveryRepository by CustomerName ascending: " +  deliveries.size() + " items");
        return deliveries;
    }

    public List<Delivery> sortByCustomerNameDesc() {
        List<Delivery> deliveries = getAll();
        deliveries.sort((d1, d2) -> {
            if(!d1.getOrder().getCustomer().getFullName().equals(d2.getOrder().getCustomer().getFullName()))
                return d2.getOrder().getCustomer().getFullName().compareTo(d1.getOrder().getCustomer().getFullName());
            else if (!d1.getDeliverer().getFullName().equals(d2.getDeliverer().getFullName()))
                return d1.getDeliverer().getFullName().compareTo(d2.getDeliverer().getFullName());
            else
                return d1.getDeliveryTime().compareTo(d2.getDeliveryTime());
        });
        logger.info("Sorted DeliveryRepository by CustomerName descending: " +  deliveries.size() + " items");
        return deliveries;
    }

    public List<Delivery> sortByDateDesc() {
        List<Delivery> deliveries = getAll();
        deliveries.sort((d1, d2) ->{
            if (!d1.getDeliveryTime().equals(d2.getDeliveryTime()))
                return d2.getDeliveryTime().compareTo(d1.getDeliveryTime());
            else if(!d1.getOrder().getCustomer().getFullName().equals(d2.getOrder().getCustomer().getFullName()))
                return  d1.getOrder().getCustomer().getFullName().compareTo(d2.getOrder().getCustomer().getFullName());
            else
                return d1.getDeliverer().getFullName().compareTo(d2.getDeliverer().getFullName());
        });
        logger.info("Sorted DeliveryRepository by DateDescending: " +  deliveries.size() + " items");
        return deliveries;
    }

    public List<Delivery> sortByStaffAsc() {
        List<Delivery> deliveries = getAll();
        deliveries.sort((d1, d2) -> {
            if (!d1.getDeliverer().getFullName().equals(d2.getDeliverer().getFullName()))
                return d1.getDeliverer().getFullName().compareTo(d2.getDeliverer().getFullName());
            else if (!d1.getOrder().getCustomer().getFullName().equals(d2.getOrder().getCustomer().getFullName()))
                return d1.getOrder().getCustomer().getFullName().compareTo(d2.getOrder().getCustomer().getFullName());
            else
                return d1.getDeliveryTime().compareTo(d2.getDeliveryTime());
        });
        logger.info("Sorted DeliveryRepository by Staff ascending: " +  deliveries.size() + " items");
        return deliveries;
    }

    public List<Delivery> sortByStaffDesc() {
        List<Delivery> deliveries = getAll();
        deliveries.sort((d1, d2) -> {
            if (!d1.getDeliverer().getFullName().equals(d2.getDeliverer().getFullName()))
                return d2.getDeliverer().getFullName().compareTo(d1.getDeliverer().getFullName());
            else if(!d1.getOrder().getCustomer().getFullName().equals(d2.getOrder().getCustomer().getFullName()))
                return d1.getOrder().getCustomer().getFullName().compareTo(d2.getOrder().getCustomer().getFullName());
            else
                return d1.getDeliveryTime().compareTo(d2.getDeliveryTime());
        });
        logger.info("Sorted DeliveryRepository by Staff descending: " +  deliveries.size() + " items");
        return deliveries;
    }

}
