package task1.rickandmorty;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    public static RequestSpecification baseRequestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(io.restassured.http.ContentType.JSON)
                .log(LogDetail.BODY)   // логируем тело запроса
                .build();
    }

    public static ResponseSpecification baseResponseSpecSuccess() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)    // логируем всё: статус, заголовки, тело
                .build();
    }
}