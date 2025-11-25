package ua.delivery.service.reporting;

import ua.delivery.model.Customer;
import ua.delivery.model.Order;

import java.util.List;
import java.util.Map;

public record DeliveryReport(
        List<Customer> customers,
        List<Order> freshOrders,
        long generationTimeMs
) {

    @Override
    public String toString() {
        return String.format(
                """
                
                === Delivery Report ===
                Customers: %d,
                Fresh Orders: %d,
                Generation Time: %d ms
                """,
                customers.size(),
                freshOrders.size(),
                generationTimeMs
        );
    }
}
