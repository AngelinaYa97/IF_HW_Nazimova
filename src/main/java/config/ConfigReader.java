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
                throw new RuntimeException("файл config.properties не найден");
            }

            properties.load(input);

        } catch (Exception ex) {
            throw new RuntimeException("ошибка загрузки файла config.properties", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}