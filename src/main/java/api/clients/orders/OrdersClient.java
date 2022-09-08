package api.clients.orders;

import api.clients.Order;
import api.endpoints.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class OrdersClient {

    private static final String INGREDIENTS = "ingredients";

    private static final String AUTHORIZATION = "authorization";

    private static final String ORDERS = "orders";

    @Step("Get ingredients list")
    public ValidatableResponse getIngredientsList() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .when()
                .get(INGREDIENTS)
                .then();
    }

    @Step("Create order with auth-token")
    public ValidatableResponse createOrderWithAuthToken(Order order, String accessToken) {
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Create order without auth-token")
    public ValidatableResponse createOrderWithoutAuthToken(Order order) {
        return given()
                .spec(Endpoints.getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Create order without ingredients")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Create order with invalid hash")
    public ValidatableResponse createOrderWithInvalidHash(String accessToken) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(INGREDIENTS, "invalidHash");
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(dataMap)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Get orders special authorized user")
    public ValidatableResponse getOrderAuthUser(String accessToken) {
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .get(ORDERS)
                .then();
    }

    @Step("Get orders no authorized user")
    public ValidatableResponse getOrderNoAuthUser() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .when()
                .get(ORDERS)
                .then();
    }
}

