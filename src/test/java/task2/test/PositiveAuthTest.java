package task2.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import task2.steps.AuthSteps;


import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositiveAuthTest {

    private static AuthSteps steps;
    private static Map<String, String> credentials;
    private static String token;

    @BeforeAll
    static void setUp() throws Exception {
        steps = new AuthSteps();
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = PositiveAuthTest.class.getClassLoader().getResourceAsStream("user.json")) {
            if (is == null) throw new RuntimeException("user.json not found");
            credentials = mapper.readValue(is, Map.class);
        }
        System.out.println("Credentials loaded: " + credentials);
    }

    @Test
    @Order(1)
    @DisplayName("Регистрация пользователя")
    void testRegistration() {
        String username = credentials.get("username");
        String password = credentials.get("password");
        steps.registerUser(username, password);
    }

    @Test
    @Order(2)
    @DisplayName("Успешный логин и получение токена")
    void testLoginSuccess() {
        String username = credentials.get("username");
        String password = credentials.get("password");
        steps.loginSuccess(username, password);
        token = steps.getToken();
        assertNotNull(token, "Токен не должен быть null");
        System.out.println("Token: " + token);
    }

    @Test
    @Order(3)
    @DisplayName("Успешный выход из учётной записи")
    void testLogoutSuccess() {
        steps.logoutSuccess();
    }
}