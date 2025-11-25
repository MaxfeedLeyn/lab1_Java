package ua.delivery.service.comparison;

import ua.delivery.model.Customer;
import ua.delivery.model.MenuItem;
import ua.delivery.model.Order;
import ua.delivery.repository.CustomerRepository;
import ua.delivery.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Stream;

public class PerformanceComparisonService {

    private static final Logger logger = Logger.getLogger(PerformanceComparisonService.class.getName());

    public ComparisonResult compareCustomerFiltering(
            CustomerRepository repository,
                    String namePart){

        logger.info("Comparing Customer filtering approach");

        long startSequential =  System.currentTimeMillis();
        List<Customer> customers = repository.getAll().stream()
                .filter(s -> s.getFullName().contains(namePart))
                .toList();
        long sequentialTime = System.currentTimeMillis() - startSequential;

        long startParallel = System.currentTimeMillis();
        List<Customer> parallelCustomers = repository.getAll().parallelStream()
                .filter(s -> s.getFullName().contains(namePart))
                .toList();
        long parallelTime = System.currentTimeMillis() - startParallel;

        long startExecutor = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try{
            List<Customer> allCustomers = repository.getAll();
            int chunkSize = Math.max(1, allCustomers.size() / 4);

            List<CompletableFuture<List<Customer>>> futures = new ArrayList<>();

            for (int i = 0; i < allCustomers.size(); i += chunkSize) {
                int start = i;
                int end = Math.min(i + chunkSize, allCustomers.size());
                List<Customer> chunk = allCustomers.subList(start, end);

                CompletableFuture<List<Customer>> future = CompletableFuture.supplyAsync(() ->
                        chunk.stream()
                                .filter(c -> c.getFullName().contains(namePart))
                                .toList(), executor);
                futures.add(future);
            }

            List<Customer> executorResult = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .toList();

        } finally {
            executor.shutdown();
        }
        long executionTime = System.currentTimeMillis() - startExecutor;

        ComparisonResult result = new ComparisonResult(
                "Customer Filtering by some part",
                customers.size(),
                sequentialTime,
                parallelTime,
                executionTime
        );

        logger.info("Customer Filtering completed: " + result);
        return result;
    }

    public ComparisonResult compareOrderCalculation(
            OrderRepository repository){

        logger.info("Comparing Order calculation approach");

        long startSequential =  System.currentTimeMillis();
        List<Float> orders = repository.getAll().stream()
                .flatMap(order ->{
                    List<MenuItem> menuItemList = List.of(order.getMenuItems());
                    float OrderedSum = menuItemList.stream()
                            .map(MenuItem::getPrice)
                            .reduce( 0f, (acc, element) -> acc + element);
                    return Stream.of(OrderedSum);
                })
                .toList();
        long sequentialTime = System.currentTimeMillis() - startSequential;

        long startParallel = System.currentTimeMillis();
        List<Float> parallelResult = repository.getAll().parallelStream()
                .flatMap(order ->{
                    List<MenuItem> menuItemList = List.of(order.getMenuItems());
                    float OrderedSum = menuItemList.stream()
                            .map(MenuItem::getPrice)
                            .reduce( 0f, (acc, element) -> acc + element);
                    return Stream.of(OrderedSum);
                })
                .toList();
        long parallelTime = System.currentTimeMillis() - startParallel;

        long startExecutor = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try{
            List<Order> allOrders = repository.getAll();
            int chunkSize = Math.max(1, allOrders.size() / 4);
            List<CompletableFuture<List<Float>>> futures = new ArrayList<>();
            for (int i = 0; i < allOrders.size(); i += chunkSize) {
                int start = i;
                int end = Math.min(i + chunkSize, allOrders.size());
                List<Order> chunk = allOrders.subList(start, end);

                CompletableFuture<List<Float>> future = CompletableFuture.supplyAsync(() ->
                        chunk.stream()
                                .flatMap(order ->{
                                    List<MenuItem> menuItemList = List.of(order.getMenuItems());
                                    float OrderedSum = menuItemList.stream()
                                            .map(MenuItem::getPrice)
                                            .reduce( 0f, (acc, element) -> acc + element);
                                    return Stream.of(OrderedSum);
                                })
                                .toList(), executor);
                futures.add(future);
            }
        } finally {
            executor.shutdown();
        }
        long executionTime = System.currentTimeMillis() - startExecutor;

        ComparisonResult result = new ComparisonResult(
                "Order Sum Calculation",
                orders.size(),
                sequentialTime,
                parallelTime,
                executionTime
        );

        logger.info("Order Sum Calculation completed: " + result);
        return result;
    }
}
