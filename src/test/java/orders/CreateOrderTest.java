package orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserOperation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {

    private CreateOrder getCreateOrder;
    private UserOperation getUserOperation;

    @Before
    public void setUp() {

        getCreateOrder = new CreateOrder();
        getUserOperation = new UserOperation();
        getCreateOrder.getIngredientsList();
    }

    @After
    public void tearDown() {
        getUserOperation.userRemove();
    }


    @Test
    @DisplayName("Create order with auth")
    @Description("200 Created")
    public void createOrderWithAuthToken() {
        getUserOperation.newUserRegister();
        getUserOperation.setAccessToken();
        getCreateOrder.createOrderWithAuthToken(getUserOperation.getAccessToken());
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .and()
                .body("name", notNullValue());

    }


    @Test
    @DisplayName("Create order without ingredients")
    @Description("400 Bad Request")
    public void createOrderWithoutIngredients() {
        getUserOperation.newUserRegister();
        getUserOperation.setAccessToken();
        getCreateOrder.createOrderWithoutIngredients(getUserOperation.getAccessToken());
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create order with invalid hash")
    @Description("500 Internal Server Error")
    public void createOrderWithInvalidHash() {
        getUserOperation.newUserRegister();
        getUserOperation.setAccessToken();
        getCreateOrder.createOrderWithInvalidHash(getUserOperation.getAccessToken());
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Get orders special authorized user")
    @Description("200 ОК")
    public void getOrderAuthUser() {
        getUserOperation.newUserRegister();
        getUserOperation.setAccessToken();
        getCreateOrder.createOrderWithAuthToken(getUserOperation.getAccessToken());
        getCreateOrder.getOrderAuthUser(getUserOperation.getAccessToken());
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .and()
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Get orders no authorized user")
    @Description("401 Unauthorized")
    public void getOrderNoAuthUser() {
        getCreateOrder.getOrderNoAuthUser();
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}