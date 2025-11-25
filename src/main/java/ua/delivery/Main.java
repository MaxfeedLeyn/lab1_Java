package ua.delivery;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.delivery.config.AppConfig;
import ua.delivery.persistence.PersistenceManager;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.repository.CustomerRepository;
import ua.delivery.repository.GenericRepository;
import ua.delivery.repository.MenuItemRepository;
import ua.delivery.repository.OrderRepository;
import ua.delivery.serializer.DataSerializer;
import ua.delivery.serializer.JsonDataSerializer;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.parser.*;
import ua.delivery.service.*;
import ua.delivery.model.*;
import ua.delivery.service.comparison.ComparisonResult;
import ua.delivery.service.comparison.PerformanceComparisonService;
import ua.delivery.service.loading.DataLoader;
import ua.delivery.service.loading.ExecutorLoadingStrategy;
import ua.delivery.service.loading.ParallelLoadingStrategy;
import ua.delivery.service.loading.SequentialLoadingStrategy;
import ua.delivery.service.reporting.DeliveryReport;
import ua.delivery.service.reporting.DeliveryReportService;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    public static void main(String[] args){

//        try{
//            MenuItem test = MenuItem.createMenuItem("Sushi", 15f, "Japan");
////            MenuItem test2 = MenuItem.createMenuItem("Sushi", 15f, "Japan");
//            test.setName("Suushi");
//            test.setPrice(20f);
//            test.setCategory("Japanese");
//
////            Customer testCustomer = new Customer("Jane", "Doe", "St. 1");
////            System.out.println(testCustomer);
//
//            Restaurant restaurant = Restaurant.createRestaurant("", "", "");
//        }
//        catch (InvalidDataException e){
//            System.out.println(e.getMessage());
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }


//        try {
//            AppConfig appConfig = new AppConfig();
//            PersistenceManager manager = new PersistenceManager(appConfig);
//
//            System.out.println("\n" + "=".repeat(70) + "\n");
//
//            List<Person> personRepo = new ArrayList<>();
//
//            personRepo.add(new Person("Mark", "Tsukerberg", "St. Central 1"));
//            personRepo.add(new Person("Sau", "Paulo", "St. Central 2"));
//            personRepo.add(new Person("Robert", "Polson", "St. Central 3"));
//
//            manager.save(personRepo, "persons", Person.class, "JSON");
//            System.out.println("Saved to: " + appConfig.getJsonFilePath("persons"));
//
//            manager.save(personRepo, "persons", Person.class, "YAML");
//            System.out.println("Saved to: " + appConfig.getYamlFilePath("persons"));
//
//            GenericRepository<Person> loadedFromJson = new GenericRepository<>(
//                    person -> person.getFirstName(),
//                    "Person"
//            );
//            List<Person> personList = manager.load("persons", Person.class, "JSON");
//            List<Person> personList2 = manager.load("persons", Person.class, "YAML");
//            for(Person person : personList){
//                System.out.println(person);
//            }
//
//            for(Person person : personList2){
//                System.out.println(person);
//            }
//
//
//            MenuItem menuItem1 = new MenuItem("Pasta", 10.0f, "Italian");
//            MenuItem menuItem2 = new MenuItem("Filet-O-Fish", 20.0f, "English");
//            MenuItem menuItem3 = new MenuItem("burrito", 25.0f, "Mexican");
//            MenuItemRepository menuItemRepository = new MenuItemRepository();
//            menuItemRepository.add(menuItem1);
//            menuItemRepository.add(menuItem2);
//            menuItemRepository.add(menuItem3);
//            manager.save(menuItemRepository.getAll(), "menuItems", MenuItem.class, "YAML");
//            List<MenuItem> list = manager.load("menuitems", MenuItem.class, "YAML");
//        }
//        catch (InvalidDataException e) {
//            System.out.println(e.getMessage());
//        }
//        catch (DataSerializationException e){
//            System.out.println(e.getMessage());
//        }

        AppConfig appConfig = new AppConfig();
        PersistenceManager persistenceManager = new PersistenceManager(appConfig);

        CustomerRepository customerRepository = new CustomerRepository();
        OrderRepository orderRepository = new OrderRepository();

        demonstrateParallelLoading(
                persistenceManager,
                customerRepository,
                orderRepository
        );

        demonstrateReportGeneration(
                customerRepository,
                orderRepository
        );

        demonstratePerformanceComparison(
                customerRepository,
                orderRepository
        );
    }

    private static void demonstrateParallelLoading(
        PersistenceManager persistenceManager,
        CustomerRepository customerRepository,
        OrderRepository orderRepository
    ){
        DataLoader dataLoader = new DataLoader(persistenceManager);

        LoadResult sequentialResult = dataLoader.load(
            customerRepository,
            orderRepository,
            new SequentialLoadingStrategy()
        );
        System.out.println(sequentialResult);

        clearRepository(customerRepository, orderRepository);

        LoadResult parallelResult = dataLoader.load(
                customerRepository,
                orderRepository,
                new ParallelLoadingStrategy()
        );
        System.out.println(parallelResult);

        clearRepository(customerRepository, orderRepository);

        LoadResult executeResult = dataLoader.load(
                customerRepository,
                orderRepository,
                new ExecutorLoadingStrategy(4)
        );
        System.out.println(executeResult);

        System.out.println("\n === Loading Time Comparison ===");
        System.out.println("Sequential:     " + sequentialResult.durationMs() + "ms" );
        System.out.println("Parallel:       " + parallelResult.durationMs() + "ms" );
        System.out.println("ExecuteService: " + executeResult.durationMs() + "ms" );
    }

    private static void demonstrateReportGeneration(
            CustomerRepository customerRepository,
            OrderRepository orderRepository){

        DeliveryReportService reportService = new DeliveryReportService(4);

        try{

            DeliveryReport report = reportService.generateReportAsync(
                    customerRepository,
                    orderRepository
            ).join();

            System.out.println(report);

            DeliveryReport report2 = reportService.generateReportWithExecutor(
                    customerRepository,
                    orderRepository
            );

            System.out.println("ExecutorService report generation: " + report2.generationTimeMs() + "ms\n");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            reportService.shutdown();
        }
    }

    private static void demonstratePerformanceComparison(
            CustomerRepository customerRepository,
            OrderRepository orderRepository){

        PerformanceComparisonService comparisonService = new PerformanceComparisonService();

        ComparisonResult filterResult = comparisonService.compareCustomerFiltering(
                customerRepository, "er"
        );
        System.out.println(filterResult);

        ComparisonResult ordersResult = comparisonService.compareOrderCalculation(
                orderRepository
        );
        System.out.println(ordersResult);
    }

    private static void clearRepository(
            CustomerRepository customerRepository,
            OrderRepository orderRepository){
        customerRepository.clear();
        orderRepository.clear();
    }
}
