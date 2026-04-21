package task2.steps;

import task2.AuthApiClient;
import task2.dto.RegistrationRequestDto;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthSteps {

    private final AuthApiClient api = new AuthApiClient();
    private String token;

    public void registerUser(String username, String password) {
        RegistrationRequestDto dto = new RegistrationRequestDto(username, password);
        api.register(dto)
                .statusCode(200)
                .body(is("success register"));
    }

    public void loginUserNotFound(String username, String password) {
        RegistrationRequestDto dto = new RegistrationRequestDto(username, password);
        api.login(dto)
                .statusCode(401)
                .body(is("not found"));
    }

    public void loginWrongPassword(String username, String password) {
        RegistrationRequestDto dto = new RegistrationRequestDto(username, password);
        api.login(dto)
                .statusCode(401)
                .body(is("not right pass"));
    }

    public void loginSuccess(String username, String password) {
        RegistrationRequestDto dto = new RegistrationRequestDto(username, password);
        String responseBody = api.login(dto)
                .statusCode(200)
                .extract()
                .asString();
        this.token = responseBody.split(":")[1].trim();
        assertNotNull(token, "Токен не должен быть null");
    }

    public void logoutWithInvalidToken(String invalidToken) {
        api.logout(invalidToken)
                .statusCode(401)
                .body(is("not found"));
    }

    public void logoutSuccess() {
        api.logout(this.token)
                .statusCode(200)
                .body(is("success logout"));
    }

    public String getToken() {
        return token;
    }
}