package ua.delivery.service.reporting;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
import ua.delivery.model.*;
import ua.delivery.repository.*;
import ua.delivery.util.OrderUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DeliveryReportService {

    private static final Logger logger = Logger.getLogger(DeliveryReportService.class.getName());

    private final ExecutorService executor;

    public DeliveryReportService(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        logger.info("DeliveryReportService initialized with " +  threadPoolSize + " threads");
    }

    public DeliveryReportService() {
        this(4);
    }

    public Callable<List<Order>> createFreshOrders(OrderRepository orderRepository) {
        return () -> {
            logger.info("Creating fresh orders from order repository in thread " + Thread.currentThread().getName());
            return orderRepository.getAll().stream()
                    .filter(o -> OrderUtils.isValidDate(o.getOrderDate()))
                    .toList();
        };
    }

    public Callable<List<Customer>> createCustomers(CustomerRepository customerRepository) {
        return () -> {
            logger.info("Creating customers from order repository in thread " + Thread.currentThread().getName());

            return customerRepository.getAll().stream()
                    .filter(Objects::nonNull)
                    .toList();
        };
    }

    public CompletableFuture<DeliveryReport> generateReportAsync(
            CustomerRepository customerRepository,
            OrderRepository orderRepository){

        logger.info("Starting parallel report generation");
        long startTime = System.currentTimeMillis();

        CompletableFuture<List<Customer>> customersFuture = CompletableFuture
                .supplyAsync(() -> {
                    try{
                        return createCustomers(customerRepository).call();
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }, executor);

        CompletableFuture<List<Order>> ordersFuture = CompletableFuture
                .supplyAsync(() -> {
                    try{
                        return createFreshOrders(orderRepository).call();
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });

        return CompletableFuture.allOf(
                customersFuture,
                ordersFuture
        ).thenApply(v -> {
            long duration = System.currentTimeMillis() - startTime;


            logger.info("Report generation completed in " + duration + " ms");
            return new DeliveryReport(
                    customersFuture.join(),
                    ordersFuture.join(),
                    duration
            );
        });
    }

    public DeliveryReport generateReportWithExecutor(
            CustomerRepository customerRepository,
            OrderRepository orderRepository) throws ExecutionException, InterruptedException {

        logger.info("Starting report generation with ExecutorService");
        long startTime = System.currentTimeMillis();

        Future<List<Customer>> customerFuture =
                executor.submit(createCustomers(customerRepository));

        Future<List<Order>> orderFuture =
                executor.submit(createFreshOrders(orderRepository));

        long duration = System.currentTimeMillis() - startTime;

        DeliveryReport deliveryReport = new DeliveryReport(
                customerFuture.get(),
                orderFuture.get(),
                duration
        );

        logger.info("Report generation completed in " + duration + " ms");
        return deliveryReport;
    }


    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("UniversityReportService shut down");
    }
}
