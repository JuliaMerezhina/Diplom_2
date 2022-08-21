package orders;

import endpoints.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class CreateOrder {
    private List<String> ingredientsList =
            List.of("60d3b41abdacab0026a733c6", "609646e4dc916e00276b2870",
                    "609646e4dc916e00276b2873");
    private Response getOrderResponse;

    private static final String INGREDIENTS = "ingredients";

    private static final String AUTHORIZATION = "authorization";

    private static final String ORDERS = "orders";

    private final Random random = new Random();

    @Step("Getting successful response")
    public Response getOrderResponseSuccess() {
        return getOrderResponse;
    }

    @Step("Get ingredients list")
    public void getIngredientsList() {
        ingredientsList = given()
                .spec(Endpoints.getBaseSpec())
                .get(INGREDIENTS)
                .then()
                .extract()
                .path("data._id");
    }

    @Step("Create order with auth-token")
    public void createOrderWithAuthToken(String accessToken) {

        String randomIngredientOfList = ingredientsList
                .get(random.nextInt(ingredientsList.size() - 1));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(INGREDIENTS, randomIngredientOfList);
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(dataMap)
                .when()
                .post(ORDERS);
    }

    @Step("Create order without auth-token")
    public void createOrderWithoutAuthToken() {

        String randomIngredientOfList = ingredientsList
                .get(random.nextInt(ingredientsList.size() - 1));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(INGREDIENTS, randomIngredientOfList);
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .post(ORDERS);
    }

    @Step("Create order without ingredients")
    public void createOrderWithoutIngredients(String accessToken) {
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .post(ORDERS);
    }

    @Step("Create order with invalid hash")
    public void createOrderWithInvalidHash(String accessToken) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(INGREDIENTS, "invalidHash");
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(dataMap)
                .when()
                .post(ORDERS);
    }

    @Step("Get orders special authorized user")
    public void getOrderAuthUser(String accessToken) {
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .get(ORDERS);
    }

    @Step("Get orders no authorized user")
    public void getOrderNoAuthUser() {
        getOrderResponse = given()
                .spec(Endpoints.getBaseSpec())
                .when()
                .get(ORDERS);
    }
}

