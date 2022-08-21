package endpoints;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;


public class Endpoints {

    private Endpoints() {
    }

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site/api/")
                .build();
    }
}

