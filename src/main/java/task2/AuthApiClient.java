package task2;

import io.restassured.response.ValidatableResponse;
import task2.dto.RegistrationRequestDto;

import static io.restassured.RestAssured.given;

public class AuthApiClient extends BaseApiClient {

    public ValidatableResponse register(RegistrationRequestDto dto) {
        return given()
                .spec(requestSpec)
                .body(dto)
                .post("/api/register")
                .then();
    }

    public ValidatableResponse login(RegistrationRequestDto dto) {
        return given()
                .spec(requestSpec)
                .body(dto)
                .post("/api/login")
                .then();
    }

    public ValidatableResponse logout(String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", token)
                .get("/api/logout")
                .then();
    }
}