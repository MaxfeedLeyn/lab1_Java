package ua.delivery.service.loading;

import java.util.logging.Logger;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.model.MenuItem;
import ua.delivery.persistence.PersistenceManager;
import ua.delivery.repository.*;
import ua.delivery.service.LoadResult;

import java.util.List;

public class DataLoader {

    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

    private final PersistenceManager persistenceManager;
    private final String format;

    public DataLoader(PersistenceManager persistenceManager, String format) {
        this.persistenceManager = persistenceManager;
        this.format = format;
        logger.info("DataLoader initialized with format: " + format);
    }

    public DataLoader(PersistenceManager persistenceManager) {
        this(persistenceManager, "JSON");
    }

    public <T> int loadEntity(Class<T> tClass, GenericRepository<T> genericRepository)
            throws DataSerializationException {
        String entityName = tClass.getSimpleName().toLowerCase() + "s";
        return loadEntity(entityName, tClass, genericRepository);
    }

    public <T> int loadEntity(String entityName, Class<T> tClass, GenericRepository<T> genericRepository)
            throws DataSerializationException {
        List<T> items = persistenceManager.load(entityName, tClass, format);
        return genericRepository.addAll(items);
    }

    public LoadResult load(
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            LoadingStrategy strategy){

        logger.info("Loading data using strategy: " + strategy.getClass().getSimpleName());

        return strategy.load(
                customerRepository,
                orderRepository,
                this
        );
    }
}
