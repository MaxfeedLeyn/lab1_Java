package ua.delivery.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import ua.delivery.exception.InvalidDataException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppConfig {

    private static final Logger LOG = Logger.getLogger(AppConfig.class.getName());
    private final Properties properties;
    private final String configFilePath;

    public AppConfig(){
        this("config.properties");
    }

    public AppConfig(String configFilePath){
        this.configFilePath = configFilePath;
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties(){
        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
            LOG.info("Configuration loaded successfully from file: " + configFilePath);
        } catch (IOException e) {
            LOG.warning("Could not load config from file system: " + configFilePath + ". Trying classpath...");

            try(InputStream input = getClass().getClassLoader().getResourceAsStream(configFilePath)){
                if (input == null){
                    LOG.severe("Configuration file not found in classpath: " + configFilePath);
                    throw new InvalidDataException("Unable to find configuration file: " + configFilePath);
                }
                properties.load(input);
                LOG.info("Configuration loaded successfully from file: " + configFilePath);
            }
            catch (IOException ex){
                throw new RuntimeException("Failed to load configuration", ex);
            }
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

    private String combinePaths(String basePath, String filename) {
        Path base = Paths.get(basePath);
        Path file = Paths.get(filename);

        if (file.isAbsolute()) {
            return filename;
        }

        return base.resolve(file).toString();
    }

    public String getJsonFilePath(String entityType){
        String basePath = getBaseDataPath();
        String key = String.format("data.path.%s.json", entityType.toLowerCase());
        String fileName = getProperty(key);

        if (fileName == null){
            LOG.warning("Json file not found for entity type: " + entityType + ". Using default.");
            fileName = String.format("%s.json", entityType.toLowerCase());
        }

        return combinePaths(basePath, fileName);
    }

    public String getYamlFilePath(String entityType){
        String basePath = getBaseDataPath();
        String key = String.format("data.path.%s.yaml", entityType.toLowerCase());
        String fileName = getProperty(key);

        if (fileName == null){
            LOG.warning("Yaml file not found for entity type: " + entityType + ". Using default.");
            fileName = String.format("%s.yaml", entityType.toLowerCase());
        }

        return combinePaths(basePath, fileName);
    }

    public String getBaseDataPath() {
        return getProperty("data.path.base", "src/data");
    }

    public int getIntProperty(String key, int defaultValue){
        String value = getProperty(key);
        if (value == null){
            return defaultValue;
        }

        try{
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex){
            LOG.warning("Invalid integer value for key: " + key + ". Using default value: " + defaultValue);
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue){
        String value = getProperty(key);
        if (value == null){
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public boolean hasProperty(String key){
        return properties.containsKey(key);
    }

    public Properties getProperties(){
        return new Properties(properties);
    }
}
