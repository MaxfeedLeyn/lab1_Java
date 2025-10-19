package ua.delivery.model;

public record OrderDelivery(
        OrderStatus orderStatus,
        Customer customer,
        Delivery delivery
) {
}
