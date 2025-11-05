package ua.delivery.serializer;

import ua.delivery.exception.DataSerializationException;
import java.util.List;

public interface DataSerializer<T> {

    void serialize(List<T> items, String filePath) throws DataSerializationException;

    List<T> deserialize(String filePath, Class<T> tClass) throws DataSerializationException;

    String getFormat();
}
