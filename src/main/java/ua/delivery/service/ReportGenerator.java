package ua.delivery.service;

import ua.delivery.model.OrderDelivery;
import ua.delivery.model.OrderStatus;

public class ReportGenerator {

    public static String formatOrderStatus(OrderDelivery orderDelivery) {
        return switch (orderDelivery.orderStatus()){
            case PENDING -> "Pending";
            case CONFIRMED -> "Confirmed";
            case PREPARING -> "Preparing";
            case DELIVERED -> "Delivered";
            case CANCELED -> "Canceled";
        };
    }
    public static String generateReport(OrderDelivery[] orderDeliveries) {
        StringBuilder report = new StringBuilder();

        for (OrderDelivery delivery : orderDeliveries) {
            report.append(String.format(
                    "Status: %s, \n Customer: %s, \n Delivery: %s",
                    formatOrderStatus(delivery),
                    delivery.customer().toString(),
                    delivery.delivery().toString()
            ));
        }

        return report.toString();
    }
}
