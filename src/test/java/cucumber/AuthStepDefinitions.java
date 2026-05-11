package cucumber;

import constants.AuthCredentials;
import constants.AuthMessage;
import hooks.ServerHooks;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import task2.api.AuthorizationApi;
import task2.dto.UserDTO;
import task2.steps.AuthSteps;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthStepDefinitions {

    private final AuthSteps authSteps = new AuthSteps(new AuthorizationApi());
    private final Map<String, String> originalUser;
    private String username;
    private String password;
    private UserDTO user;
    private String token;
    private ValidatableResponse lastResponse;

    public AuthStepDefinitions() {
        this.originalUser = ServerHooks.getUserCredentials();
    }

    @Дано("email пользователя")
    @Step("Установить email пользователя из JSON")
    public void setUserEmail() {
        this.username = originalUser.get("username");
    }

    @Дано("пароль пользователя")
    @Step("Установить пароль пользователя из JSON")
    public void setUserPassword() {
        this.password = originalUser.get("password");
    }

    @Дано("зарегистрированный пользователь")
    @Step("Зарегистрироваться под пользователем")
    public void registeredUser() {
        initDefaultUser();
        doRegister();
    }

    @Дано("авторизованный пользователь")
    @Step("Авторизоваться под пользователем")
    public void authorizedUser() {
        initDefaultUser();
        doRegister();
        ValidatableResponse loginResp = authSteps.login(user);
        loginResp.statusCode(200)
                .body(containsString("token :"));
        this.token = authSteps.extractToken(loginResp);
        Allure.addAttachment("Извлеченный токен", "text/plain", token, ".txt");
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    private void initDefaultUser() {
        this.username = originalUser.get("username");
        this.password = originalUser.get("password");
        this.user = new UserDTO(username, password);
    }

    private void doRegister() {
        authSteps.register(user)
                .statusCode(200)
                .body(is(AuthMessage.SUCCESS_REGISTER.getValue()));
    }

    @Дано("несуществующий пользователь")
    @Step("Подменить username на несуществующий")
    public void nonExistentUser() {
        Map<String, String> modified = new HashMap<>(originalUser);
        modified.put("username", AuthCredentials.NON_EXISTENT_USERNAME);
        this.username = modified.get("username");
        this.password = modified.get("password");
    }

    @Дано("пользователь с неверным паролем")
    @Step("Подменить пароль на неверный")
    public void wrongPassword() {
        Map<String, String> modified = new HashMap<>(originalUser);
        modified.put("password", AuthCredentials.WRONG_PASSWORD);
        this.username = originalUser.get("username");
        this.password = modified.get("password");
    }

    @Дано("невалидный токен")
    @Step("Установить невалидный токен")
    public void invalidToken() {
        this.token = AuthCredentials.INVALID_TOKEN;
    }

    @Когда("пользователь отправляет запрос на регистрацию")
    @Step("Отправить запрос POST на регистрацию")
    public void userSendsRegistrationRequest() {
        user = new UserDTO(username, password);
        lastResponse = authSteps.register(user);
    }

    @Когда("пользователь отправляет запрос на логин")
    @Step("Отправить запрос POST на логин")
    public void userSendsLoginRequest() {
        user = new UserDTO(username, password);
        lastResponse = authSteps.login(user);
        // Если логин успешен, извлекаем токен
        if (lastResponse.extract().statusCode() == 200) {
            this.token = authSteps.extractToken(lastResponse);
            Allure.addAttachment("Извлеченный токен", "text/plain", token, ".txt");
        }
    }

    @Когда("пользователь отправляет запрос на logout")
    @Step("Отправить запрос GET на logout")
    public void userSendsLogoutRequest() {
        lastResponse = authSteps.logout(token);
    }

    @Когда("пользователь отправляет запрос на logout с невалидным токеном")
    @Step("Отправить запрос GET на logout с невалидным токеном")
    public void userSendsLogoutInvalidToken() {
        lastResponse = authSteps.logout(AuthCredentials.INVALID_TOKEN);
    }

    @Тогда("статус код ответа должен быть {int}")
    @Step("Проверить статус-код")
    public void responseStatusCodeShouldBe(int statusCode) {
        lastResponse.statusCode(statusCode);
    }

    @И("тело ответа должно содержать {string}")
    @Step("Проверить тело ответа")
    public void responseBodyShouldContain(String expectedText) {
        lastResponse.body(containsString(expectedText));
        Allure.addAttachment("Фактическое тело ответа", "text/plain",
                lastResponse.extract().asString(), ".txt");
    }

    @Тогда("должен вернуться токен")
    @Step("Проверить токен")
    public void tokenShouldBeReturned() {
        assertNotNull(token);
        assertFalse(token.isBlank());
    }
}