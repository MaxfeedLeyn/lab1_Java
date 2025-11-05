package ua.delivery.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import java.util.logging.Logger;
import java.util.logging.Level;
import ua.delivery.exception.DataSerializationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataSerializer<T> implements DataSerializer<T> {
    private static final Logger logger = Logger.getLogger(AbstractDataSerializer.class.getName());
    protected final ObjectMapper objectMapper;

    protected AbstractDataSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void serialize(List<T> items, String filePath) throws DataSerializationException {
        validateItemsForSerialization(items);
        validateFilePath(filePath);

        try{
            File file = new File(filePath);
            createParentDirectories(file);

            objectMapper.writeValue(file, items);
        }
        catch (IOException ex) {
            String errorMsg = String.format("Error while writing serialized data %s to file %s", getFormat(), filePath);
            throw new DataSerializationException(errorMsg, ex);
        }
    }

    @Override
    public List<T> deserialize(String filePath, Class<T> tClass) throws DataSerializationException {
        validateFilePath(filePath);
        validateClass(tClass);

        try{
            File file = new File(filePath);

            if (!file.exists()) {
                logger.warning("File does not exist: " + filePath + ". Returning empty list");
                return new ArrayList<>();
            }

            if (file.length() == 0) {
                logger.warning("File is empty: " + filePath + ". Returning empty list");
                return new ArrayList<>();
            }

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, tClass);
            List<T> items = objectMapper.readValue(file, javaType);

            if (items == null)
                items = new ArrayList<>();

            logger.info(String.format("Successfully deserialized %d items from %s file: %s",
                    items.size(), getFormat(), filePath));
            return items;
        }
        catch (IOException e){
            String errorMsg = String.format("Failed to deserialize data from %s file: %s", getFormat(), filePath);
            throw new DataSerializationException(errorMsg, e);
        }
    }

    protected void validateItemsForSerialization(List<T> items) throws DataSerializationException {
        if (items == null) {
            throw new DataSerializationException("Cannot serialize null list");
        }
    }

    protected void validateFilePath(String filePath) throws DataSerializationException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new DataSerializationException("Path cannot be null or empty");
        }
    }

    protected void validateClass(Class<T> tClass) throws DataSerializationException {
        if (tClass == null) {
            throw new DataSerializationException("Class type cannot be null");
        }
    }

    protected void createParentDirectories(File file) throws DataSerializationException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (created) {
                logger.log(Level.INFO, "Created directory: " + parentDir.getAbsolutePath());
            }
        }
    }
}
