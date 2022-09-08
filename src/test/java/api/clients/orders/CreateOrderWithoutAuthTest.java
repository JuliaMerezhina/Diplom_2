package api.clients.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;


public class CreateOrderWithoutAuthTest {

    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }


    @Test
    @DisplayName("Create order without auth")
    @Description("401 Unauthorized")
    public void createOrderWithoutAuthToken() {
        ordersClient.getOrderNoAuthUser()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
