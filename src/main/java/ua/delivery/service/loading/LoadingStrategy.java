package ua.delivery.service.loading;

import ua.delivery.repository.*;
import ua.delivery.service.LoadResult;

@FunctionalInterface
public interface LoadingStrategy{

    LoadResult load(
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            DataLoader dataLoader
    );
}
