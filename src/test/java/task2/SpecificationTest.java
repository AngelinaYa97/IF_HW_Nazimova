package task2;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SpecificationTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = Specifications.baseRequestSpec("http://localhost:8080");
    }
}