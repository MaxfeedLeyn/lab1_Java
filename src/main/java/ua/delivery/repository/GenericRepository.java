package ua.delivery.repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.exception.AlreadyExistsException;

public class GenericRepository<T> {
    private static final Logger logger = Logger.getLogger(GenericRepository.class.getName());

    private final List<T> items;
    private final IdentityExtractor<T> identityExtractor;
    private final String entityType;

    public GenericRepository(IdentityExtractor<T> identityExtractor, String entityType) {
        this.items = new CopyOnWriteArrayList<>();
        this.identityExtractor =  identityExtractor;
        this.entityType = entityType;
        logger.info("Creating repository for entity: " + entityType);
        logger.info("Created thread-safe repository for " + entityType);
    }

    public synchronized boolean add(T item) {
        if (item == null) {
            throw new InvalidDataException(entityType + " cannot be null");
        }

        String identity = identityExtractor.extractIdentity(item);
        if (findByIdentity(identity).isPresent()){
            String errorMsg = String.format("%s already exists with identity: %s", entityType, identity);
            logger.severe(errorMsg);
            throw new AlreadyExistsException(errorMsg);
        }

        items.add(item);
        logger.info("Added id: "+ entityType + ": " + identity);
        return true;
    }

    public int addAll(Collection<T> items) {
        if(items == null || items.isEmpty()) {
            return 0;
        }
        int addedCount = 0;
        for (T item : items) {
            try{
                if(add(item)) {
                    addedCount++;
                }
            } catch (AlreadyExistsException e) {
                logger.warning("Skipping duplicate:" + e.getMessage());
            }
        }

        logger.info("Bulk added" + addedCount + "of " + items.size() + " items to " + entityType);
        return addedCount;
    }

    public synchronized boolean remove(T item) {
        if (item == null) {
            logger.warning("Attempted to remove null argument" + entityType);
            return false;
        }

        boolean removed = items.remove(item);
        if (removed)
            logger.info("Removed " + entityType + ": " + identityExtractor.extractIdentity(item));
        else
            logger.warning("Attempted to remove " + entityType + ": " + identityExtractor.extractIdentity(item));
        return removed;
    }

    public synchronized boolean removeByIdentity(String identity) {
        if (identity == null) {
            logger.warning("Attempted to remove" + entityType + " null identity");
            return false;
        }

        Optional<T> itemToRemove = items.stream()
                .filter(item -> identity.equals(identityExtractor.extractIdentity(item)))
                .findFirst();

        if (itemToRemove.isPresent()) {
            boolean removed = items.remove(itemToRemove.get());
            if (removed) {
                logger.info("Removed " + entityType + " by identity: " + identity);
            }
            return removed;
        } else{
            logger.warning("No " + entityType + " found with identity: " + identity + " to remove");
            return false;
        }
    }


    public synchronized boolean update(T newItem) {
        if (newItem == null) {
            throw new InvalidDataException(entityType + " cannot be null");
        }
        String identity = identityExtractor.extractIdentity(newItem);
        Optional<T> existingItem = findByIdentity(identity);
        if (existingItem.isEmpty()) {
            logger.warning("Cannot update: " + entityType + " not found with identity: " + identity);
            return false;
        }
        items.remove(existingItem.get());
        items.add(newItem);
        logger.info("Updated " + entityType + " by identity: " + identity);
        return true;
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public boolean containsByIdentity(String identity) {
        return findByIdentity(identity).isPresent();
    }

    public Optional<T> findByIdentity(String identity) {
        if (identity == null) {
            logger.warning("Attempted to find" + entityType + " null identity");
            return Optional.empty();
        }

        Optional<T> result = items.stream()
                .filter(item -> identity.equals(identityExtractor.extractIdentity(item)))
                .findFirst();

        if (result.isPresent())
            logger.info("Found " + entityType + " with identity: " + identity);
        else
            logger.warning("No " + entityType + " found with identity: " + identity);

        return result;
    }

    public List<T> getAll() {
        logger.info("Retrieved all entityType" + entityType + ". Count: " + items.size());
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public synchronized void clear() {
        int sizeBefore = items.size();
        items.clear();
        logger.info("Cleared repository. Removed" + sizeBefore + " " + entityType +  " items");
    }

    List<T> getItemsForTesting() {
        return new ArrayList<>(items);
    }

    public void sortByIdentity(String order){
        List<T> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparing(identityExtractor::extractIdentity));
        if (order.equals("desc")) {
            Collections.reverse(sorted);
        }
        items.clear();
        items.addAll(sorted);
        boolean asc = order.equals("asc");
        logger.info(String.format("Sorted %s by identity in %s order", entityType, asc ? "ascending" : "descending"));
    }
}
