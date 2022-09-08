package api.clients.curiers;

import api.clients.User;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DataUserUpdateTest {

    private CourierClient courierClient;
    private User user;
    private String accessToken;
    private Faker faker;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        user = User.getRandom();
        faker = new Faker();
        ValidatableResponse response = courierClient.newUserRegister();
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        courierClient.userRemove(accessToken);
    }

    @Test
    @DisplayName("Update data auth user")
    @Description("200 ОК")
    public void userUpdate() {
        String password = faker.lorem().characters(10, true);
        user.setEmail(password);
        courierClient.userUpdate(accessToken)
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", is(true));
    }


    @Test
    @DisplayName("User update without Auth")
    @Description("401 Unauthorized")
    public void userUpdateWithoutAuth() {
        ValidatableResponse response = courierClient.userUpdateWithoutAuth();
        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 401",
                statusCode, equalTo(401));
        String errorMessage = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect",
                errorMessage, equalTo("You should be authorised"));
    }
}
