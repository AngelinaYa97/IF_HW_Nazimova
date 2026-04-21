package task2;

import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {
    protected RequestSpecification requestSpec;

    public BaseApiClient() {
        this.requestSpec = Specifications.baseRequestSpec("http://localhost:8080");
    }
}