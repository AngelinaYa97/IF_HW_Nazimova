package hooks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

import java.io.InputStream;
import java.util.Map;

public class ServerHooks {

    private static Map<String, String> credentials;

    @BeforeAll
    public static void setUp() {
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 10000));
        RestAssured.filters(new AllureSanitizedFilter());
        getUserCredentials();
    }

    public static synchronized Map<String, String> getUserCredentials() {
        if (credentials == null) {
            loadCredentials();
        }
        return Map.copyOf(credentials);
    }

    private static void loadCredentials() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = ServerHooks.class
                .getClassLoader()
                .getResourceAsStream("user.json")) {
            if (is == null) {
                throw new RuntimeException("user.json не найден в classpath");
            }
            credentials = mapper.readValue(is, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить user.json", e);
        }
    }
}
