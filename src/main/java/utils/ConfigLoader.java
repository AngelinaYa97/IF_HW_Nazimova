package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл application.properties не найден в classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить application.properties", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Свойство '" + key + "' не найдено в application.properties");
        }
        return value;
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getRickMortyApiUrl() {
        return getProperty("rickmorty.api.url");
    }
}