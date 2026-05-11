package baseApi;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.ConfigLoader;

import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {

    protected static final String BASE_URL = ConfigLoader.getBaseUrl();

    static {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected ValidatableResponse post(String urn, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .log().all()
                .post(urn)
                .then()
                .log().all();
    }

    protected ValidatableResponse postWithHeader(String urn, String headerName, String headerValue) {
        return given()
                .header(headerName, headerValue)
                .log().all()
                .post(urn)
                .then()
                .log().all();
    }

    protected ValidatableResponse get(String url, Object... pathParams) {
        return given()
                .log().all()
                .get(url, pathParams)
                .then()
                .log().all();
    }

    protected ValidatableResponse getWithHeader(String urn, String headerName, String headerValue) {
        return given()
                .header(headerName, headerValue)
                .log().all()
                .get(urn)
                .then()
                .log().all();
    }

    protected ValidatableResponse getWithQueryParams(String baseUri, String path, java.util.Map<String, ?> queryParams) {
        var spec = given().baseUri(baseUri).log().all();
        queryParams.forEach(spec::queryParam);
        return spec.get(path).then().log().all();
    }
}
