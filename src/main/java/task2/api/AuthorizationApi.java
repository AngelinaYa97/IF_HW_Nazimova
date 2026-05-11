package task2.api;

import baseApi.BaseApiClient;
import io.restassured.response.ValidatableResponse;
import task2.dto.UserDTO;

import static constants.AuthUrn.LOGIN;
import static constants.AuthUrn.LOGOUT;
import static constants.AuthUrn.REGISTER;


public class AuthorizationApi extends BaseApiClient {

    public ValidatableResponse register(UserDTO user) {
        return post(REGISTER.getValue(), user);
    }

    public ValidatableResponse login(UserDTO user) {
        return post(LOGIN.getValue(), user);
    }

    public ValidatableResponse logout(String token) {
        return getWithHeader(LOGOUT.getValue(), "Authorization", token);
    }
}