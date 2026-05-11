package task2.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import task2.api.AuthorizationApi;
import task2.dto.UserDTO;

public class AuthSteps {

    private final AuthorizationApi api;

    public AuthSteps(AuthorizationApi api) {
        this.api = api;
    }

    public ValidatableResponse register(UserDTO user) {
        return api.register(user);
    }

    public ValidatableResponse login(UserDTO user) {
        return api.login(user);
    }

    public ValidatableResponse logout(String token) {
        return api.logout(token);
    }

    public String extractToken(ValidatableResponse loginResponse) {
        String body = loginResponse.extract().asString();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(body);
            if (node.has("token")) {
                return node.get("token").asText();
            }
        } catch (Exception ignored) {
        }
        String marker = "token :";
        if (body.contains(marker)) {
            return body.split(marker)[1].trim();
        }
        marker = "token:";
        if (body.contains(marker)) {
            return body.split(marker)[1].trim();
        }
        throw new RuntimeException("Токен не найден в ответе: " + body);
    }
}