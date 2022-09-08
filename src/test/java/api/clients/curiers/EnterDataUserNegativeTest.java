package api.clients.curiers;

import api.clients.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

public class EnterDataUserNegativeTest {

    private CourierClient courierClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
        user = User.getRandom();
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
    @DisplayName("Auth user with incorrect pass log")
    @Description("401 Unauthorized")
    public void authIncorrectPassLogg() {
        ValidatableResponse response = courierClient.userEnterLog(user);
        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 401",
                statusCode, equalTo(401));
        boolean isLoggedIn = response
                .extract()
                .path("success");
        assertFalse("User logged in with an invalid password",
                isLoggedIn);
        String message = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect",
                message, equalTo("email or password are incorrect"));
    }
}
