package ua.delivery.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.delivery.config.AppConfig;
import ua.delivery.model.*;
import ua.delivery.persistence.PersistenceManager;
import ua.delivery.repository.*;
import ua.delivery.service.LoadResult;
import ua.delivery.service.loading.DataLoader;
import ua.delivery.service.loading.ExecutorLoadingStrategy;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    private long startTime;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        startTime = System.currentTimeMillis();

        logger.info("=================================================================");
        logger.info("===  APPLICATION CONTEXT INITIALIZED - STARTING UP           ===");
        logger.info("=================================================================");

        ServletContext context = sce.getServletContext();

        try {
            logger.info("Step 1: Creating AppConfig and PersistenceManager...");
            AppConfig config = new AppConfig();
            PersistenceManager persistenceManager = new PersistenceManager(config);
            logger.info("✓ Configuration initialized");

            logger.info("Step 2: Creating repositories...");
            CustomerRepository customerRepository = new CustomerRepository();
            OrderRepository orderRepository = new OrderRepository();
            logger.info("✓ All repositories created");

            logger.info("Step 3: Loading data from JSON files using ExecutorLoadingStrategy...");
            DataLoader dataLoader = new DataLoader(persistenceManager);

            LoadResult loadResult = dataLoader.load(
                    customerRepository,
                    orderRepository,
                    new ExecutorLoadingStrategy(4)
            );

            logger.info("✓ Data loading completed");
            logLoadResults(loadResult, customerRepository, orderRepository);

            logger.info("Step 4: Storing repositories in ServletContext...");
            context.setAttribute("customerRepository", customerRepository);
            context.setAttribute("orderRepository", orderRepository);
            context.setAttribute("persistenceManager", persistenceManager);
            logger.info("✓ Repositories stored in ServletContext");

            long initTime = System.currentTimeMillis() - startTime;
            logger.info("=================================================================");
            logger.info("===  APPLICATION STARTUP SUCCESSFUL in {}ms                  ===", initTime);
            logger.info("===  All servlets will now share these repository instances  ===");
            logger.info("=================================================================");

        } catch (Exception e) {
            logger.error("=================================================================");
            logger.error("===  CRITICAL ERROR DURING APPLICATION STARTUP               ===");
            logger.error("=================================================================");
            logger.error("Failed to initialize application context", e);
            throw new RuntimeException("Application initialization failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("=================================================================");
        logger.info("===  APPLICATION CONTEXT DESTROYED - SHUTTING DOWN           ===");
        logger.info("=================================================================");

        ServletContext context = sce.getServletContext();

        try {
            // Get repositories for final statistics and saving
            CustomerRepository customerRepository =
                    (CustomerRepository) context.getAttribute("customerRepository");
            OrderRepository orderRepository =
                    (OrderRepository) context.getAttribute("orderRepository");
            PersistenceManager persistenceManager =
                    (PersistenceManager) context.getAttribute("persistenceManager");

            logger.info("Final statistics:");
            if (customerRepository != null) {
                logger.info("  - Customers: {}", customerRepository.size());
            }
            if (orderRepository != null) {
                logger.info("  - Orders: {}", orderRepository.size());
            }

            // Save all data to JSON files before shutdown
            if (persistenceManager != null) {
                logger.info("Saving all data to JSON files...");

                if (customerRepository != null) {
                    persistenceManager.save(
                            customerRepository.getAll(),
                            "customers",
                            Customer.class,
                            "JSON"
                    );
                    logger.info("✓ Customers saved");
                }

                if (orderRepository != null) {
                    persistenceManager.save(
                            orderRepository.getAll(),
                            "orders",
                            Order.class,
                            "JSON"
                    );
                    logger.info("✓ Orders saved");
                }

                logger.info("All data successfully saved to JSON files");
            }

            long totalUptime = System.currentTimeMillis() - startTime;
            logger.info("Total application uptime: {}ms ({} seconds)",
                    totalUptime, totalUptime / 1000);

            // Clean up ServletContext
            logger.info("Cleaning up ServletContext attributes...");
            context.removeAttribute("customerRepository");
            context.removeAttribute("orderRepository");

            logger.info("=================================================================");
            logger.info("===  APPLICATION SHUTDOWN COMPLETE                           ===");
            logger.info("=================================================================");

        } catch (Exception e) {
            logger.error("Error during application shutdown", e);
        }
    }

    private void logLoadResults(LoadResult loadResult,
                                CustomerRepository customerRepository,
                                OrderRepository orderRepository) {

        logger.info("─────────────────────────────────────────────────────────────────");
        logger.info("DATA LOADING RESULTS:");
        logger.info("─────────────────────────────────────────────────────────────────");
        logger.info("  Customers loaded:  {}", customerRepository.size());
        logger.info("  Orders loaded:  {}", orderRepository.size());
        logger.info("─────────────────────────────────────────────────────────────────");
        logger.info("  Total entities:   {}",
                customerRepository.size() + orderRepository.size());
        logger.info("─────────────────────────────────────────────────────────────────");
    }
}