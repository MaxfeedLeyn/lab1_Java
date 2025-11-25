package ua.delivery.service.loading;

import java.util.logging.Logger;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.model.*;
import ua.delivery.repository.*;
import ua.delivery.service.LoadResult;


public class SequentialLoadingStrategy implements LoadingStrategy {

    final static Logger logger = Logger.getLogger(SequentialLoadingStrategy.class.getName());

    @Override
    public LoadResult load(
        CustomerRepository customerRepository,
        OrderRepository orderRepository,
        DataLoader dataLoader){

        logger.info("Starting sequential loading...");
        long startTime = System.currentTimeMillis();

        try {

            int customers = dataLoader.loadEntity(Customer.class, customerRepository);
            int orders = dataLoader.loadEntity(Order.class, orderRepository);

            long duration = System.currentTimeMillis() - startTime;
            logger.info("Sequential loading completed in" + duration + "ms");

            return new LoadResult(customers, orders, duration);
        } catch (DataSerializationException e){
            long duration = System.currentTimeMillis() - startTime;
            logger.severe(String.format("Sequential loading failed after %d ms: %s", duration, e.getMessage()));
            throw new RuntimeException("Failed to load data", e);
        }
    }
}
