package task2.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import task2.steps.AuthSteps;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NegativeAuthTest {

    private static AuthSteps steps;
    private static Map<String, String> credentials;

    @BeforeAll
    static void setUp() throws Exception {
        steps = new AuthSteps();
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = NegativeAuthTest.class.getClassLoader().getResourceAsStream("user.json")) {
            if (is == null) throw new RuntimeException("user.json not found");
            credentials = mapper.readValue(is, Map.class);
        }
        System.out.println("Credentials loaded: " + credentials);
    }

    @Test
    @Order(1)
    @DisplayName("Логин с несуществующим пользователем")
    void testLoginUserNotFound() {
        String validPassword = credentials.get("password");
        steps.loginUserNotFound("unknown_user", validPassword);
    }

    @Test
    @Order(2)
    @DisplayName("Логин с неверным паролем")
    void testLoginWrongPassword() {
        String validUsername = credentials.get("username");
        steps.loginWrongPassword(validUsername, "wrong_password");
    }

    @Test
    @Order(3)
    @DisplayName("Выход с неверным токеном (валидный UUID, но не зарегистрированный)")
    void testLogoutInvalidToken() {
        String username = credentials.get("username");
        String password = credentials.get("password");

        steps.registerUser(username, password);
        steps.loginSuccess(username, password);
        String fakeToken = UUID.randomUUID().toString();
        steps.logoutWithInvalidToken(fakeToken);
    }
}