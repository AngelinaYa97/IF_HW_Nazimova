package hooks;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.regex.Pattern;

public class AllureSanitizedFilter implements Filter {

    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("\"password\"\\s*:\\s*\"[^\"]*\"", Pattern.CASE_INSENSITIVE);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        Allure.getLifecycle().getCurrentTestCase().ifPresent(tc -> {
            attachRequest(requestSpec);
            attachResponse(response);
        });

        return response;
    }

    private void attachRequest(FilterableRequestSpecification requestSpec) {
        String method = requestSpec.getMethod();
        String uri = requestSpec.getURI();
        String body = requestSpec.getBody() != null
                ? requestSpec.getBody().toString()
                : "";

        String sanitized = PASSWORD_REGEX.matcher(body)
                .replaceAll("\"password\":\"***\"");

        Allure.addAttachment("Request: " + method + " " + uri,
                "application/json", sanitized, ".json");
    }

    private void attachResponse(Response response) {
        String body = response.getBody() != null
                ? response.getBody().asString()
                : "";

        String sanitized = PASSWORD_REGEX.matcher(body)
                .replaceAll("\"password\":\"***\"");

        Allure.addAttachment("Response: " + response.getStatusCode(),
                "application/json", sanitized, ".json");
    }
}
