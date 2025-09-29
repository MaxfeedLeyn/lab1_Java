package ua.delivery.service;

import ua.delivery.model.OrderDelivery;

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
                    "Status: %s, \n Delivery: %s %n",
                    formatOrderStatus(delivery),
                    delivery.delivery().toString()
            ));
            report.append("\n");
        }
        return report.toString();
    }
}
