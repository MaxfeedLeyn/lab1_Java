package ua.delivery.serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.logging.Logger;
import java.util.logging.Level;

public class JsonDataSerializer<T> extends AbstractDataSerializer<T>{

    private static final Logger logger = Logger.getLogger(JsonDataSerializer.class.getName());

    public JsonDataSerializer() {
        super(createDefaultObjectMapper());
        logger.info("JsonDataSerializer initialized with pretty printing enabled");
    }

    public JsonDataSerializer(ObjectMapper mapper) {
        super(mapper);
        logger.info("JsonDataSerializer initialized with custom ObjectMapper");
    }

    private static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    @Override
    public String getFormat() {
        return "JSON";
    }
}
