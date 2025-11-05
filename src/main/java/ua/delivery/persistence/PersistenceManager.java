package ua.delivery.persistence;


import java.util.logging.Logger;
import java.util.logging.Level;
import ua.delivery.config.AppConfig;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.serializer.DataSerializer;
import ua.delivery.serializer.JsonDataSerializer;
import ua.delivery.serializer.YamlDataSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenceManager {

    private static final Logger logger = Logger.getLogger(PersistenceManager.class.getName());

    private final AppConfig config;
    private final Map<String, DataSerializer<?>> serializers;

    public PersistenceManager(AppConfig Config) {
        this.config = Config;
        this.serializers = new HashMap<>();
        initializeSerializers();
        logger.info(String.format("RepositoryManager initialization with %d serializers",  serializers.size()));
    }

    private void initializeSerializers() {
        serializers.put("JSON", new JsonDataSerializer<>());
        serializers.put("YAML", new YamlDataSerializer<>());
        logger.info(String.format("Registered serializers: %s", serializers.keySet()));
    }

    private <T> void validateParameters(List<T> items, String entityType, Class<T> tClass)
            throws DataSerializationException {
        if (items == null) {
            throw new DataSerializationException("Items list cannot be null");
        }

        if (entityType == null ||  entityType.trim().isEmpty()) {
            throw new DataSerializationException("Entity type cannot be null or empty");
        }

        if (tClass == null) {
            throw new DataSerializationException("Class type cannot be null");
        }
    }

    public <T> void save(List<T> items,
                         String entityType,
                         Class<T> tClass,
                         String format) throws DataSerializationException {
        validateParameters(items, entityType, tClass);

        String formatUpper = format.toUpperCase();
        DataSerializer<T> serializer = getSerializer(formatUpper);
        String filePath = getFilePath(entityType, formatUpper);

        try{
            serializer.serialize(items, filePath);
            logger.info(String.format("Successfully saved %d %s items to %s",
                    items.size(), entityType, formatUpper));
        }
        catch (DataSerializationException ex) {
            logger.severe(String.format("Failed to save %s to %s: %s",
                    entityType, formatUpper, ex.getMessage()));
            throw ex;
        }
    }

    public <T> List<T> load(String entityType,
                            Class<T> tClass,
                            String format) throws DataSerializationException {
        if (entityType == null ||  entityType.trim().isEmpty()) {
            throw new DataSerializationException("Entity type cannot be null or empty");
        }

        if (tClass == null) {
            throw new DataSerializationException("Class type cannot be null");
        }

        String formatUpper = format.toUpperCase();
        DataSerializer<T> serializer = getSerializer(formatUpper);
        String filePath = getFilePath(entityType, formatUpper);

        logger.info(String.format("Loading %s from %s file: %s",
                entityType, formatUpper, filePath));

        try{
            List<T> items = serializer.deserialize(filePath, tClass);
            logger.info(String.format("Successfully loaded %d items of type %s",
                    items.size(), entityType));
            return items;
        }
        catch (DataSerializationException ex) {
            logger.severe(String.format("Failed to load %s from %s: %s", entityType, formatUpper, ex.getMessage()));
            throw ex;
        }
    }

    public <T> void saveToAllFormat(List<T> items,
                                  String entityType,
                                  Class<T> tClass) throws DataSerializationException {
        logger.info(String.format("Saving %d items of type %s to all formats", items.size(), entityType));

        save(items, entityType, tClass, "JSON");
        save(items, entityType, tClass, "YAML");

        logger.info(String.format("Successfully saved %s to all formats", entityType));
    }

    @SuppressWarnings("unchecked")
    private <T> DataSerializer<T> getSerializer(String format) throws DataSerializationException {
        DataSerializer<?> serializer = serializers.get(format);

        if (serializer == null) {
            String errorMsg = String.format("Unsupported format: %s. Available formats: %s",
                    format, serializers.keySet());
            logger.severe(errorMsg);
            throw new DataSerializationException(errorMsg);
        }

        return (DataSerializer<T>) serializer;
    }

    private String getFilePath(String entityType, String format) {
        return switch (format){
            case  "JSON" -> config.getJsonFilePath(entityType);
            case  "YAML" -> config.getYamlFilePath(entityType);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    public boolean isFormatSupported(String format) {
        return serializers.containsKey(format.toUpperCase());
    }

    public String[] getSupportedFormats() {
        return serializers.keySet().toArray(new String[0]);
    }
}
