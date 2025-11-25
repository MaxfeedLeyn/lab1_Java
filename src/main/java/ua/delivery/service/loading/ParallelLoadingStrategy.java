package ua.delivery.service.loading;

import java.util.logging.Logger;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.model.*;
import ua.delivery.repository.*;
import ua.delivery.service.LoadResult;

import java.util.concurrent.CompletableFuture;

public class ParallelLoadingStrategy implements LoadingStrategy {

    private static final Logger logger = Logger.getLogger(ParallelLoadingStrategy.class.getName());

    @Override
    public LoadResult load(
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            DataLoader dataLoader){

        logger.info("Starting parallel loading with Completable Future...");
        long startTime = System.currentTimeMillis();

        CompletableFuture<Integer> customerFuture = CompletableFuture
                .supplyAsync(() -> loadEntity(dataLoader, Customer.class, customerRepository))
                .exceptionally(e -> handleError("customers", e));

        CompletableFuture<Integer> orderFuture = CompletableFuture
                .supplyAsync(() -> loadEntity(dataLoader, Order.class, orderRepository))
                .exceptionally(e -> handleError("orders", e));

        CompletableFuture.allOf(customerFuture, orderFuture).join();

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Finished parallel loading in " + duration + " ms");

        return new LoadResult(
                customerFuture.join(),
                orderFuture.join(),
                duration
        );
    }

    private <T> int loadEntity(DataLoader dataLoader, Class<T> tClass, GenericRepository <T> genericRepository) {
        try{
            return dataLoader.loadEntity(tClass,  genericRepository);
        } catch (DataSerializationException e){
            throw new RuntimeException(e);
        }
    }

    private int handleError(String entityType, Throwable e){
        logger.severe("Failed to load entity " + entityType + ": " + e.getMessage());
        return 0;
    }
}
