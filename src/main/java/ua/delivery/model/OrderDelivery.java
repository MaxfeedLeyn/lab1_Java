package ua.delivery.model;

public record OrderDelivery(
//        Restaurant restaurant,
//        CuisineType cuisineType,
        OrderStatus orderStatus,
        Customer customer,
        Delivery delivery
) {
}
