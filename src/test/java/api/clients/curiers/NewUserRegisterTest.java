package api.clients.curiers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;


public class NewUserRegisterTest {

    private CourierClient courierClient;

    private String accessToken;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.userRemove(accessToken).assertThat().statusCode(202)
                .and()
                .body("success", is(true))
                .and()
                .body("message", is("User successfully removed"));
    }

    @Test
    @DisplayName("New user register")
    @Description("200 OK")
    public void newUserRegister() {

        ValidatableResponse response = courierClient.newUserRegister();
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(200)
                .and()
                .body("success", is(true));
    }
}