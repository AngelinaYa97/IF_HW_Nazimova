package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("jj config.properties jj");
            }

            properties.load(input);

        } catch (Exception ex) {
            throw new RuntimeException("jjk config.properties", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}