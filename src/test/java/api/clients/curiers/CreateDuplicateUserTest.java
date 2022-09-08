package api.clients.curiers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateDuplicateUserTest {

    private CourierClient courierClient;
    private String accessToken;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        accessToken = courierClient
                .newUserRegister()
                .extract()
                .path("accessToken")
                .toString()
                .substring(7);

    }

    @After
    public void tearDown() {
        courierClient.userRemove(accessToken);
    }


    @Test
    @DisplayName("User already exist")
    @Description("403 Forbidden")
    public void newUserRegExist() {
        ValidatableResponse response = courierClient.defaultUserRegister();
        courierClient.defaultUserRegister();

        response.assertThat().statusCode(403)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("User already exists"));

    }

}
