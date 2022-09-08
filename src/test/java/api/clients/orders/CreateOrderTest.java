package api.clients.orders;

import api.clients.Order;
import api.clients.curiers.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

public class CreateOrderTest {

    private OrdersClient ordersClient;
    private CourierClient courierClient;

    private Order order;
    private String accessToken;

    @Before
    public void setUp() {
        order = new Order();
        order.setIngredients(List.of("60d3b41abdacab0026a733c6", "609646e4dc916e00276b2870"));
        ordersClient = new OrdersClient();
        courierClient = new CourierClient();
        ordersClient.getIngredientsList();

        ValidatableResponse response = courierClient.newUserRegister();
        accessToken = response.extract().path("accessToken");
        response.assertThat()
                .statusCode(200)
                .and()
                .body("success", is(true));

    }

    @After
    public void tearDown() {
        courierClient.userRemove(accessToken).assertThat()
                .statusCode(202)
                .and()
                .body("success", is(true))
                .and()
                .body("message", is("User successfully removed"));
    }


    @Test
    @DisplayName("Create order with auth")
    @Description("200 Created")
    public void createOrderWithAuthToken() {

        ordersClient.createOrderWithAuthToken(order, accessToken)
                .assertThat().statusCode(200)
                .body("success", equalTo(true))
                .and()
                .body("name", notNullValue());

    }


    @Test
    @DisplayName("Create order without ingredients")
    @Description("400 Bad Request")
    public void createOrderWithoutIngredients() {
        ordersClient.createOrderWithoutIngredients(accessToken)
                .assertThat()
                .statusCode(400)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Create order with invalid hash")
    @Description("500 Internal Server Error")
    public void createOrderWithInvalidHash() {
        ordersClient.createOrderWithInvalidHash(accessToken)
                .assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Get orders special authorized user")
    @Description("200 ОК")
    public void getOrderAuthUser() {
        courierClient.newUserRegister();
        ordersClient.createOrderWithAuthToken(order, accessToken)
                .assertThat()
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("api/clients/orders", notNullValue());
    }

    @Test
    @DisplayName("Get orders no authorized user")
    @Description("401 Unauthorized")
    public void getOrderNoAuthUser() {
        ordersClient.getOrderNoAuthUser()
                .assertThat()
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}