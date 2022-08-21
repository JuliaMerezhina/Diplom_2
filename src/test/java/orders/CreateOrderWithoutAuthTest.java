package orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserOperation;

import static org.hamcrest.CoreMatchers.equalTo;

//Возвращается статус-код 200 вместо 401
//Пробовала дебажить и включать логи. Не удалось понять, где ошибка. Может быть это баг.
public class CreateOrderWithoutAuthTest {
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
    @DisplayName("Create order without auth")
    @Description("401 Unauthorized")
    public void createOrderWithoutAuthToken() {
        getUserOperation.newUserRegister();
        getCreateOrder.createOrderWithoutAuthToken();
        getCreateOrder.getOrderResponseSuccess()
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
